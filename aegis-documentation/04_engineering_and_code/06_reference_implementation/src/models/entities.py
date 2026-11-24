"""Domain entities."""

from dataclasses import dataclass, field
from datetime import datetime, timezone
from uuid import UUID, uuid4

from .enums import CaseStatus, RiskLevel
from .value_objects import Money, RiskScore


def _now() -> datetime:
    return datetime.now(timezone.utc)


@dataclass
class Customer:
    legal_name: str
    id: UUID = field(default_factory=uuid4)
    risk_rating: RiskLevel = RiskLevel.LOW
    kyc_status: str = "pending"


@dataclass
class Account:
    customer_id: UUID
    id: UUID = field(default_factory=uuid4)
    balance: Money = field(default_factory=lambda: Money(0.0))
    currency: str = "USD"


@dataclass
class Transaction:
    account_id: UUID
    amount: Money
    direction: str = "debit"
    id: UUID = field(default_factory=uuid4)
    occurred_at: datetime = field(default_factory=_now)


@dataclass
class Card:
    customer_id: UUID
    id: UUID = field(default_factory=uuid4)
    frozen: bool = False

    def freeze(self) -> None:
        self.frozen = True

    def unfreeze(self) -> None:
        self.frozen = False


@dataclass
class Case:
    alert_id: UUID
    id: UUID = field(default_factory=uuid4)
    status: CaseStatus = CaseStatus.OPEN
    risk_score: RiskScore | None = None
    narrative: str = ""
    created_at: datetime = field(default_factory=_now)

    def transition(self, target: CaseStatus) -> None:
        allowed = _ALLOWED[self.status]
        if target not in allowed:
            from ..exceptions import InvalidTransition

            raise InvalidTransition(f"cannot go {self.status.value} -> {target.value}")
        self.status = target


_ALLOWED: dict[CaseStatus, set[CaseStatus]] = {
    CaseStatus.OPEN: {CaseStatus.INVESTIGATING, CaseStatus.DISMISSED},
    CaseStatus.INVESTIGATING: {CaseStatus.PENDING_REVIEW, CaseStatus.FILED, CaseStatus.DISMISSED},
    CaseStatus.PENDING_REVIEW: {CaseStatus.INVESTIGATING, CaseStatus.FILED, CaseStatus.CLOSED},
    CaseStatus.FILED: {CaseStatus.CLOSED},
    CaseStatus.CLOSED: set(),
    CaseStatus.DISMISSED: set(),
}