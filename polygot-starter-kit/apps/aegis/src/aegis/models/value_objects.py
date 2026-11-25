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
        if len(self.currency) != 3 or not self.currency.isalpha():
            raise ValueError("currency must be a 3-letter ISO code")
        object.__setattr__(self, "currency", self.currency.upper())


@dataclass(frozen=True)
class RiskScore:
    value: float
    level: RiskLevel

    @classmethod
    def from_value(cls, value: float) -> "RiskScore":
        value = max(0.0, min(1.0, value))
        if value >= 0.85:
            level = RiskLevel.CRITICAL
        elif value >= 0.65:
            level = RiskLevel.HIGH
        elif value >= 0.4:
            level = RiskLevel.MEDIUM
        else:
            level = RiskLevel.LOW
        return cls(value=value, level=level)