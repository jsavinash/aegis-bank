"""KYC / KYB & customer due diligence workflow."""

from dataclasses import dataclass, field
from uuid import UUID, uuid4

from ..exceptions.base import AegisError
from ..models.enums import KycStatus

_ALLOWED: dict[KycStatus, set[KycStatus]] = {
    KycStatus.PENDING: {KycStatus.IN_REVIEW, KycStatus.REJECTED},
    KycStatus.IN_REVIEW: {KycStatus.VERIFIED, KycStatus.REJECTED},
    KycStatus.VERIFIED: set(),
    KycStatus.REJECTED: set(),
}


@dataclass
class KycCase:
    customer_id: str
    id: UUID = field(default_factory=uuid4)
    status: KycStatus = KycStatus.PENDING
    documents: list[str] = field(default_factory=list)

    def transition(self, target: KycStatus) -> None:
        if target not in _ALLOWED[self.status]:
            raise AegisError(
                f"cannot go {self.status.value} -> {target.value}"
            )
        self.status = target


class KycService:
    def __init__(self) -> None:
        self._cases: dict[UUID, KycCase] = {}

    def start(self, customer_id: str) -> KycCase:
        case = KycCase(customer_id=customer_id)
        self._cases[case.id] = case
        return case

    def add_document(self, case_id: UUID, document: str) -> KycCase:
        case = self._require(case_id)
        case.documents.append(document)
        return case

    def review(self, case_id: UUID) -> KycCase:
        case = self._require(case_id)
        case.transition(KycStatus.IN_REVIEW)
        return case

    def verify(self, case_id: UUID) -> KycCase:
        case = self._require(case_id)
        case.transition(KycStatus.VERIFIED)
        return case

    def reject(self, case_id: UUID) -> KycCase:
        case = self._require(case_id)
        case.transition(KycStatus.REJECTED)
        return case

    def get(self, case_id: UUID) -> KycCase:
        return self._require(case_id)

    def list(self) -> list[KycCase]:
        return list(self._cases.values())

    def _require(self, case_id: UUID) -> KycCase:
        case = self._cases.get(case_id)
        if case is None:
            raise AegisError(f"kyc case not found: {case_id}")
        return case