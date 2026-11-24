from aegis.agents import run_pipeline
from aegis.eval import build_smoke_suite
from aegis.models import Alert, AlertType, CaseState, Entity, RiskLevel


def _answer(q: str) -> str:
    import asyncio

    e = Entity(legal_name="Acme")
    a = Alert(alert_type=AlertType.STRUCTURING, entity_id=e.id, severity=RiskLevel.HIGH, score=0.8)
    c = CaseState(alert_id=a.id, entity_id=e.id, alert=a, entity=e)
    asyncio.run(run_pipeline(c))
    return c.narrative or "no narrative"


def test_smoke_suite_runs():
    s = build_smoke_suite()
    result = s.run(_answer)
    assert result["n"] == 4
    assert "accuracy" in result


def test_suite_gate_low_threshold_passes():
    s = build_smoke_suite()
    result = s.run(lambda q: "ok")
    assert s.gate(result, min_accuracy=0.0) is True