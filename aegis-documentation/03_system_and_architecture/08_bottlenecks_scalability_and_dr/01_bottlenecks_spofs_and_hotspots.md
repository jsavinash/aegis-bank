# Bottlenecks, SPOFs and Hotspots

## Potential bottlenecks

| Area | Bottleneck | Mitigation |
|---|---|---|
| **Core ledger** | Single-writer per account | Partition by account; horizontal core scaling |
| **Fraud hot path** | Synchronous ML calls | Precomputed embeddings; async where possible |
| **LLM inference** | GPU throughput | vLLM continuous batching; model routing; caching |
| **RAG** | Vector search latency | HNSW tuning; caching; scoped graphs |
| **Event bus** | Consumer lag | Partitioning; autoscaling consumers |
| **Database** | Hot partitions | Sharding; read replicas; caching |

## Single points of failure

| SPOF | Mitigation |
|---|---|
| **Core** | Multi-AZ, DR, vendor SLA |
| **Identity** | Redundant IdP, fallback auth |
| **Event bus** | Multi-AZ Kafka, replication |
| **Vector/graph** | Replicas, backups |

## Hotspots

- **Celebrity/enterprise accounts** — dedicated partitioning.
- **Payday spikes** — autoscaling, queue-based load levelling.
- **Fraud attack bursts** — rate limiting, load shedding.

## Monitoring

- **USE/RED** metrics (utilisation, saturation, errors; rate, errors, duration).
- **Distributed tracing** (OTel) to find slow spans.
- **Load testing** before peak events.