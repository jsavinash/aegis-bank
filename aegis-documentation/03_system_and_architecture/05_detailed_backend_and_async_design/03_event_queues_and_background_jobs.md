# Event Queues and Background Jobs

## Event backbone

- **Kafka / Redpanda** for the event stream; **Flink** for stream processing.
- **Topics** partitioned by entity (account, customer) for ordering.
- **Schema registry** (Avro/Protobuf) for evolution; **compaction** for keyed topics.

## Key event topics

| Topic | Producers | Consumers |
|---|---|---|
| `transactions` | Core | Fraud, AML, Data |
| `payments` | Payments | Ledger, Notifications |
| `cards` | Cards | Fraud, Notifications |
| `loans` | Lending | Data, Notifications |
| `kyc_events` | Compliance | Data, Risk |
| `consents` | Open Banking | Data, Audit |
| `model_scores` | AI plane | Data, Audit |
| `audit_events` | All | Audit, Compliance |

## Background jobs

| Job | Tool | Cadence |
|---|---|---|
| **Retraining** | Dagster | Scheduled / drift-triggered |
| **KYC periodic review** | Temporal workflow | Risk-based |
| **Reconciliation** | Temporal workflow | Daily |
| **Statement generation** | Temporal | Monthly |
| **Report generation** | Temporal | Scheduled |
| **Model drift eval** | Dagster | Daily |
| **Data archival** | Dagster | Weekly |

## Delivery guarantees

- **At-least-once** + idempotent consumers → effectively-once.
- **Dead-letter queue** for poison messages; **replay** from offset.
- **Transactional outbox** to avoid dual-write.
- **Consumer groups** for horizontal scale; **backpressure** via lag monitoring.

## Scheduling & isolation

- **Temporal** for durable workflows; **Dagster** for data pipelines.
- Per-tenant **queues/namespaces**; **rate limits** per consumer.

## Observability

- Consumer lag, throughput, DLQ depth, job success rate; alert on lag/DLQ growth.
