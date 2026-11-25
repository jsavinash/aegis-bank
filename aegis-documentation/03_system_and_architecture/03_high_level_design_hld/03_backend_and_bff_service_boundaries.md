# Backend and BFF Service Boundaries
> **Scope:** all 37 feature-catalog sections + AI use cases (as features) are in scope per [`in_scope_and_out_of_scope.md`](../../01-business-and-strategy/05_scope_and_boundaries/in_scope_and_out_of_scope.md); out of scope: physical branches, proprietary payment scheme, own core-ledger build.

## Service boundaries

| Service | Language | Responsibility |
|---|---|---|
| **BFF (GraphQL/REST)** | TypeScript | Client aggregation, auth context |
| **API Gateway / Identity** | Java / Keycloak | Auth, rate limit, routing |
| **Accounts** | Java / core | Account lifecycle, balances |
| **Payments** | Java (Spring Boot / Quarkus) | Domestic/cross-border, idempotency |
| **Cards** | Java (Spring Boot / Quarkus) | Authorization, controls, tokenisation |
| **Lending** | Python + Java | Origination, decisioning, servicing |
| **Wealth** | Python | Portfolios, robo, analytics |
| **Business** | Python + TS | Invoicing, expenses, payroll |
| **Treasury** | Java + Python | Cash positioning, forecasting |
| **Open Banking** | Java | Consent, aggregation, PISP |
| **Compliance** | Python | KYC/AML/regulatory () |
| **AI Platform** | Python | Agents, RAG, inference |
| **Fraud** | Python | Real-time scoring + triage |
| **Data** | Python/Java | Pipelines, feature store |
| **Notifications** | Java/Python | Push/SMS/email |
| **Documents** | Python | Statements, Docling extraction |
| **Search** | Python | Hybrid/vector search |
| **Rewards / Insurance / ESG / Digital Assets** | Python/Java | Domain modules |

## Boundary rules

- **Core is the system of record** — services don't duplicate ledger truth.
- **Each service owns its data** — no shared databases; integrate via APIs + events.
- **Sync for commands, async for propagation** — events via Kafka; idempotency keys on writes.
- **AI services are stateless** — state in Qdrant/Neo4j/Postgres.
- **BFF holds no business logic** — only aggregation and shaping.
- **Dependency direction:** channels → BFF → services → core/data; no reverse deps.

## Contract-first

Every service publishes an OpenAPI/AsyncAPI/GraphQL schema; breaking changes gated by CI (Spectral); versioned with backward compatibility.
