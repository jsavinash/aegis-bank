# Backend and BFF Service Boundaries

## Service boundaries

| Service | Language | Responsibility |
|---|---|---|
| **BFF (GraphQL/REST)** | TypeScript | Client aggregation, auth context |
| **Accounts** | Go / core | Account lifecycle, balances |
| **Payments** | Go | Domestic/cross-border, idempotency |
| **Cards** | Go | Authorization, controls, tokenisation |
| **Lending** | Python + Go | Origination, decisioning, servicing |
| **Wealth** | Python | Portfolios, robo, analytics |
| **Compliance** | Python | KYC/AML/regulatory (`aegis/`) |
| **AI Platform** | Python | Agents, RAG, inference |
| **Fraud** | Python | Real-time scoring + triage |
| **Data** | Python/Go | Pipelines, feature store |
| **Notifications** | Go/Python | Push/SMS/email |

## Boundary rules

- **Core is the system of record** — services don't duplicate ledger truth.
- **Services communicate via APIs + events**, never shared databases.
- **AI services are stateless** — state in Qdrant/Neo4j/Postgres.
- **BFF holds no business logic** — only aggregation and shaping.

## Contract-first

Every service publishes an OpenAPI/AsyncAPI/GraphQL schema; breaking changes gated by CI (Spectral).