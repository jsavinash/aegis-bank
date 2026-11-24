"""Case service with per-case locking for concurrency safety."""

import asyncio
from uuid import UUID

from ..models import Case, CaseStatus
from ..models.value_objects import RiskScore


class CaseService:
    """Manages case state with a per-case asyncio lock to serialize updates."""

    def __init__(self) -> None:
        self._cases: dict[UUID, Case] = {}
        self._locks: dict[UUID, asyncio.Lock] = {}

    def _lock(self, case_id: UUID) -> asyncio.Lock:
        return self._locks.setdefault(case_id, asyncio.Lock())

    def create(self, alert_id: UUID) -> Case:
        case = Case(alert_id=alert_id)
        self._cases[case.id] = case
        return case

    async def transition(self, case_id: UUID, target: CaseStatus) -> Case:
        async with self._lock(case_id):
            case = self._cases[case_id]
            case.transition(target)
            return case

    async def set_risk(self, case_id: UUID, score: RiskScore) -> Case:
        async with self._lock(case_id):
            case = self._cases[case_id]
            case.risk_score = score
            return case

    def get(self, case_id: UUID) -> Case:
        return self._cases[case_id]