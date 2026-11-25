"""Fraud scoring & decisioning endpoints (hot path)."""

from fastapi import APIRouter
from pydantic import BaseModel

from ..services.fraud_service import FraudService

router = APIRouter(prefix="/fraud", tags=["fraud"])
service = FraudService()


class ScoreRequest(BaseModel):
    signals: dict


class DecideRequest(BaseModel):
    signals: dict
    step_up: float = 0.5
    decline: float = 0.85


@router.post("/score")
def score(req: ScoreRequest):
    result = service.score(req.signals)
    return {"value": result.value, "level": result.level.value}


@router.post("/decide")
def decide(req: DecideRequest):
    verdict = service.decide(req.signals, req.step_up, req.decline)
    return {"verdict": verdict.value}