from uuid import uuid4

import pytest

from src.models import CaseStatus
from src.services import CaseService


@pytest.mark.asyncio
async def test_case_lifecycle():
    svc = CaseService()
    case = svc.create(uuid4())
    assert case.status == CaseStatus.OPEN

    await svc.transition(case.id, CaseStatus.INVESTIGATING)
    await svc.transition(case.id, CaseStatus.PENDING_REVIEW)
    await svc.transition(case.id, CaseStatus.FILED)
    assert svc.get(case.id).status == CaseStatus.FILED


@pytest.mark.asyncio
async def test_invalid_transition_raises():
    from src.exceptions import InvalidTransition

    svc = CaseService()
    case = svc.create(uuid4())
    with pytest.raises(InvalidTransition):
        await svc.transition(case.id, CaseStatus.FILED)  # OPEN -> FILED is illegal