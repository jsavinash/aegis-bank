"""MCP tools exposed to agents: fraud scoring, AML triage, KYC transition checks.

These are self-contained (no cross-app import) so the MCP server stays a thin,
deployable tool layer. In production each tool delegates to the `aegis` services.
"""

from mcp.server import MCPServer


def _risk_level(value: float) -> str:
    if value >= 0.85:
        return "critical"
    if value >= 0.65:
        return "high"
    if value >= 0.4:
        return "medium"
    return "low"


def fraud_score(signals: dict) -> dict:
    """Score a transaction for fraud risk (0..1) with a level band."""
    raw = 0.0
    raw += 0.2 * min(1.0, signals.get("velocity_1h", 0) / 10.0)
    raw += 0.2 * min(1.0, signals.get("device_shared_count", 0) / 5.0)
    raw += 0.2 * (1.0 - min(1.0, signals.get("device_age_days", 365) / 365.0))
    raw += 0.2 * max(0.0, min(1.0, signals.get("merchant_risk", 0.0)))
    raw += 0.2 * min(1.0, signals.get("prior_chargebacks", 0) / 3.0)
    if signals.get("high_risk_jurisdiction"):
        raw = min(1.0, raw + 0.15)
    value = max(0.0, min(1.0, raw))
    return {"value": round(value, 4), "level": _risk_level(value)}


def aml_triage(signals: dict) -> dict:
    """Triage a transaction for AML risk and return a verdict."""
    raw = 0.0
    raw += 0.25 * min(1.0, signals.get("velocity_24h", 0) / 20.0)
    raw += 0.25 * (1.0 if signals.get("structuring") else 0.0)
    raw += 0.25 * (1.0 if signals.get("high_risk_jurisdiction") else 0.0)
    raw += 0.25 * min(1.0, signals.get("cash_intensity", 0.0))
    value = max(0.0, min(1.0, raw))
    if value >= 0.65:
        verdict = "escalate"
    elif value >= 0.4:
        verdict = "step_up"
    else:
        verdict = "approve"
    return {"value": round(value, 4), "level": _risk_level(value), "verdict": verdict}


_KYC_ALLOWED = {
    "pending": {"in_review", "rejected"},
    "in_review": {"verified", "rejected"},
    "verified": set(),
    "rejected": set(),
}


def kyc_transition(current: str, target: str) -> dict:
    """Validate a KYC status transition; returns ok + resulting status."""
    allowed = _KYC_ALLOWED.get(current)
    if allowed is None:
        return {"ok": False, "error": f"unknown status: {current}"}
    if target not in allowed:
        return {"ok": False, "error": f"cannot go {current} -> {target}"}
    return {"ok": True, "status": target}


def register(server: MCPServer) -> None:
    """Register all tools on the given MCP server."""

    server.tool()(fraud_score)
    server.tool()(aml_triage)
    server.tool()(kyc_transition)