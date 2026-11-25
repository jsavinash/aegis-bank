# Integration and Data Strategy
> **Scope:** all 37 feature-catalog sections + AI use cases (as features) are in scope per [`in_scope_and_out_of_scope.md`](../../01-business-and-strategy/05_scope_and_boundaries/in_scope_and_out_of_scope.md); out of scope: physical branches, proprietary payment scheme, own core-ledger build.

## Integration patterns

| Pattern | Use |
|---|---|
| **REST/OpenAPI** | Synchronous request/response (public APIs) |
| **GraphQL** | BFF aggregation for clients |
| **AsyncAPI / Kafka** | Event-driven integration |
| **Webhooks** | Merchant/partner notifications |
| **Open banking APIs** | PSD2/3 account info + payment initiation |
| **SFTP/batch** | Legacy core reconciliation |

## Core integration

- **Core banking** integrates via its published REST APIs + event streams.
- **No direct DB access** to the core from services.
- **Anti-corruption layer** maps core models to domain models.
- **Outbox pattern** for reliable event publication; **idempotency keys** on all writes.

## Resilience & reliability

- **Retries** with exponential backoff + jitter; **circuit breakers** per dependency.
- **Dead-letter queues** for failed events; replay tooling.
- **Rate limiting** + bulkheads to contain blast radius.
- **Schema registry** (AsyncAPI/OpenAPI) with backward-compatibility checks.

## Data strategy

- **Single source of truth** per domain (core = ledger/account truth).
- **Event-driven propagation** to data platform and AI plane.
- **Feature store** for ML consistency.
- **Data contracts** enforced in CI; **schema versioning**.

## External data

| Source | Use |
|---|---|
| **KYC/AML feeds** | Sanctions, PEP, adverse media |
| **Credit bureaus** | Credit scores |
| **Fraud consortium** | Shared fraud signals |
| **FX rates** | Currency conversion |
| **Open banking** | Account aggregation |

- Cached with TTL; **fallback** to last-known-good; **SLA** + **vendor risk** tracked.

## Governance

- Data catalogue + lineage; PII minimisation; consent enforcement; residency; retention; DSAR fulfilment.
