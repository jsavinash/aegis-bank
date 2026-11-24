"""Fraud scoring service."""

from ..models import RiskScore
from ..strategies import ModelScorer


class FraudService:
    def __init__(self, scorer: ModelScorer | None = None):
        self.scorer = scorer or ModelScorer()

    def score(self, signals: dict) -> RiskScore:
        return self.scorer.score(signals)

    def decide(self, signals: dict, step_up: float = 0.5, decline: float = 0.85) -> str:
        score = self.score(signals)
        if score.value >= decline:
            return "decline"
        if score.value >= step_up:
            return "step_up"
        return "approve"