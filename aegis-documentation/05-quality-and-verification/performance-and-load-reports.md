# Performance and Load Reports

## Targets

| Metric | Target |
|---|---|
| Balance latency (p99) | <200ms |
| Fraud decision (p99) | <150ms |
| Payment end-to-end (p95) | <2s |
| Assistant first token (p95) | <1s |
| Peak throughput | 10k+ TPS |
| Error rate | <0.1% (Tier 0) |

## Load test scenarios

| Scenario | Profile |
|---|---|
| **Steady state** | Average daily volume |
| **Peak** | 3× average (payday, holidays) |
| **Spike** | Flash sale / fraud attack burst |
| **Soak** | 24h sustained |
| **Failover** | AZ/region loss |

## Tools

- **k6 / Gatling** for API load.
- **Locust** for scenario-based.
- **Synthetic** monitoring for real-user journeys.

## Reporting

- **p50/p95/p99** latency per endpoint.
- **Throughput** vs target.
- **Error budget** burn.
- **Capacity headroom** vs N+1.

## Cadence

- **Pre-release:** full load suite.
- **Quarterly:** capacity re-validation.
- **Post-incident:** regression load test.

## Results template

| Test | Target | Actual | Status |
|---|---|---|---|
| Balance p99 | <200ms | TBD | |
| Fraud p99 | <150ms | TBD | |
| Peak TPS | 10k+ | TBD | |