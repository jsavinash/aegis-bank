"""Repository layer — persistence for documents and audit events."""

from __future__ import annotations

import json
from uuid import UUID

from sqlalchemy import text
from sqlalchemy.ext.asyncio import AsyncSession


class DocumentRepository:
    def __init__(self, session: AsyncSession):
        self.session = session

    async def add(
        self,
        doc_id: UUID,
        case_id: UUID | None,
        source: str,
        ref: str,
        content: str,
        metadata: dict,
        embedding: list[float] | None = None,
    ) -> None:
        emb = f"'{json.dumps(embedding)}'" if embedding else "NULL"
        await self.session.execute(
            text(
                """
                INSERT INTO documents (id, case_id, source, ref, content, metadata, embedding)
                VALUES (:id, :case_id, :source, :ref, :content, CAST(:metadata AS jsonb), :emb)
                ON CONFLICT (id) DO UPDATE SET content = EXCLUDED.content,
                    metadata = EXCLUDED.metadata, embedding = EXCLUDED.embedding
                """
            ),
            {
                "id": str(doc_id),
                "case_id": str(case_id) if case_id else None,
                "source": source,
                "ref": ref,
                "content": content,
                "metadata": json.dumps(metadata),
                "emb": emb,
            },
        )
        await self.session.commit()


class AuditRepository:
    def __init__(self, session: AsyncSession):
        self.session = session

    async def record(
        self,
        actor: str,
        action: str,
        detail: dict,
        case_id: UUID | None = None,
        model: str | None = None,
        prompt_version: str | None = None,
        judge_score: float | None = None,
    ) -> None:
        await self.session.execute(
            text(
                """
                INSERT INTO audit_events (id, case_id, actor, action, detail, model,
                    prompt_version, judge_score)
                VALUES (gen_random_uuid(), :case_id, :actor, :action,
                    CAST(:detail AS jsonb), :model, :prompt_version, :judge_score)
                """
            ),
            {
                "case_id": str(case_id) if case_id else None,
                "actor": actor,
                "action": action,
                "detail": json.dumps(detail),
                "model": model,
                "prompt_version": prompt_version,
                "judge_score": judge_score,
            },
        )
        await self.session.commit()