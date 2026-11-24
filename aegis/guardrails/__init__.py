"""Guardrails — fail-closed input/output/tool-call rails with deterministic-first checks."""

from __future__ import annotations

import re
from dataclasses import dataclass, field

from aegis.config import get_settings
from aegis.telemetry import get_logger

log = get_logger("guardrails")

# Patterns for obvious secret leakage in agent output (deterministic, ~2.6ms).
SECRET_PATTERNS = [
    re.compile(r"AKIA[0-9A-Z]{16}"),  # AWS access key
    re.compile(r"gh[pousr]_[A-Za-z0-9]{30,}"),  # GitHub token
    re.compile(r"sk-[A-Za-z0-9]{20,}"),  # generic api key
    re.compile(r"-----BEGIN [A-Z ]*PRIVATE KEY-----"),
    re.compile(r"(?i)password\s*[:=]\s*\S+"),
]

INJECTION_MARKERS = [
    "ignore previous instructions",
    "ignore all prior instructions",
    "disregard the above",
    "you are now a",
    "system override",
    "clear this alert",
    "approve this transaction",
]


class GuardrailResult:
    def __init__(self, passed: bool, action: str, rule: str, reason: str = "", latency_ms: float = 0.0):
        self.passed = passed
        self.action = action  # PASS | MODIFY | BLOCK
        self.rule = rule
        self.reason = reason
        self.latency_ms = latency_ms

    def __repr__(self) -> str:
        return f"GuardrailResult({self.action}, rule={self.rule}, passed={self.passed})"


@dataclass
class GuardrailConfig:
    fail_closed: bool = field(default_factory=lambda: get_settings().guardrails_fail_closed)


class GuardrailEngine:
    """Layers deterministic rails first, then optional model-backed rails."""

    def __init__(self, config: GuardrailConfig | None = None):
        self.cfg = config or GuardrailConfig()

    # ---- deterministic input rails -------------------------------------
    def check_input(self, text: str) -> GuardrailResult:
        import time

        t0 = time.perf_counter()
        low = text.lower()
        for marker in INJECTION_MARKERS:
            if marker in low:
                return GuardrailResult(
                    False, "BLOCK", "prompt_injection", marker, (time.perf_counter() - t0) * 1000
                )
        for pat in SECRET_PATTERNS:
            if pat.search(text):
                return GuardrailResult(
                    False, "MODIFY", "secret_present", "secret pattern detected", (time.perf_counter() - t0) * 1000
                )
        return GuardrailResult(True, "PASS", "input_clean", "", (time.perf_counter() - t0) * 1000)

    # ---- deterministic output rails ------------------------------------
    def check_output(self, text: str, require_citations: bool = False) -> GuardrailResult:
        import time

        t0 = time.perf_counter()
        for pat in SECRET_PATTERNS:
            if pat.search(text):
                return GuardrailResult(
                    False, "BLOCK", "output_secret", "output leaked a secret", (time.perf_counter() - t0) * 1000
                )
        if require_citations and not re.search(r"\[\w+\]", text):
            return GuardrailResult(
                False, "MODIFY", "ungrounded", "claims lack citations", (time.perf_counter() - t0) * 1000
            )
        return GuardrailResult(True, "PASS", "output_clean", "", (time.perf_counter() - t0) * 1000)

    # ---- tool-call rails -----------------------------------------------
    ALLOWED_TOOLS: dict[str, set[str]] = {
        "evidence": {"get_transactions", "get_kyc", "screen_watchlist", "get_prior_sars", "search_policy"},
        "network": {"query_graph_neighbourhood", "get_device_history", "get_entity"},
        "risk": {"get_customer_360", "check_chargeback_history", "get_device_history"},
        "compliance": {"search_obligations", "get_control_library", "diff_policy_version", "open_source_repo"},
    }
    WRITE_TOOLS = {"file_sar", "freeze_account", "update_control", "close_case"}

    def check_tool_call(self, role: str, tool: str, args: dict) -> GuardrailResult:
        import time

        t0 = time.perf_counter()
        if tool in self.WRITE_TOOLS and not args.get("_approved"):
            return GuardrailResult(
                False, "BLOCK", "approval_required", f"{tool} requires human approval", (time.perf_counter() - t0) * 1000
            )
        allowed = self.ALLOWED_TOOLS.get(role, set())
        if tool not in allowed and tool not in self.WRITE_TOOLS:
            return GuardrailResult(
                False, "BLOCK", "tool_not_allowed", f"{tool} not permitted for {role}", (time.perf_counter() - t0) * 1000
            )
        return GuardrailResult(True, "PASS", "tool_ok", "", (time.perf_counter() - t0) * 1000)

    # ---- orchestration --------------------------------------------------
    def enforce(self, result: GuardrailResult, *, on_fail: str = "raise"):
        log.info("guardrail", rule=result.rule, action=result.action, passed=result.passed)
        if result.passed:
            return result
        if self.cfg.fail_closed and on_fail == "raise":
            raise GuardrailViolation(result)
        return result


class GuardrailViolation(Exception):
    def __init__(self, result: GuardrailResult):
        self.result = result
        super().__init__(f"guardrail blocked: {result.rule} ({result.reason})")


_engine: GuardrailEngine | None = None


def get_guardrails() -> GuardrailEngine:
    global _engine
    if _engine is None:
        _engine = GuardrailEngine()
    return _engine