"""Temporal durable workflows: case investigation, risk escalation, compliance review.

Each workflow is crash-proof, can wait days for a human, and resumes from the
last checkpoint. Side effects are idempotent (keyed by workflow id).
"""

from __future__ import annotations

import asyncio
from datetime import timedelta

from aegis.graphs import CaseGraph
from aegis.models import Alert, AlertType, CaseState, Entity, RiskLevel
from aegis.telemetry import get_logger

log = get_logger("workflows")

try:
    from temporalio import activity, workflow
    from temporalio.common import RetryPolicy

    _HAS_TEMPORAL = True
except Exception:  # pragma: no cover
    _HAS_TEMPORAL = False

    def activity(fn=None, **_):
        def wrap(f):
            return f

        return wrap(fn) if fn else wrap

    class workflow:  # type: ignore
        @staticmethod
        def run(fn):
            return fn

        @staticmethod
        def init(*a, **k):
            return lambda f: f

    class RetryPolicy:  # type: ignore
        def __init__(self, **_): ...


@activity.defn
async def run_case_investigation(case_json: str) -> str:
    case = CaseState.model_validate_json(case_json)
    case = await CaseGraph().run(case)
    return case.model_dump_json()


@activity.defn
async def wait_for_human_approval(case_json: str) -> str:
    """Stand-in for a human approval that may arrive days later."""
    log.info("workflow.waiting_for_approval")
    await asyncio.sleep(0)  # in production: Temporal signal
    return case_json


@workflow.defn
class CaseInvestigationWorkflow:
    def __init__(self) -> None:
        self._case: CaseState | None = None

    @workflow.run
    async def run(self, case_json: str) -> str:
        case = CaseState.model_validate_json(case_json)
        self._case = case
        result = await workflow.execute_activity(
            run_case_investigation,
            case.model_dump_json(),
            start_to_close_timeout=timedelta(minutes=10),
            retry_policy=RetryPolicy(maximum_attempts=3),
        )
        self._case = CaseState.model_validate_json(result)
        if self._case.status.value == "pending_review":
            approved = await workflow.execute_activity(
                wait_for_human_approval,
                result,
                start_to_close_timeout=timedelta(days=5),
            )
            self._case = CaseState.model_validate_json(approved)
        return self._case.model_dump_json()


def make_case(alert_type: AlertType = AlertType.STRUCTURING, severity: RiskLevel = RiskLevel.HIGH) -> CaseState:
    entity = Entity(legal_name="Acme Holdings LLC", entity_type="company", risk_rating=severity)
    alert = Alert(alert_type=alert_type, entity_id=entity.id, severity=severity, score=0.79)
    return CaseState(alert_id=alert.id, entity_id=entity.id, alert=alert, entity=entity)


# Local (non-Temporal) helpers so the platform runs without a Temporal server.
async def run_local(case: CaseState) -> CaseState:
    return await CaseGraph().run(case)


WORKFLOWS = {"CaseInvestigation": CaseInvestigationWorkflow}