from aegis.agents import (
    EvidenceGatherer,
    NarrativeWriter,
    QAReviewer,
    TypologyAnalyst,
    run_pipeline,
)
from aegis.models import Alert, AlertType, CaseState, Entity, RiskLevel


def _case():
    e = Entity(legal_name="Acme")
    a = Alert(alert_type=AlertType.STRUCTURING, entity_id=e.id, severity=RiskLevel.HIGH, score=0.8)
    return CaseState(alert_id=a.id, entity_id=e.id, alert=a, entity=e)


def test_evidence_gatherer_collects():
    c = _case()
    import asyncio

    res = asyncio.run(EvidenceGatherer().run(c, "entity-1 alert structuring"))
    assert res.ok and len(c.evidence) >= 3


def test_full_pipeline_produces_narrative():
    c = _case()
    import asyncio

    out = asyncio.run(run_pipeline(c))
    assert out.narrative
    assert len(out.evidence) > 0
    assert len(out.hypotheses) > 0


def test_narrative_requires_citations():
    from aegis.guardrails import get_guardrails

    g = get_guardrails()
    res = g.check_output("some narrative without citations at all", require_citations=True)
    assert not res.passed and res.rule == "ungrounded"
    assert g.check_output("Structuring indicated. [t1][t2]", require_citations=True).passed