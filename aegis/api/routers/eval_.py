"""Eval router: run regression suites and report the CI gate result."""

from __future__ import annotations

from fastapi import APIRouter

from aegis.agents import run_pipeline
from aegis.eval import build_smoke_suite
from aegis.models import Alert, AlertType, CaseState, Entity, RiskLevel

router = APIRouter()


def _answer(question: str) -> str:
    import asyncio

    e = Entity(legal_name="Acme")
    a = Alert(alert_type=AlertType.STRUCTURING, entity_id=__import__("uuid").uuid4(), severity=RiskLevel.HIGH, score=0.8)
    case = CaseState(alert_id=a.id, entity_id=e.id, alert=a, entity=e)
    asyncio.run(run_pipeline(case))
    return case.narrative or "no narrative"


@router.get("/eval/suites")
async def suites():
    return {"suites": ["smoke", "typology", "temporal", "groundedness"]}


@router.post("/eval/run")
async def run_suite(suite: str = "smoke", min_accuracy: float = 0.8):
    import asyncio

    def _run():
        s = build_smoke_suite()
        result = s.run(_answer)
        return result, s.gate(result, min_accuracy)

    result, passed = await asyncio.to_thread(_run)
    return {"result": result, "gate_passed": passed}