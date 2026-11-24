# ADR-002: Database Selection

- **Status:** Accepted
- **Date:** 2026-11-24
- **Deciders:** Chief Architect, Data Lead

## Context
Aegis needs transactional integrity (ledger), vector search (RAG), graph traversal (GraphRAG), and OLAP (analytics). No single store fits all.

## Decision
**Polyglot persistence:**
- **PostgreSQL 16** (+ pgvector) — core transactional data, audit, small-scale vectors.
- **Qdrant** — production vector store (metadata filtering, HNSW).
- **Neo4j** (+ Graphiti) — temporal knowledge graph.
- **ClickHouse** — OLAP/analytics.
- **Iceberg on S3** — lakehouse (time-travel, temporal queries).
- **Redis** — hot state, sessions, feature cache.

## Alternatives
| Option | Pros | Cons |
|---|---|---|
| Single Postgres | Simple, transactional | Can't scale vectors/graph well |
| One vector DB for all | Fewer systems | Poor graph/OLAP |

## Consequences
- Operational complexity → managed services + IaC.
- Clear data-lifecycle boundaries.

## Compliance
Postgres for audit trail; Qdrant/Neo4j self-hosted in-VPC for residency.