# Bottlenecks, SPOFs and Hotspots

## Potential bottlenecks

| Area | Bottleneck | Mitigation |
|---|---|---|
| **Core ledger** | Single-writer per account | Partition by account; horizontal core scaling |
| **Fraud hot path** | Synchronous ML calls | Precomputed embeddings; async where possible |
| **LLM inference** | GPU throughput | vLLM continuous batching; model routing; caching |
| **RAG** | Vector search latency | HNSW tuning; caching; scoped graphs |
| **Event bus** | Consumer lag | Partitioning; autoscaling consumers |
| **Database** | Hot partitions / connections | Sharding; read replicas; pooling; caching |
| **Third-party APIs** | Rate limits/latency | Caching; circuit breakers; fallbacks |
| **Cache** | Hot keys / evictions | Replication; request coalescing |
| **DNS / edge** | Resolution/edge latency | Multi-CDN; caching; health checks |

## Single points of failure

| SPOF | Mitigation |
|---|---|
| **Core** | Multi-AZ, DR, vendor SLA |
| **Identity** | Redundant IdP, fallback auth |
| **Event bus** | Multi-AZ Kafka, replication |
| **Vector/graph** | Replicas, backups |
| **Postgres primary** | Multi-AZ failover; read replicas |
| **KMS/HSM** | Redundant HSM; key escrow |
| **LLM provider** | Multi-provider routing (LiteLLM) |
| **DNS / CDN** | Secondary provider; failover |

## Hotspots

- **Celebrity/enterprise accounts** — dedicated partitioning.
- **Payday spikes** — autoscaling, queue-based load levelling.
- **Fraud attack bursts** — rate limiting, load shedding.
- **Retry storms / viral events** — backoff + jitter, circuit breakers, load shedding.

## Monitoring & assurance

- **USE/RED** metrics; **distributed tracing** (OTel) for slow spans.
- **Capacity headroom** dashboards; **load testing** before peak events.
- **Chaos/failover drills** (kill AZ/dependency) each quarter.
