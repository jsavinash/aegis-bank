# Integration and Data Strategy

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

## Data strategy

- **Single source of truth** per domain (core = ledger/account truth).
- **Event-driven propagation** to data platform and AI plane.
- **Feature store** for ML consistency.
- **Data contracts** enforced in CI.

## External data

| Source | Use |
|---|---|
| **KYC/AML feeds** | Sanctions, PEP, adverse media |
| **Credit bureaus** | Credit scores |
| **Fraud consortium** | Shared fraud signals |
| **FX rates** | Currency conversion |
| **Open banking** | Account aggregation |

## Governance

- Data catalogue + lineage; PII minimisation; consent enforcement; residency.