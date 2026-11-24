"""Value objects with validation."""

from dataclasses import dataclass

from .enums import RiskLevel


@dataclass(frozen=True)
class Money:
    amount: float
    currency: str = "USD"

    def __post_init__(self) -> None:
        if self.amount < 0:
            raise ValueError("amount must be non-negative")
        if len(self.currency) != 3:
            raise ValueError("currency must be a 3-letter ISO code")


@dataclass(frozen=True)
class RiskScore:
    value: float
    level: RiskLevel

    @classmethod
    def from_value(cls, value: float) -> "RiskScore":
        value = max(0.0, min(1.0, value))
        level = (
            RiskLevel.CRITICAL
            if value >= 0.85
            else RiskLevel.HIGH
            if value >= 0.65
            else RiskLevel.MEDIUM
            if value >= 0.4
            else RiskLevel.LOW
        )
        return cls(value=value, level=level)