# Core Workflows and Distributed Algorithms

## Core workflows

### Payment flow
1. Validate → 2. Fraud score → 3. Ledger post (double-entry, idempotent) → 4. Emit event → 5. Notify.

### Card authorization
1. Auth request → 2. Controls/limits → 3. Fraud score (<150ms) → 4. Approve/decline → 5. Emit event.

### Onboarding flow
1. Collect → 2. Docling extract → 3. KYC/sanctions → 4. Risk score → 5. Provision account.

### Lending origination
1. Apply → 2. Document verify → 3. Underwrite (ML + copilot) → 4. Decision (HITL if complex) → 5. Disburse.

### AML investigation flow
1. Alert → 2. Evidence gather → 3. Typology/network analysis → 4. Narrative → 5. QA → 6. HITL → 7. File.

### Dispute / chargeback
1. Raise → 2. Evidence → 3. Decide → 4. Provisional credit/debit → 5. Network representment.

### Reconciliation
1. Ingest → 2. Match → 3. Break resolution → 4. Adjust (idempotent) → 5. Report.

## Distributed algorithms

| Concern | Algorithm / pattern |
|---|---|
| **Consensus (ledger)** | Single-writer per account (core-managed); serializable transactions |
| **Idempotency** | Idempotency keys; dedup on write |
| **Saga (payments)** | Orchestration-based saga with compensations |
| **Event ordering** | Partitioned by account id (Kafka) |
| **Exactly-once** | Idempotent consumers + transactional outbox |
| **Sharding** | Consistent hashing by account id |
| **Leader election** | etcd/Redis for singleton workers |
| **Rate limiting / backpressure** | Token bucket; queue-based load levelling |
| **Distributed lock** | Redis/etcd for rare cross-service locks |

## Consistency model

- **Core ledger:** strongly consistent (ACID); read-your-writes.
- **Event-driven projections:** eventually consistent (read models).
- **AI features:** eventual, with online store for freshness.

## Failure handling

- **Retries** with exponential backoff + jitter.
- **Circuit breakers** on dependencies; **bulkheads** to isolate failures.
- **Timeouts** everywhere (no unbounded waits).
- **DLQ + replay** for failed events; **graceful degradation** for Tier 2.

## Observability

- Every workflow step is an OTel span; audit events for Tier 0/1; run ID correlates logs/traces.
