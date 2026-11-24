"""Domain enumerations."""

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