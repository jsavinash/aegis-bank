# 03 — Business Goals and KPIs

> **Scope:** all features + AI use cases are in scope per [`in_scope_and_out_of_scope.md`](05_scope_and_boundaries/in_scope_and_out_of_scope.md); out of scope: physical branches, proprietary payment scheme, own core-ledger build.

## Business goals

| Goal | Description | Metric |
|---|---|---|
| **G1 — Trust & security** | Best-in-class auth & fraud defence | Passkey adoption, fraud loss (bps), false-positive rate |
| **G2 — Digital adoption** | Customers use digital as primary channel | MAU, digital sales %, app rating |
| **G3 — AI leverage** | AI improves decisions & efficiency | Contact deflection %, AML analyst time/alert, underwriting cycle time |
| **G4 — Compliance** | Audit-ready, regulator-ready | Exam findings, SAR timeliness, KYC refresh compliance |
| **G5 — Growth** | Expand product holdings | Products per customer, cross-sell rate |
| **G6 — Accessibility** | WCAG 2.2 AA across journeys | Accessibility audit pass rate |

## KPIs (with targets)

| Category | KPI | Baseline | Target |
|---|---|---|---|
| **Reliability** | Availability | — | 99.95%+ |
| **Reliability** | Balance latency (p99) | — | <200ms |
| **Reliability** | Fraud decision latency (p99) | — | <150ms |
| **Reliability** | App cold start (p95) | — | <2s |
| **Reliability** | Payment success rate | — | >99.5% |
| **Reliability** | Peak throughput | — | 10k+ TPS, multi-region |
| **Fraud & financial crime** | Fraud loss rate | ~6–8 bps (industry) | reduce 30–50% |
| **Fraud & financial crime** | Fraud false-positive rate | rules baseline | cut 40–60% |
| **Fraud & financial crime** | AML analyst time per alert | ~70% manual research | down ~70% |
| **Fraud & financial crime** | AML alert-to-filing rate | legacy baseline | ↑ (fewer false alerts) |
| **AI leverage** | Contact deflection (tier-1) | — | 25–40% |
| **AI leverage** | KYC onboarding time | days | <10 min |
| **AI leverage** | Underwriting cycle (straight-through) | days | <1 hour |
| **Adoption & growth** | Monthly active users (MAU) | — | ↑ vs baseline |
| **Adoption & growth** | Digital sales % | — | ↑ vs baseline |
| **Adoption & growth** | Products per customer | — | ↑ vs baseline |
| **Compliance & accessibility** | SAR filing timeliness | — | 100% within regulatory deadline |
| **Compliance & accessibility** | WCAG 2.2 AA conformance | — | 100% of journeys |

## North-star metric

**Monthly active customers completing at least one high-value digital journey** (payment, application, savings goal) — balancing growth and trust.

## Measurement discipline

Track **business metrics** (fraud loss bps, cost per contact, alert-to-filing rate), not just model accuracy. Compare before/after; attribute conservatively.

**Counter-metrics (guard against gaming):** support contacts per 1k users (should fall), chargeback rate, complaint rate, app-store rating & review sentiment.

**Reporting cadence:** daily ops dashboard (fraud, latency, errors) · weekly product funnel + guardrails · monthly business review (north star, value pools).

## Related

- [north_star_and_funnel_metrics.md](../02-product-and-ux/06_analytics_and_experimentation/north_star_and_funnel_metrics.md) — funnel stages, trust & counter-metrics, reporting cadence.
- [03_observability_rum_and_slos.md](../03_system_and_architecture/10_testing_cicd_and_observability/03_observability_rum_and_slos.md) — SLOs behind the availability/latency/payment-success KPIs.