# Observability, RUM and SLOs

## Observability pillars

| Pillar | Tool |
|---|---|
| **Traces** | OpenTelemetry → Grafana Tempo / Datadog |
| **Metrics** | Prometheus + Grafana |
| **Logs** | Loki / Datadog (retention-tiered; PII-redacted) |
| **LLM traces** | Langfuse (self-hosted) |
| **RUM** | Grafana Faro / Datadog RUM |
| **Synthetic** | Blackbox / Checkly |
| **Profiling / replay** | Pyroscope; session replay (opt-in) |

## SLOs

| SLO | Target |
|---|---|
| **API availability** | 99.95% |
| **Balance latency (p99)** | <200ms |
| **Fraud decision (p99)** | <150ms |
| **Payment success rate** | >99.5% |
| **Assistant response (p95)** | <3s |

- **Error-budget policy:** budget exhausted → freeze risky releases; prioritize reliability.

## Tracing

- **Every request** instrumented with OTel; **every AI step** (model/tool/retrieval) is a span with input/output/latency/tokens.
- **Trace context** propagated across services; **exemplars** link metrics→traces; **correlation IDs** tie logs.

## Alerting

- **SLO burn-rate** alerts (fast + slow burn); **anomaly detection** on key metrics.
- **Routing/escalation** + dedup; **on-call** rotation with runbooks.

## Dashboards

- **Ops:** fraud, latency, errors, payments.
- **AI:** eval scores, drift, token/cost.
- **Product:** funnel, north star.

## Incidents & postmortems

- **Blameless postmortems**; action items tracked; **SLO review** each quarter.
