"""Case management endpoints (agentic investigation workflow)."""

from uuid import UUID

from fastapi import APIRouter, HTTPException
from pydantic import BaseModel

from ..exceptions.base import CaseNotFound, InvalidTransition
from ..models.enums import CaseStatus
from ..services.case_service import CaseService

router = APIRouter(prefix="/cases", tags=["cases"])
service = CaseService()


class CreateCase(BaseModel):
    alert_id: UUID


class Transition(BaseModel):
    target: CaseStatus


class RiskUpdate(BaseModel):
    value: float


class NarrativeUpdate(BaseModel):
    narrative: str


def _view(case) -> dict:
    return {
        "id": str(case.id),
        "alert_id": str(case.alert_id),
        "status": case.status.value,
        "risk": None if case.risk_score is None else case.risk_score.value,
        "narrative": case.narrative,
    }


@router.post("", status_code=201)
def create(req: CreateCase):
    return _view(service.create(req.alert_id))


@router.get("")
def list_cases():
    return [_view(c) for c in service.list()]


@router.get("/{case_id}")
def get(case_id: UUID):
    try:
        return _view(service.get(case_id))
    except CaseNotFound as e:
        raise HTTPException(status_code=404, detail=str(e))


@router.post("/{case_id}/transition")
async def transition(case_id: UUID, req: Transition):
    try:
        return _view(await service.transition(case_id, req.target))
    except CaseNotFound as e:
        raise HTTPException(status_code=404, detail=str(e))
    except InvalidTransition as e:
        raise HTTPException(status_code=409, detail=str(e))


@router.post("/{case_id}/risk")
async def set_risk(case_id: UUID, req: RiskUpdate):
    from ..models.value_objects import RiskScore

    try:
        return _view(await service.set_risk(case_id, RiskScore.from_value(req.value)))
    except CaseNotFound as e:
        raise HTTPException(status_code=404, detail=str(e))


@router.post("/{case_id}/narrative")
async def set_narrative(case_id: UUID, req: NarrativeUpdate):
    try:
        return _view(await service.set_narrative(case_id, req.narrative))
    except CaseNotFound as e:
        raise HTTPException(status_code=404, detail=str(e))