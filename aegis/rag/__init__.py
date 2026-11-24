"""Hybrid retrieval (BM25 + vector) with reranking, plus a GraphRAG router."""

from __future__ import annotations

import re
from dataclasses import dataclass
from typing import Any

from aegis.telemetry import get_logger

log = get_logger("rag")


@dataclass
class Retrieved:
    ref: str
    content: str
    score: float
    source: str = "vector"
    metadata: dict[str, Any] | None = None


_TOKEN = re.compile(r"[a-z0-9]+")


def bm25_scores(query: str, docs: list[dict]) -> list[float]:
    """Minimal in-memory BM25 for the offline/hybrid path."""
    q_terms = _TOKEN.findall(query.lower())
    if not q_terms or not docs:
        return [0.0] * len(docs)
    k1, b = 1.5, 0.75
    N = len(docs)
    avgdl = sum(len(_TOKEN.findall(d.get("content", ""))) for d in docs) / N or 1
    df: dict[str, int] = {}
    for d in docs:
        for t in set(_TOKEN.findall(d.get("content", "").lower())):
            df[t] = df.get(t, 0) + 1
    scores = []
    for d in docs:
        toks = _TOKEN.findall(d.get("content", "").lower())
        dl = len(toks) or 1
        tf: dict[str, int] = {}
        for t in toks:
            tf[t] = tf.get(t, 0) + 1
        s = 0.0
        for t in q_terms:
            if t not in tf:
                continue
            idf = max(0.0, (N - df[t] + 0.5) / (df[t] + 0.5))
            s += idf * (tf[t] * (k1 + 1)) / (tf[t] + k1 * (1 - b + b * dl / avgdl))
        scores.append(s)
    return scores


def _vector_scores(query: str, docs: list[dict]) -> list[float]:
    """Placeholder vector similarity (char-trigram cosine) for offline mode."""
    q = query.lower()
    qset = {q[i : i + 3] for i in range(max(0, len(q) - 2))}
    out = []
    for d in docs:
        c = d.get("content", "").lower()
        cset = {c[i : i + 3] for i in range(max(0, len(c) - 2))}
        inter = len(qset & cset)
        union = len(qset | cset) or 1
        out.append(inter / union)
    return out


class HybridRetriever:
    def __init__(self, top_k: int = 20, rerank_n: int = 8):
        self.top_k = top_k
        self.rerank_n = rerank_n

    def retrieve(self, query: str, docs: list[dict]) -> list[Retrieved]:
        if not docs:
            return []
        bm = bm25_scores(query, docs)
        vs = _vector_scores(query, docs)
        fused = []
        for i, d in enumerate(docs):
            score = 0.6 * bm[i] + 0.4 * vs[i]
            fused.append((score, i, d))
        fused.sort(reverse=True, key=lambda x: x[0])
        top = fused[: self.top_k]
        reranked = self.rerank(query, [(d, s) for s, _, d in top])
        return reranked[: self.rerank_n]

    def rerank(self, query: str, candidates: list[tuple[dict, float]]) -> list[Retrieved]:
        q = query.lower()
        out = []
        for d, base in candidates:
            c = d.get("content", "").lower()
            overlap = len(set(_TOKEN.findall(q)) & set(_TOKEN.findall(c)))
            score = base + 0.05 * overlap
            out.append(
                Retrieved(
                    ref=d.get("ref", ""),
                    content=d.get("content", ""),
                    score=score,
                    source=d.get("source", "vector"),
                    metadata=d.get("metadata"),
                )
            )
        out.sort(reverse=True, key=lambda r: r.score)
        return out


class GraphStore:
    """In-memory adjacency list standing in for Neo4j; respects temporal validity."""

    def __init__(self):
        self.adj: dict[str, list[dict]] = {}
        self.nodes: dict[str, dict] = {}

    def add_node(self, node_id: str, **attrs) -> None:
        self.nodes[node_id] = attrs

    def add_edge(self, a: str, b: str, relation: str, valid_to: str | None = None) -> None:
        self.adj.setdefault(a, []).append({"to": b, "relation": relation, "valid_to": valid_to})

    def neighbourhood(self, node_id: str, depth: int = 2, as_of: str = "2026-11-24") -> list[dict]:
        seen = {node_id}
        frontier = [node_id]
        result = []
        for _ in range(depth):
            nxt = []
            for cur in frontier:
                for edge in self.adj.get(cur, []):
                    if edge["valid_to"] and edge["valid_to"] < as_of:
                        continue
                    nbr = edge["to"]
                    result.append({"id": nbr, "relation": edge["relation"], "via": cur})
                    if nbr not in seen:
                        seen.add(nbr)
                        nxt.append(nbr)
            frontier = nxt
        return result


_graph: GraphStore | None = None


def get_graph() -> GraphStore:
    global _graph
    if _graph is None:
        _graph = GraphStore()
        _seed_graph(_graph)
    return _graph


def _seed_graph(g: GraphStore) -> None:
    g.add_node("entity-1", name="Acme Holdings LLC", risk="medium")
    g.add_node("acct-991", name="Mule A", chargeback=True)
    g.add_node("acct-992", name="Mule B", chargeback=False)
    g.add_edge("entity-1", "acct-991", "shared_device", valid_to=None)
    g.add_edge("entity-1", "acct-992", "shared_contact", valid_to=None)
    g.add_edge("acct-991", "acct-992", "counterparty", valid_to="2026-01-01")  # expired


class Router:
    """Decides hybrid vs graph based on query type (the 'router, not rebuild' pattern)."""

    GRAPH_HINTS = ("across all", "which controls", "network", "related", "all product", "timeline", "over time")

    def should_use_graph(self, query: str) -> bool:
        q = query.lower()
        return any(h in q for h in self.GRAPH_HINTS)

    def retrieve(self, query: str, docs: list[dict]) -> dict:
        hybrid = HybridRetriever()
        results = hybrid.retrieve(query, docs)
        if self.should_use_graph(query):
            log.info("router.graph", query=query[:60])
            return {"mode": "graph+hybrid", "hybrid": results, "graph": _graph_hint(query)}
        return {"mode": "hybrid", "hybrid": results}


def _graph_hint(query: str) -> list[dict]:
    g = get_graph()
    return g.neighbourhood("entity-1", depth=2)