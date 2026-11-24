"""Regulatory change intelligence: obligation ingestion, mapping, gap analysis."""

from __future__ import annotations

from datetime import datetime
from uuid import UUID

from aegis.models import Control, Gap, Obligation
from aegis.telemetry import get_logger

log = get_logger("compliance")


class ComplianceKnowledgeBase:
    def __init__(self):
        self.obligations: dict[UUID, Obligation] = {}
        self.controls: dict[UUID, Control] = {}
        self.gaps: dict[UUID, Gap] = {}

    def ingest_obligation(self, o: Obligation) -> Obligation:
        self.obligations[o.id] = o
        log.info("obligation.ingested", ref=o.reference, source=o.source)
        return o

    def add_control(self, c: Control) -> Control:
        self.controls[c.id] = c
        return c

    def record_gap(self, g: Gap) -> Gap:
        self.gaps[g.id] = g
        return g

    def active_obligations(self, as_of: datetime | None = None) -> list[Obligation]:
        out = []
        for o in self.obligations.values():
            if o.superseded_by:
                continue
            if o.in_force_from and as_of and o.in_force_from > as_of:
                continue
            out.append(o)
        return out


_kb: ComplianceKnowledgeBase | None = None


def get_kb() -> ComplianceKnowledgeBase:
    global _kb
    if _kb is None:
        _kb = ComplianceKnowledgeBase()
        _seed_kb(_kb)
    return _kb


def _seed_kb(kb: ComplianceKnowledgeBase) -> None:
    fatf = kb.ingest_obligation(
        Obligation(
            source="FATF",
            reference="FATF-REC-10",
            title="Customer Due Diligence",
            text="Financial institutions must conduct CDD, including periodic review of high-risk relationships.",
            clause_path="rec10",
            in_force_from=datetime(2026, 1, 1),
        )
    )
    kb.add_control(
        Control(
            name="KYC Periodic Review",
            description="Periodic review of customer risk rating and CDD information.",
            code_ref="policy/kyc.md#periodic-review",
            mapped_obligation_ids=[fatf.id],
            last_reviewed=datetime(2025, 6, 1),
        )
    )