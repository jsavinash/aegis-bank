"""Risk router: real-time scoring + warm-path analyst verdict."""

from __future__ import annotations

from fastapi import APIRouter
from pydantic import BaseModel

from aegis.agents import RiskAnalyst
from aegis.models import CaseState, Entity
from aegis.risk import HotPathScorer, RiskSignals, decide, route

router = APIRouter()
_scorer = HotPathScorer()


class RiskRequest(BaseModel):
    amount: float = 1840.0
    velocity_1h: int = 3
    geo_velocity_kmh: float = 0.0
    device_age_days: int = 5
    device_shared_count: int = 4
    merchant_risk: float = 0.7
    customer_tenure_days: int = 90
    prior_chargebacks: int = 0
    high_risk_jurisdiction: bool = False


@router.post("/risk/score")
async def score(req: RiskRequest):
    signals = RiskSignals(
        amount=req.amount,
        velocity_1h=req.velocity_1h,
        geo_velocity_kmh=req.geo_velocity_kmh,
        device_age_days=req.device_age_days,
        device_shared_count=req.device_shared_count,
        merchant_risk=req.merchant_risk,
        customer_tenure_days=req.customer_tenure_days,
        prior_chargebacks=req.prior_chargebacks,
        high_risk_jurisdiction=req.high_risk_jurisdiction,
    )
    score = _scorer.score(signals)
    return {
        "score": score.as_dict(),
        "decision": decide(score).value,
        "route": route(score),
    }


@router.post("/risk/analyze")
async def analyze(req: RiskRequest):
    """Warm-path: LLM reasoning over the structured risk object."""
    signals = RiskSignals(
        amount=req.amount,
        velocity_1h=req.velocity_1h,
        device_age_days=req.device_age_days,
        device_shared_count=req.device_shared_count,
        merchant_risk=req.merchant_risk,
        prior_chargebacks=req.prior_chargebacks,
        high_risk_jurisdiction=req.high_risk_jurisdiction,
    )
    score = _scorer.score(signals)
    case = CaseState(
        entity=Entity(legal_name="cardholder"),
        alert=__import__("aegis.models", fromlist=["Alert"]).Alert(
            alert_type="velocity", entity_id=__import__("uuid").uuid4(), severity=score.level, score=score.score
        ),
        risk_score=score.score,
    )
    res = await RiskAnalyst().run(case, context=f"dev-1 score={score.score}")
    return {"score": score.as_dict(), "analysis": res.data, "ok": res.ok}