"""Core domain models — the typed vocabulary of the platform."""

from __future__ import annotations

from datetime import UTC, datetime
from enum import StrEnum
from typing import Any
from uuid import UUID, uuid4

from pydantic import BaseModel, Field


def utcnow() -> datetime:
    return datetime.now(UTC)


class RiskLevel(StrEnum):
    LOW = "low"
    MEDIUM = "medium"
    HIGH = "high"
    CRITICAL = "critical"


class CaseStatus(StrEnum):
    OPEN = "open"
    INVESTIGATING = "investigating"
    PENDING_REVIEW = "pending_review"
    ESCALATED = "escalated"
    FILED = "filed"
    CLOSED = "closed"
    DISMISSED = "dismissed"


class AlertType(StrEnum):
    STRUCTURING = "structuring"
    RAPID_MOVEMENT = "rapid_movement"
    HIGH_RISK_JURISDICTION = "high_risk_jurisdiction"
    SANCTIONS_NEAR_MISS = "sanctions_near_miss"
    MULE_NETWORK = "mule_network"
    TRADE_BASED = "trade_based"
    KYC_STALE = "kyc_stale"
    VELOCITY = "velocity"


class Verdict(StrEnum):
    APPROVE = "approve"
    STEP_UP = "step_up"
    DECLINE = "decline"
    ESCALATE = "escalate"
    FILE_SAR = "file_sar"


class Entity(BaseModel):
    id: UUID = Field(default_factory=uuid4)
    legal_name: str
    entity_type: str = "person"
    jurisdiction: str = "US"
    risk_rating: RiskLevel = RiskLevel.LOW
    kyc_status: str = "verified"
    pep: bool = False
    sanctions_hits: int = 0
    created_at: datetime = Field(default_factory=utcnow)
    metadata: dict[str, Any] = Field(default_factory=dict)


class Transaction(BaseModel):
    id: UUID = Field(default_factory=uuid4)
    entity_id: UUID
    amount: float
    currency: str = "USD"
    direction: str = "debit"
    channel: str = "ach"
    counterparty_id: UUID | None = None
    counterparty_name: str | None = None
    jurisdiction: str = "US"
    occurred_at: datetime = Field(default_factory=utcnow)
    memo: str | None = None
    metadata: dict[str, Any] = Field(default_factory=dict)


class Evidence(BaseModel):
    id: UUID = Field(default_factory=uuid4)
    case_id: UUID
    source: str
    ref: str
    content: str
    score: float = 0.0
    collected_at: datetime = Field(default_factory=utcnow)
    metadata: dict[str, Any] = Field(default_factory=dict)


class Alert(BaseModel):
    id: UUID = Field(default_factory=uuid4)
    alert_type: AlertType
    entity_id: UUID
    severity: RiskLevel = RiskLevel.MEDIUM
    score: float = 0.0
    rationale: str = ""
    transaction_ids: list[UUID] = Field(default_factory=list)
    created_at: datetime = Field(default_factory=utcnow)
    metadata: dict[str, Any] = Field(default_factory=dict)


class TypologyHypothesis(BaseModel):
    code: str
    label: str
    confidence: float = Field(ge=0.0, le=1.0)
    rationale: str = ""
    decisive_evidence: list[str] = Field(default_factory=list)


class CaseState(BaseModel):
    case_id: UUID = Field(default_factory=uuid4)
    alert_id: UUID
    entity_id: UUID
    status: CaseStatus = CaseStatus.OPEN
    alert: Alert | None = None
    entity: Entity | None = None
    evidence: list[Evidence] = Field(default_factory=list)
    hypotheses: list[TypologyHypothesis] = Field(default_factory=list)
    narrative: str = ""
    risk_score: float = 0.0
    qa_notes: list[str] = Field(default_factory=list)
    approvals: list[dict[str, Any]] = Field(default_factory=list)
    citations: list[dict[str, str]] = Field(default_factory=list)
    created_at: datetime = Field(default_factory=utcnow)
    updated_at: datetime = Field(default_factory=utcnow)

    def touch(self) -> CaseState:
        self.updated_at = utcnow()
        return self


class SARField(BaseModel):
    field: str
    value: Any
    citations: list[str] = Field(default_factory=list)


class SARDraft(BaseModel):
    case_id: UUID
    subject: str
    amount_total: float
    typology_codes: list[str]
    narrative: str
    fields: list[SARField] = Field(default_factory=list)
    confidence: float = 0.0
    grounded: bool = True
    created_at: datetime = Field(default_factory=utcnow)


class Obligation(BaseModel):
    id: UUID = Field(default_factory=uuid4)
    source: str
    reference: str
    title: str
    text: str
    clause_path: str = ""
    in_force_from: datetime | None = None
    superseded_by: UUID | None = None
    obligations_links: list[UUID] = Field(default_factory=list)
    created_at: datetime = Field(default_factory=utcnow)


class Control(BaseModel):
    id: UUID = Field(default_factory=uuid4)
    name: str
    description: str
    owner: str = ""
    code_ref: str = ""
    mapped_obligation_ids: list[UUID] = Field(default_factory=list)
    last_reviewed: datetime | None = None


class Gap(BaseModel):
    id: UUID = Field(default_factory=uuid4)
    obligation_id: UUID
    control_id: UUID | None = None
    severity: RiskLevel
    compliant: bool
    rationale: str
    evidence_spans: list[str] = Field(default_factory=list)
    proposed_remediation: str = ""
    created_at: datetime = Field(default_factory=utcnow)


class AuditEvent(BaseModel):
    id: UUID = Field(default_factory=uuid4)
    case_id: UUID | None = None
    actor: str
    action: str
    detail: dict[str, Any] = Field(default_factory=dict)
    model: str | None = None
    prompt_version: str | None = None
    judge_score: float | None = None
    created_at: datetime = Field(default_factory=utcnow)