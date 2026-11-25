import asyncio

import pytest

from aegis.exceptions.base import CaseNotFound, InvalidTransition
from aegis.models.entities import Case
from aegis.models.enums import CaseStatus
from aegis.models.value_objects import Money, RiskScore
from aegis.services.case_service import CaseService
from aegis.services.fraud_service import FraudService
from aegis.strategies.risk_strategy import RulesScorer


def test_money_rejects_negative():
    with pytest.raises(ValueError):
        Money(-1.0)


def test_risk_score_levels():
    assert RiskScore.from_value(0.9).level.value == "critical"
    assert RiskScore.from_value(0.7).level.value == "high"
    assert RiskScore.from_value(0.5).level.value == "medium"
    assert RiskScore.from_value(0.1).level.value == "low"


def test_fraud_decide_thresholds():
    svc = FraudService(RulesScorer())
    assert svc.decide({}).value == "approve"
    assert svc.decide({"high_risk_jurisdiction": True}).value in {
        "approve",
        "step_up",
        "decline",
    }


def test_case_state_machine():
    case = Case(alert_id=__import__("uuid").uuid4())
    case.transition(CaseStatus.INVESTIGATING)
    case.transition(CaseStatus.FILED)
    case.transition(CaseStatus.CLOSED)
    with pytest.raises(InvalidTransition):
        case.transition(CaseStatus.INVESTIGATING)


def test_case_service_locking_and_not_found():
    svc = CaseService()
    case = svc.create(__import__("uuid").uuid4())

    async def run():
        await svc.transition(case.id, CaseStatus.INVESTIGATING)
        await svc.set_risk(case.id, RiskScore.from_value(0.5))

    asyncio.run(run())
    assert svc.get(case.id).status == CaseStatus.INVESTIGATING

    with pytest.raises(CaseNotFound):
        svc.get(__import__("uuid").uuid4())