# Observability, RUM and SLOs

## Observability pillars

| Pillar | Tool |
|---|---|
| **Traces** | OpenTelemetry → Grafana Tempo / Datadog |
| **Metrics** | Prometheus + Grafana |
| **Logs** | Loki / Datadog |
| **LLM traces** | Langfuse (self-hosted) |
| **RUM** | Grafana Faro / Datadog RUM |
| **Synthetic** | Blackbox / Checkly |

## SLOs

| SLO | Target |
|---|---|
| **API availability** | 99.95% |
| **Balance latency (p99)** | <200ms |
| **Fraud decision (p99)** | <150ms |
| **Payment success rate** | >99.5% |
| **Assistant response (p95)** | <3s |

## Tracing

- **Every request** instrumented with OTel.
- **Every AI step** (model, tool, retrieval) is a span with input/output/latency/tokens.
- **Trace context** propagated across services.

## Alerting

- **SLO burn-rate** alerts (fast + slow burn).
- **Anomaly detection** on key metrics.
- **On-call** rotation with runbooks.

## Dashboards

- **Ops:** fraud, latency, errors, payments.
- **AI:** eval scores, drift, token/cost.
- **Product:** funnel, north star.