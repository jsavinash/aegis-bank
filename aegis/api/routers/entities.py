"""Entities router: KYC / customer 360 lookups."""

from __future__ import annotations

from fastapi import APIRouter
from pydantic import BaseModel

from aegis.models import Entity, RiskLevel

router = APIRouter()


class EntityRequest(BaseModel):
    legal_name: str
    entity_type: str = "person"
    jurisdiction: str = "US"
    risk_rating: RiskLevel = RiskLevel.LOW


@router.get("/entities/{entity_id}", response_model=Entity)
async def get_entity(entity_id: str):
    return Entity(id=__import__("uuid").UUID(entity_id), legal_name="Acme Holdings LLC", entity_type="company", risk_rating=RiskLevel.MEDIUM)


@router.post("/entities", response_model=Entity, status_code=201)
async def create_entity(req: EntityRequest):
    return Entity(legal_name=req.legal_name, entity_type=req.entity_type, jurisdiction=req.jurisdiction, risk_rating=req.risk_rating)