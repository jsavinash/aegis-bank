"""Specialist agents. Each is a thin, testable unit over the LLM + tools + guardrails."""

from __future__ import annotations

import json
from typing import Any

from pydantic import BaseModel, Field

from aegis.guardrails import get_guardrails
from aegis.llm import Budget, get_router, prompts, two_pass
from aegis.models import (
    CaseState,
    Evidence,
    RiskLevel,
    TypologyHypothesis,
    Verdict,
)
from aegis.telemetry import get_logger
from aegis.tools import registry

log = get_logger("agents")


class AgentResult(BaseModel):
    ok: bool = True
    text: str = ""
    data: dict[str, Any] = Field(default_factory=dict)


class BaseAgent:
    role: str = "supervisor"
    tier: str = "reasoning"

    def __init__(self, budget: Budget | None = None):
        self.budget = budget or Budget()
        self.llm = get_router().get(self.tier)
        self.guardrails = get_guardrails()

    async def run(self, case: CaseState, context: str = "") -> AgentResult:
        raise NotImplementedError

    async def _gather(self, role: str, instructions: str, tools: list[str]) -> dict:
        calls: dict[str, Any] = {}
        for name in tools:
            args = _infer_args(role, name, instructions)
            res = self.guardrails.check_tool_call(role, name, args)
            if not res.passed and self.guardrails.cfg.fail_closed:
                calls[name] = {"error": res.reason}
                continue
            calls[name] = await registry.call(role, name, args)
        return calls


def _infer_args(role: str, tool: str, context: str) -> dict:
    ctx = context.lower()
    if "device" in tool:
        for tok in ctx.split():
            if tok.startswith("dev-"):
                return {"device_id": tok}
        return {"device_id": "dev-unknown"}
    if tool in ("get_entity", "get_customer_360", "check_chargeback_history", "get_kyc", "get_prior_sars"):
        for tok in ctx.split():
            if tok.startswith("entity-") or tok.startswith("acct-") or tok.startswith("cust-"):
                return {"entity_id": tok}
        return {"entity_id": "entity-1"}
    if "name" in tool or tool == "screen_watchlist":
        return {"name": "Acme Holdings LLC"}
    if tool == "query_graph_neighbourhood":
        return {"entity_id": "entity-1", "depth": 2}
    if tool == "search_policy":
        return {"query": context[:120]}
    return {}


class EvidenceGatherer(BaseAgent):
    role = "evidence"
    tier = "fast"

    TOOLS = ["get_transactions", "get_kyc", "screen_watchlist", "get_prior_sars"]

    async def run(self, case: CaseState, context: str = "") -> AgentResult:
        get_guardrails().enforce(get_guardrails().check_input(context))
        calls = await self._gather("evidence", context, self.TOOLS)
        evidence: list[Evidence] = []
        for source, payload in calls.items():
            evidence.append(
                Evidence(
                    case_id=case.case_id,
                    source=source.split("_")[0] if "_" in source else source,
                    ref=source,
                    content=json.dumps(payload, default=str)[:2000],
                    score=0.8,
                )
            )
        case.evidence.extend(evidence)
        return AgentResult(text=f"collected {len(evidence)} evidence items", data={"calls": calls})


class TypologyAnalyst(BaseAgent):
    role = "typology"
    tier = "reasoning"

    class Output(BaseModel):
        hypotheses: list[TypologyHypothesis]

    async def run(self, case: CaseState, context: str = "") -> AgentResult:
        evidence_blob = "\n".join(e.content for e in case.evidence)[:4000]
        msgs = [
            prompts.system("typology"),
            prompts.user(f"Alert: {case.alert.alert_type if case.alert else 'unknown'}\nEvidence:\n{evidence_blob}"),
        ]
        out = await two_pass(
            self.llm,
            msgs,
            [prompts.user("Return JSON: {hypotheses:[{code,label,confidence,rationale,decisive_evidence}]}")],
            self.Output,
            self.budget,
        )
        case.hypotheses = out.hypotheses
        return AgentResult(text=f"{len(out.hypotheses)} hypotheses", data={"hypotheses": [h.model_dump() for h in out.hypotheses]})


class NetworkAnalyst(BaseAgent):
    role = "network"
    tier = "reasoning"

    async def run(self, case: CaseState, context: str = "") -> AgentResult:
        calls = await self._gather("network", context, ["query_graph_neighbourhood", "get_entity"])
        return AgentResult(text="network mapped", data={"calls": calls})


class NarrativeWriter(BaseAgent):
    role = "narrative"
    tier = "reasoning"

    class Output(BaseModel):
        narrative: str
        citations: list[dict[str, str]] = Field(default_factory=list)

    async def run(self, case: CaseState, context: str = "") -> AgentResult:
        evidence_blob = "\n".join(f"[{e.ref}] {e.content}" for e in case.evidence)[:6000]
        hyps = ", ".join(h.code for h in case.hypotheses) or "unknown"
        msgs = [
            prompts.system("narrative", typologies=hyps),
            prompts.user(
                f"Write the SAR narrative. Cite evidence as [ref].\n"
                f"Typologies: {hyps}\nEvidence:\n{evidence_blob}"
            ),
        ]
        out = await two_pass(
            self.llm,
            msgs,
            [prompts.user("Return JSON: {narrative, citations:[{claim,ref}]}")],
            self.Output,
            self.budget,
        )
        res = self.guardrails.check_output(out.narrative, require_citations=True)
        if not res.passed and self.guardrails.cfg.fail_closed:
            return AgentResult(ok=False, text=f"narrative blocked: {res.reason}", data={"guardrail": res.rule})
        case.narrative = out.narrative
        case.citations = out.citations
        return AgentResult(text="narrative written", data={"citations": out.citations})


class QAReviewer(BaseAgent):
    role = "qa"
    tier = "reasoning"

    class Output(BaseModel):
        approved: bool
        confidence: float
        notes: list[str] = Field(default_factory=list)

    APPROVAL_THRESHOLD = 0.85

    async def run(self, case: CaseState, context: str = "") -> AgentResult:
        msgs = [
            prompts.system("qa"),
            prompts.user(f"Review this SAR narrative:\n{case.narrative}\n\nTypologies: {[h.code for h in case.hypotheses]}"),
        ]
        out = await two_pass(
            self.llm,
            msgs,
            [prompts.user("Return JSON: {approved,confidence,notes}")],
            self.Output,
            self.budget,
        )
        case.qa_notes = out.notes
        case.risk_score = out.confidence
        approved = out.approved and out.confidence >= self.APPROVAL_THRESHOLD
        return AgentResult(ok=approved, text="approved" if approved else "needs review", data=out.model_dump())


class RiskAnalyst(BaseAgent):
    role = "risk"
    tier = "reasoning"

    class Output(BaseModel):
        decision: Verdict
        score: float
        typology: list[str] = Field(default_factory=list)
        rationale: str
        evidence_spans: list[str] = Field(default_factory=list)

    async def run(self, case: CaseState, context: str = "") -> AgentResult:
        calls = await self._gather("risk", context, ["get_customer_360", "query_graph_neighbourhood", "check_chargeback_history"])
        msgs = [
            prompts.system("risk"),
            prompts.user(f"Structured risk object:\n{case.model_dump_json()[:4000]}\nContext:\n{context}\nTool results:\n{json.dumps(calls, default=str)[:4000]}"),
        ]
        out = await two_pass(
            self.llm,
            msgs,
            [prompts.user("Return JSON: {decision,score,typology,rationale,evidence_spans}")],
            self.Output,
            self.budget,
        )
        get_guardrails().enforce(get_guardrails().check_output(out.rationale, require_citations=bool(out.evidence_spans)))
        return AgentResult(text=out.decision.value, data=out.model_dump())


class GapAnalyst(BaseAgent):
    role = "gap"
    tier = "reasoning"

    class Output(BaseModel):
        compliant: bool
        severity: RiskLevel
        rationale: str
        evidence_spans: list[str] = Field(default_factory=list)
        proposed_remediation: str = ""

    async def run(self, case: CaseState, context: str = "") -> AgentResult:
        calls = await self._gather("compliance", context, ["search_obligations", "get_control_library", "diff_policy_version"])
        msgs = [
            prompts.system("gap"),
            prompts.user(f"Obligation/control context:\n{json.dumps(calls, default=str)[:4000]}"),
        ]
        out = await two_pass(
            self.llm,
            msgs,
            [prompts.user("Return JSON: {compliant,severity,rationale,evidence_spans,proposed_remediation}")],
            self.Output,
            self.budget,
        )
        return AgentResult(text="compliant" if out.compliant else "gap", data=out.model_dump())


async def run_pipeline(case: CaseState) -> CaseState:
    """Sequential pipeline used by tests and the non-Temporal path."""
    ctx = f"entity-1 alert {case.alert.alert_type.value if case.alert else 'unknown'}"
    await EvidenceGatherer().run(case, ctx)
    await TypologyAnalyst().run(case, ctx)
    await NetworkAnalyst().run(case, ctx)
    await NarrativeWriter().run(case, ctx)
    qa = await QAReviewer().run(case, ctx)
    case.status = case.status.PENDING_REVIEW if not qa.ok else case.status.INVESTIGATING
    return case


__all__ = [
    "BaseAgent",
    "AgentResult",
    "EvidenceGatherer",
    "TypologyAnalyst",
    "NetworkAnalyst",
    "NarrativeWriter",
    "QAReviewer",
    "RiskAnalyst",
    "GapAnalyst",
    "run_pipeline",
]