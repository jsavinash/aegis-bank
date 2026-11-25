"""Domain enumerations for the Aegis AI plane."""

from enum import StrEnum


class RiskLevel(StrEnum):
    LOW = "low"
    MEDIUM = "medium"
    HIGH = "high"
    CRITICAL = "critical"


class CaseStatus(StrEnum):
    OPEN = "open"
    INVESTIGATING = "investigating"
    PENDING_REVIEW = "pending_review"
    FILED = "filed"
    CLOSED = "closed"
    DISMISSED = "dismissed"


class Verdict(StrEnum):
    APPROVE = "approve"
    STEP_UP = "step_up"
    DECLINE = "decline"
    ESCALATE = "escalate"


class AlertStatus(StrEnum):
    OPEN = "open"
    CONFIRMED = "confirmed"
    DISMISSED = "dismissed"


class KycStatus(StrEnum):
    PENDING = "pending"
    IN_REVIEW = "in_review"
    VERIFIED = "verified"
    REJECTED = "rejected"


class AmlRisk(StrEnum):
    LOW = "low"
    MEDIUM = "medium"
    HIGH = "high"