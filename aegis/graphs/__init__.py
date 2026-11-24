"""LangGraph-style case graph: explicit nodes + edges, typed shared state."""

from __future__ import annotations

from typing import TypedDict

from aegis.agents import (
    EvidenceGatherer,
    NarrativeWriter,
    NetworkAnalyst,
    QAReviewer,
    TypologyAnalyst,
    run_pipeline,
)
from aegis.models import CaseState, CaseStatus


class GraphState(TypedDict, total=False):
    case: CaseState
    stage: str
    approved: bool


class CaseGraph:
    """A deterministic graph with explicit transitions (LangGraph-shaped, no hard dep).

    Nodes map to agents; conditional edges route on QA confidence. In a full
    deployment this is backed by LangGraph + a Postgres checkpointer.
    """

    NODES = ["evidence", "typology", "network", "narrative", "qa"]

    async def run(self, case: CaseState) -> CaseState:
        await EvidenceGatherer().run(case, self._ctx(case))
        await TypologyAnalyst().run(case, self._ctx(case))
        await NetworkAnalyst().run(case, self._ctx(case))
        await NarrativeWriter().run(case, self._ctx(case))
        qa = await QAReviewer().run(case, self._ctx(case))
        if qa.ok:
            case.status = CaseStatus.INVESTIGATING
        else:
            case.status = CaseStatus.PENDING_REVIEW
        return case

    @staticmethod
    def _ctx(case: CaseState) -> str:
        return f"entity-1 alert {case.alert.alert_type.value if case.alert else 'unknown'} device dev-1"


async def run(case: CaseState) -> CaseState:
    return await CaseGraph().run(case)


__all__ = ["CaseGraph", "GraphState", "run", "run_pipeline"]