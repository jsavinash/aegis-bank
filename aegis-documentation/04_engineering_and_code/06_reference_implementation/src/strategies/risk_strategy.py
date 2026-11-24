"""Risk-scoring strategy (rules vs model)."""

from typing import Protocol

from ..models import RiskScore


class RiskScorer(Protocol):
    def score(self, signals: dict) -> RiskScore: ...


class RulesScorer:
    """Deterministic rule-based scorer (fallback / hot path)."""

    def score(self, signals: dict) -> RiskScore:
        raw = 0.0
        raw += 0.2 * min(1.0, signals.get("velocity_1h", 0) / 10.0)
        raw += 0.2 * min(1.0, signals.get("device_shared_count", 0) / 5.0)
        raw += 0.2 * (1.0 - min(1.0, signals.get("device_age_days", 365) / 365.0))
        raw += 0.2 * max(0.0, min(1.0, signals.get("merchant_risk", 0.0)))
        raw += 0.2 * min(1.0, signals.get("prior_chargebacks", 0) / 3.0)
        if signals.get("high_risk_jurisdiction"):
            raw = min(1.0, raw + 0.15)
        return RiskScore.from_value(raw)


class ModelScorer:
    """Placeholder for a trained GNN/GBM model (returns rules for now)."""

    def __init__(self, fallback: RiskScorer | None = None):
        self.fallback = fallback or RulesScorer()

    def score(self, signals: dict) -> RiskScore:
        return self.fallback.score(signals)