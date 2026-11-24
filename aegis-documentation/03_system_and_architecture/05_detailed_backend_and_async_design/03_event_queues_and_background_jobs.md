# Event Queues and Background Jobs

## Event backbone

- **Kafka / Redpanda** for the event stream; **Flink** for stream processing.
- **Topics** partitioned by entity (account, customer) for ordering.
- **Schema registry** (Avro/Protobuf) for evolution.

## Key event topics

| Topic | Producers | Consumers |
|---|---|---|
| `transactions` | Core | Fraud, AML, Data |
| `payments` | Payments | Ledger, Notifications |
| `kyc_events` | Compliance | Data, Risk |
| `model_scores` | AI plane | Data, Audit |

## Background jobs

| Job | Tool | Cadence |
|---|---|---|
| **Retraining** | Dagster | Scheduled / drift-triggered |
| **KYC periodic review** | Temporal workflow | Risk-based |
| **Reconciliation** | Temporal workflow | Daily |
| **Report generation** | Temporal | Scheduled |
| **Model drift eval** | Dagster | Daily |

## Delivery guarantees

- **At-least-once** + idempotent consumers → effectively-once.
- **Dead-letter queue** for poison messages.
- **Transactional outbox** to avoid dual-write.
- **Replay** from offset for reprocessing.