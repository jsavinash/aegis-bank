# Database Schemas and ERD
> **Scope:** all 37 feature-catalog sections + AI use cases (as features) are in scope per [`in_scope_and_out_of_scope.md`](../../01-business-and-strategy/05_scope_and_boundaries/in_scope_and_out_of_scope.md); out of scope: physical branches, proprietary payment scheme, own core-ledger build.

## Core entities

`mermaid
erDiagram
 CUSTOMER | |--o{ ACCOUNT : owns
 CUSTOMER | |--o{ CARD : holds
 CUSTOMER | |--o{ LOAN : borrows
 CUSTOMER | |--o{ DEVICE : uses
 CUSTOMER | |--o{ KYC_CHECK : undergoes
 CUSTOMER | |--o{ CONSENT : grants
 CUSTOMER | |--o{ DOCUMENT : owns
 ACCOUNT | |--o{ TRANSACTION : posts
 ACCOUNT | |--o{ PAYMENT : initiates
 CARD | |--o{ CARD_TXN : authorises
 LOAN | |--o{ REPAYMENT : schedules
 TRANSACTION | |--o{ FRAUD_SIGNAL : triggers
 FRAUD_SIGNAL | |--o{ CASE : becomes
 CASE | |--o{ AUDIT_EVENT : records
 CUSTOMER | |--o{ NOTIFICATION : receives
`

## Key tables (Postgres)

| Table | Purpose |
|---|---|
| `customers` | CIF: identity, risk, status |
| `accounts` | Account balances, product refs |
| `transactions` | Double-entry postings |
| `cards` | Card metadata, status, controls |
| `card_txns` | Card authorizations/clearing |
| `payments` | Payment instructions, status |
| `loans` | Loan agreements, terms |
| `repayments` | Repayment schedule/status |
| `devices` | Bound devices, risk signals |
| `kyc_checks` | KYC outcomes, evidence |
| `consents` | Open-banking consent lifecycle |
| `cases` | Fraud/AML case management |
| `documents` | pgvector-embedded docs |
| `notifications` | Delivery log/preferences |
| `audit_events` | Immutable decision log |

## Schemas by store

- **Postgres:** transactional + audit (partitioned `transactions`/`audit_events` by time).
- **Qdrant:** embeddings (payload metadata: source, case_id, tenant).
- **Neo4j:** entity graph (temporal validity intervals).
- **ClickHouse:** analytical events (OLAP).
- **Iceberg:** lakehouse tables (time-travel, 5-year online).
- **Redis:** hot state (balances cache, rate limits, idempotency keys).
- **S3:** documents/statements (encrypted).

## Indexing & retention

- Index `transactions(account_id, created_at)`, `cases(status, priority)`, `consents(external_id)`.
- Partition `transactions`/`audit_events` monthly; **7-year** retention (5-year KYC/AML).
- PII columns encrypted; `audit_events` append-only.