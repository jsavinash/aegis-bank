# Core Workflows and Distributed Algorithms

## Core workflows

### Payment flow
1. Validate → 2. Fraud score → 3. Ledger post (double-entry, idempotent) → 4. Emit event → 5. Notify.

### Onboarding flow
1. Collect → 2. Docling extract → 3. KYC/sanctions → 4. Risk score → 5. Provision account.

### AML investigation flow
1. Alert → 2. Evidence gather → 3. Typology/network analysis → 4. Narrative → 5. QA → 6. HITL → 7. File.

## Distributed algorithms

| Concern | Algorithm / pattern |
|---|---|
| **Consensus (ledger)** | Single-writer per account (core-managed); serializable transactions |
| **Idempotency** | Idempotency keys; dedup on write |
| **Saga (payments)** | Orchestration-based saga with compensations |
| **Event ordering** | Partitioned by account id (Kafka) |
| **Exactly-once** | Idempotent consumers + transactional outbox |
| **Distributed lock** | Redis/etcd for rare cross-service locks |

## Consistency model

- **Core ledger:** strongly consistent (ACID).
- **Event-driven projections:** eventually consistent (read models).
- **AI features:** eventual, with online store for freshness.

## Failure handling

- **Retries** with exponential backoff + jitter.
- **Circuit breakers** on dependencies.
- **Bulkheads** to isolate failures.
- **Timeouts** everywhere (no unbounded waits).