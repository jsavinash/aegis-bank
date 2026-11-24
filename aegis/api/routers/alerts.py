"""Alerts router: simulate/ingest transaction-monitoring alerts."""

from __future__ import annotations

from fastapi import APIRouter
from pydantic import BaseModel

from aegis.models import Alert, AlertType, RiskLevel

router = APIRouter()


class AlertRequest(BaseModel):
    alert_type: AlertType = AlertType.STRUCTURING
    entity_id: str = "entity-1"
    severity: RiskLevel = RiskLevel.HIGH
    score: float = 0.79
    rationale: str = ""


@router.post("/alerts", response_model=Alert, status_code=201)
async def create_alert(req: AlertRequest):
    return Alert(
        alert_type=req.alert_type,
        entity_id=__import__("uuid").UUID("00000000-0000-0000-0000-000000000001"),
        severity=req.severity,
        score=req.score,
        rationale=req.rationale,
    )


@router.get("/alerts")
async def list_alerts():
    return {"alerts": [], "count": 0}