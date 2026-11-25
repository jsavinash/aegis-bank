"""Fraud scoring and decisioning service (hot path)."""

from ..models.enums import Verdict
from ..models.value_objects import RiskScore
from ..strategies.risk_strategy import ModelScorer, RiskScorer


class FraudService:
    def __init__(self, scorer: RiskScorer | None = None) -> None:
        self.scorer = scorer or ModelScorer()

    def score(self, signals: dict) -> RiskScore:
        return self.scorer.score(signals)

    def decide(
        self, signals: dict, step_up: float = 0.5, decline: float = 0.85
    ) -> Verdict:
        score = self.score(signals)
        if score.value >= decline:
            return Verdict.DECLINE
        if score.value >= step_up:
            return Verdict.STEP_UP
        return Verdict.APPROVE