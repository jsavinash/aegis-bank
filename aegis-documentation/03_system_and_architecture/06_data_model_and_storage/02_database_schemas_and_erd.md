# Database Schemas and ERD

## Core entities

```mermaid
erDiagram
    CUSTOMER ||--o{ ACCOUNT : owns
    CUSTOMER ||--o{ CARD : holds
    CUSTOMER ||--o{ LOAN : borrows
    CUSTOMER ||--o{ DEVICE : uses
    ACCOUNT ||--o{ TRANSACTION : posts
    ACCOUNT ||--o{ PAYMENT : initiates
    CARD ||--o{ CARD_TXN : authorises
    LOAN ||--o{ REPAYMENT : schedules
    CUSTOMER ||--o{ KYC_CHECK : undergoes
    CUSTOMER ||--o{ CONSENT : grants
    TRANSACTION ||--o{ FRAUD_SIGNAL : triggers
    FRAUD_SIGNAL ||--o{ CASE : becomes
```

## Key tables (Postgres)

| Table | Purpose |
|---|---|
| `customers` | CIF: identity, risk, status |
| `accounts` | Account balances, product refs |
| `transactions` | Double-entry postings |
| `cards` | Card metadata, status, controls |
| `payments` | Payment instructions, status |
| `loans` | Loan agreements, terms |
| `kyc_checks` | KYC outcomes, evidence |
| `consents` | Open-banking consent lifecycle |
| `audit_events` | Immutable decision log |
| `documents` | pgvector-embedded docs |

## Schemas by store

- **Postgres:** transactional + audit.
- **Qdrant:** embeddings (payload metadata: source, case_id, tenant).
- **Neo4j:** entity graph (temporal validity).
- **ClickHouse:** analytical events.
- **Iceberg:** lakehouse tables (time-travel).