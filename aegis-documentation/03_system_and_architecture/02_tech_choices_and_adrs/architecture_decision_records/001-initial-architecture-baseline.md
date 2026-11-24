# ADR-001: Initial Architecture Baseline

- **Status:** Accepted
- **Date:** 2026-11-24
- **Deciders:** Chief Architect, ARB

## Context
Aegis needs an AI-first retail bank architecture that is event-driven, regulator-ready, and fast to build. The core is bought; the AI/risk plane is built.

## Decision
Adopt a **layered, event-driven architecture**:
- Channels (RN/Next.js) → Edge (APISIX/WAF + Keycloak) → BFF (GraphQL/REST) → Core (bought) + Domain services (Java) + AI plane (Python) → Event bus (Kafka) → Data (Iceberg/ClickHouse).
- AI plane: LangGraph + LlamaIndex + vLLM + Qdrant + Neo4j + NeMo + Langfuse/Ragas.

## Alternatives
| Option | Pros | Cons |
|---|---|---|
| Monolith | Simple | Doesn't scale; AI hard to isolate |
| Full microservices from day 1 | Flexible | Over-engineering; ops burden |
| Layered modular monolith → services | Pragmatic | Requires discipline |

## Consequences
- Clear separation of AI vs transactional.
- Event-driven enables real-time AI.
- Requires strong contract-first governance.

## Compliance
Self-hosted AI for residency; audit-ready by design.