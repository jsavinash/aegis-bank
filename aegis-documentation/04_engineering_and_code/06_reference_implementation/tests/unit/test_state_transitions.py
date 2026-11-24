from src.models import CaseStatus
from src.models.entities import Case
from src.exceptions import InvalidTransition
from uuid import uuid4

import pytest


def test_valid_transitions():
    c = Case(alert_id=uuid4())
    c.transition(CaseStatus.INVESTIGATING)
    c.transition(CaseStatus.PENDING_REVIEW)
    c.transition(CaseStatus.FILED)
    c.transition(CaseStatus.CLOSED)
    assert c.status == CaseStatus.CLOSED


def test_invalid_transitions():
    c = Case(alert_id=uuid4())
    with pytest.raises(InvalidTransition):
        c.transition(CaseStatus.CLOSED)


def test_dismiss_from_open():
    c = Case(alert_id=uuid4())
    c.transition(CaseStatus.DISMISSED)
    assert c.status == CaseStatus.DISMISSED