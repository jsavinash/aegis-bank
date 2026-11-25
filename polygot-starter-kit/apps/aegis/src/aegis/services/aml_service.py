"""AML transaction monitoring: score + triage."""

from dataclasses import dataclass

from ..models.enums import AmlRisk, Verdict
from ..models.value_objects import Money, RiskScore


@dataclass(frozen=True)
class Alert:
    transaction_id: str
    customer_id: str
    amount: Money
    risk: RiskScore
    verdict: Verdict


class AmlService:
    """Scores transactions for money-laundering risk and triages alerts.

    Deterministic rules (structuring, high-risk jurisdiction, velocity) stand in
    for a trained model; the shape matches the production monitoring agent.
    """

    def score(self, signals: dict) -> RiskScore:
        raw = 0.0
        raw += 0.25 * min(1.0, signals.get("velocity_24h", 0) / 20.0)
        raw += 0.25 * (1.0 if signals.get("structuring") else 0.0)
        raw += 0.25 * (1.0 if signals.get("high_risk_jurisdiction") else 0.0)
        raw += 0.25 * min(1.0, signals.get("cash_intensity", 0.0))
        return RiskScore.from_value(raw)

    def triage(self, signals: dict) -> Alert:
        score = self.score(signals)
        if score.value >= 0.65:
            verdict = Verdict.ESCALATE
        elif score.value >= 0.4:
            verdict = Verdict.STEP_UP
        else:
            verdict = Verdict.APPROVE
        risk = {
            "low": AmlRisk.LOW,
            "medium": AmlRisk.MEDIUM,
            "high": AmlRisk.HIGH,
            "critical": AmlRisk.HIGH,
        }[score.level.value]
        return Alert(
            transaction_id=str(signals.get("transaction_id", "")),
            customer_id=str(signals.get("customer_id", "")),
            amount=Money(signals.get("amount", 0.0), signals.get("currency", "USD")),
            risk=score,
            verdict=verdict,
        )