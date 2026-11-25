"""AML transaction monitoring endpoints."""

from fastapi import APIRouter
from pydantic import BaseModel

from ..services.aml_service import AmlService

router = APIRouter(prefix="/aml", tags=["aml"])
service = AmlService()


class SignalsRequest(BaseModel):
    signals: dict


@router.post("/score")
def score(req: SignalsRequest):
    result = service.score(req.signals)
    return {"value": result.value, "level": result.level.value}


@router.post("/triage")
def triage(req: SignalsRequest):
    alert = service.triage(req.signals)
    return {
        "transaction_id": alert.transaction_id,
        "customer_id": alert.customer_id,
        "amount": alert.amount.amount,
        "currency": alert.amount.currency,
        "value": alert.risk.value,
        "level": alert.risk.level.value,
        "verdict": alert.verdict.value,
    }