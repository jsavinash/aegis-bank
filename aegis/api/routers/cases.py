"""Cases router: create, list, run investigation, approve, file SAR."""

from __future__ import annotations

from uuid import UUID

from fastapi import APIRouter, HTTPException

from aegis.graphs import CaseGraph
from aegis.models import AlertType, CaseState, RiskLevel
from aegis.workflows import make_case

router = APIRouter()

# In-memory case store (replace with DB-backed repository in production).
_CASES: dict[UUID, CaseState] = {}


@router.post("/cases", response_model=CaseState, status_code=201)
async def create_case(alert_type: AlertType = AlertType.STRUCTURING, severity: RiskLevel = RiskLevel.HIGH):
    case = make_case(alert_type, severity)
    _CASES[case.case_id] = case
    return case


@router.get("/cases", response_model=list[CaseState])
async def list_cases():
    return list(_CASES.values())


@router.get("/cases/{case_id}", response_model=CaseState)
async def get_case(case_id: UUID):
    case = _CASES.get(case_id)
    if not case:
        raise HTTPException(404, "case not found")
    return case


@router.post("/cases/{case_id}/investigate", response_model=CaseState)
async def investigate(case_id: UUID):
    case = _CASES.get(case_id)
    if not case:
        raise HTTPException(404, "case not found")
    case = await CaseGraph().run(case)
    _CASES[case_id] = case
    return case


@router.post("/cases/{case_id}/approve", response_model=CaseState)
async def approve_case(case_id: UUID, note: str = ""):
    case = _CASES.get(case_id)
    if not case:
        raise HTTPException(404, "case not found")
    case.approvals.append({"by": "human", "note": note, "approved": True})
    case.status = case.status.FILED
    case.touch()
    return case


@router.post("/cases/{case_id}/file-sar")
async def file_sar(case_id: UUID):
    case = _CASES.get(case_id)
    if not case:
        raise HTTPException(404, "case not found")
    if not case.narrative:
        raise HTTPException(400, "no narrative to file")
    from aegis.tools import registry

    res = await registry.call("narrative", "file_sar", {"case_id": str(case_id), "narrative": case.narrative}, approved=True)
    case.status = case.status.FILED
    return res