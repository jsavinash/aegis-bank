"""Compliance router: obligations, controls, gaps, gap analysis."""

from __future__ import annotations

from fastapi import APIRouter, HTTPException

from aegis.agents import GapAnalyst
from aegis.compliance import get_kb
from aegis.models import CaseState

router = APIRouter()


@router.get("/compliance/obligations")
async def list_obligations():
    return [o.model_dump(mode="json") for o in get_kb().active_obligations()]


@router.get("/compliance/controls")
async def list_controls():
    return [c.model_dump(mode="json") for c in get_kb().controls.values()]


@router.get("/compliance/gaps")
async def list_gaps():
    return [g.model_dump(mode="json") for g in get_kb().gaps.values()]


@router.post("/compliance/analyze-gap")
async def analyze_gap(obligation_ref: str = "FATF-REC-10"):
    kb = get_kb()
    ob = next((o for o in kb.obligations.values() if o.reference == obligation_ref), None)
    if not ob:
        raise HTTPException(404, "obligation not found")
    case = CaseState(entity=None, alert=None)
    res = await GapAnalyst().run(case, context=f"{ob.reference} {ob.text}")
    return {"obligation": ob.model_dump(mode="json"), "analysis": res.data}