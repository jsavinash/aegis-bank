"""KYC / KYB & CDD workflow endpoints."""

from uuid import UUID

from fastapi import APIRouter, HTTPException
from pydantic import BaseModel

from ..exceptions.base import AegisError
from ..services.kyc_service import KycService

router = APIRouter(prefix="/kyc", tags=["kyc"])
service = KycService()


class StartKyc(BaseModel):
    customer_id: str


class AddDocument(BaseModel):
    document: str


def _view(case) -> dict:
    return {
        "id": str(case.id),
        "customer_id": case.customer_id,
        "status": case.status.value,
        "documents": case.documents,
    }


@router.post("", status_code=201)
def start(req: StartKyc):
    return _view(service.start(req.customer_id))


@router.get("")
def list_cases():
    return [_view(c) for c in service.list()]


@router.get("/{case_id}")
def get(case_id: UUID):
    try:
        return _view(service.get(case_id))
    except AegisError as e:
        raise HTTPException(status_code=404, detail=str(e))


@router.post("/{case_id}/documents")
def add_document(case_id: UUID, req: AddDocument):
    try:
        return _view(service.add_document(case_id, req.document))
    except AegisError as e:
        raise HTTPException(status_code=404, detail=str(e))


@router.post("/{case_id}/review")
def review(case_id: UUID):
    try:
        return _view(service.review(case_id))
    except AegisError as e:
        raise HTTPException(status_code=409, detail=str(e))


@router.post("/{case_id}/verify")
def verify(case_id: UUID):
    try:
        return _view(service.verify(case_id))
    except AegisError as e:
        raise HTTPException(status_code=409, detail=str(e))


@router.post("/{case_id}/reject")
def reject(case_id: UUID):
    try:
        return _view(service.reject(case_id))
    except AegisError as e:
        raise HTTPException(status_code=409, detail=str(e))