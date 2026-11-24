"""Agent memory: short-term (case state) + long-term (Postgres-backed) with temporal validity."""

from __future__ import annotations

from datetime import datetime
from typing import Any

from aegis.telemetry import get_logger

log = get_logger("memory")


class MemoryStore:
    """Simple in-memory + pluggable-persistence long-term memory.

    In production this is a Postgres table (joinable, restorable) — the 2026
    consensus is that a plain DB beats an opaque memory framework for auditable,
    regulated workloads. This class models the interface.
    """

    def __init__(self):
        self._facts: dict[str, list[dict[str, Any]]] = {}

    def remember(self, scope: str, fact: str, *, valid_to: str | None = None, score: float = 1.0) -> None:
        self._facts.setdefault(scope, []).append(
            {"fact": fact, "valid_to": valid_to, "score": score, "at": datetime.utcnow().isoformat()}
        )
        log.info("memory.remember", scope=scope)

    def recall(self, scope: str, as_of: str = "2026-11-24") -> list[str]:
        facts = self._facts.get(scope, [])
        return [f["fact"] for f in facts if not f["valid_to"] or f["valid_to"] >= as_of]

    def forget(self, scope: str, fact: str) -> None:
        self._facts[scope] = [f for f in self._facts.get(scope, []) if f["fact"] != fact]


_memory: MemoryStore | None = None


def get_memory() -> MemoryStore:
    global _memory
    if _memory is None:
        _memory = MemoryStore()
    return _memory