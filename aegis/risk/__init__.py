"""Real-time risk engine: hot path (deterministic) + warm path (agentic)."""

from __future__ import annotations

from dataclasses import dataclass, field

from aegis.models import RiskLevel, Verdict
from aegis.telemetry import get_logger

log = get_logger("risk")


@dataclass
class RiskSignals:
    amount: float = 0.0
    velocity_1h: int = 0
    geo_velocity_kmh: float = 0.0
    device_age_days: int = 365
    device_shared_count: int = 0
    merchant_risk: float = 0.0
    customer_tenure_days: int = 365
    prior_chargebacks: int = 0
    high_risk_jurisdiction: bool = False


@dataclass
class RiskScore:
    score: float = 0.0
    level: RiskLevel = RiskLevel.LOW
    drivers: list[str] = field(default_factory=list)

    def as_dict(self) -> dict:
        return {"score": round(self.score, 4), "level": self.level.value, "drivers": self.drivers}


class HotPathScorer:
    """Sub-150ms deterministic scorer. NO LLM in this path."""

    WEIGHTS = {
        "velocity": 0.22,
        "geo": 0.15,
        "device": 0.20,
        "shared_device": 0.18,
        "merchant": 0.15,
        "chargebacks": 0.10,
    }

    def score(self, s: RiskSignals) -> RiskScore:
        drivers: list[str] = []
        v = min(1.0, s.velocity_1h / 10.0)
        if v > 0.5:
            drivers.append("high_velocity")
        g = min(1.0, s.geo_velocity_kmh / 1000.0)
        if g > 0.4:
            drivers.append("geo_impossible")
        d = 1.0 - min(1.0, s.device_age_days / 365.0)
        if d > 0.7:
            drivers.append("new_device")
        sd = min(1.0, s.device_shared_count / 5.0)
        if sd > 0.4:
            drivers.append("device_farm")
        m = max(0.0, min(1.0, s.merchant_risk))
        if m > 0.6:
            drivers.append("risky_merchant")
        cb = min(1.0, s.prior_chargebacks / 3.0)
        if cb > 0.3:
            drivers.append("chargeback_history")

        raw = (
            self.WEIGHTS["velocity"] * v
            + self.WEIGHTS["geo"] * g
            + self.WEIGHTS["device"] * d
            + self.WEIGHTS["shared_device"] * sd
            + self.WEIGHTS["merchant"] * m
            + self.WEIGHTS["chargebacks"] * cb
        )
        if s.high_risk_jurisdiction:
            raw = min(1.0, raw + 0.15)
            drivers.append("high_risk_jurisdiction")
        score = max(0.0, min(1.0, raw))
        level = (
            RiskLevel.CRITICAL
            if score >= 0.85
            else RiskLevel.HIGH
            if score >= 0.65
            else RiskLevel.MEDIUM
            if score >= 0.4
            else RiskLevel.LOW
        )
        return RiskScore(score=score, level=level, drivers=drivers)


def decide(score: RiskScore, step_up_threshold: float = 0.5, decline_threshold: float = 0.85) -> Verdict:
    if score.score >= decline_threshold:
        return Verdict.DECLINE
    if score.score >= step_up_threshold:
        return Verdict.STEP_UP
    return Verdict.APPROVE


def route(score: RiskScore) -> str:
    """Hot path vs warm path routing."""
    if score.level in (RiskLevel.LOW,):
        return "hot"
    if score.level in (RiskLevel.MEDIUM,):
        return "warm"
    return "hot_decline"