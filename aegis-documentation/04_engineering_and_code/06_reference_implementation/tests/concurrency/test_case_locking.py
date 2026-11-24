import asyncio
from uuid import uuid4

import pytest

from src.models import CaseStatus
from src.services import CaseService


@pytest.mark.asyncio
async def test_concurrent_transitions_are_serialized():
    """Concurrent transitions on the same case must not corrupt state."""
    svc = CaseService()
    case = svc.create(uuid4())

    results = await asyncio.gather(
        svc.transition(case.id, CaseStatus.INVESTIGATING),
        svc.transition(case.id, CaseStatus.PENDING_REVIEW),
        return_exceptions=True,
    )
    # At least one should succeed and the other either succeeds or raises InvalidTransition,
    # but state must never be corrupted.
    final = svc.get(case.id)
    assert final.status in (CaseStatus.INVESTIGATING, CaseStatus.PENDING_REVIEW)
    assert any(not isinstance(r, Exception) for r in results)