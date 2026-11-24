# 03 — Business Goals and KPIs

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

| KPI | Baseline | Target |
|---|---|---|
| Availability | — | 99.95%+ |
| Balance latency (p99) | — | <200ms |
| Fraud decision latency (p99) | — | <150ms |
| Payment success rate | — | >99.5% |
| Fraud loss rate | ~6–8 bps (industry) | reduce 30–50% |
| Fraud false-positive rate | rules baseline | cut 40–60% |
| Contact deflection (tier-1) | — | 25–40% |
| AML analyst time per alert | ~70% manual research | down ~70% |
| AML alert-to-filing rate | legacy baseline | ↑ (fewer false alerts) |
| KYC onboarding time | days | <10 min |
| Underwriting cycle (straight-through) | days | <1 hour |
| WCAG 2.2 AA conformance | — | 100% of journeys |

## North-star metric

**Monthly active customers completing at least one high-value digital journey** (payment, application, savings goal) — balancing growth and trust.

## Measurement discipline

Track **business metrics** (fraud loss bps, cost per contact, alert-to-filing rate), not just model accuracy. Compare before/after; attribute conservatively.

## Related

- [north_star_and_funnel_metrics.md](../02-product-and-ux/06_analytics_and_experimentation/north_star_and_funnel_metrics.md) — funnel stages, trust & counter-metrics, reporting cadence.
- [03_observability_rum_and_slos.md](../03_system_and_architecture/10_testing_cicd_and_observability/03_observability_rum_and_slos.md) — SLOs behind the availability/latency/payment-success KPIs.