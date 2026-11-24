# Back-of-the-Envelope Capacity Estimation

## Assumptions

- 5M customers; 20M card txns/month; 5M payments/month; 2M assistant queries/month.
- Peak = 3× average.

## Card authorization (hot path)

- 20M/month ≈ 7.7/sec average; peak ~23/sec — but per-node p99 <150ms is the binding constraint, not TPS.
- Stateless Java service (Spring Boot / Quarkus); horizontal scale; Redis for state.

## Fraud scoring

- Every card txn scored → ~8/sec average, ~24/sec peak.
- GNN embedding lookup precomputed; <20ms; GBM <10ms → <150ms p99.

## AI assistant

- 2M queries/month ≈ 0.8/sec avg; LLM calls dominate cost.
- Stateless agents; shared Qdrant/Neo4j; vLLM with continuous batching.

## Data

- Event volume: ~30M events/month ≈ 12/sec avg; Kafka handles easily.
- Iceberg on S3; ClickHouse for OLAP.

## Storage

- 5M customers × ~5-year history → ~TB-scale; Postgres (core) + Iceberg (lake).

## Scaling strategy

- **Tier 0:** over-provision + multi-AZ; fail over fast.
- **Tier 1:** autoscale K8s; queue-based load levelling.
- **Tier 2:** best-effort, degrade gracefully.

> **Headroom note:** the 10k+ TPS peak target in the NFR/scale documents is a multi-region *design ceiling*, not the bottom-up demand. At 5M customers the derived load is ~24/sec card-auth peak (3× the ~7.7/sec average); capacity is provisioned against the headroom target, while the bottom-up figures size the current footprint.