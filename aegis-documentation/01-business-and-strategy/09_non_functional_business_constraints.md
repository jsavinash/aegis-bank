# 09 — Non-Functional Business Constraints

> **Scope:** all features + AI use cases are in scope per [`in_scope_and_out_of_scope.md`](05_scope_and_boundaries/in_scope_and_out_of_scope.md); out of scope: physical branches, proprietary payment scheme, own core-ledger build.

| Area | Constraint / Target |
|---|---|
| **Availability** | 99.95%+ (24/7, zero-downtime deploys) |
| **Latency** | Balance <200ms; fraud hot-path <150ms; app cold start <2s |
| **Scale** | Millions of users; 10k+ TPS peak; multi-region |
| **Security** | PCI-DSS, SOC 2, ISO 27001; annual pen-test & red-team |
| **Accessibility** | WCAG 2.2 AA; EU EAA compliance; screen-reader tested |
| **Compliance** | GDPR/CCPA, PSD2/PSD3, AML/KYC (FATF/FinCEN), EU AI Act |
| **Observability** | End-to-end OTel tracing; audit logging; SLO dashboards |
| **Privacy** | Data residency, consent, minimal collection, DSAR automation |
| **Reliability** | RTO < 1h, RPO < 5min for core; graceful degradation |
| **Maintainability** | Contract-first; config-driven products; eval-gated CI |
| **Cost** | FinOps guardrails; per-step LLM budgets; reserved GPU where sensible |

## Regulatory constraints (binding)

- **Data residency** — self-host AI stack (Langfuse, Qdrant, Neo4j, vLLM) in-VPC where required.
- **Model risk** — validation, monitoring, documentation, governance (SR 11-7 equivalent).
- **Fair lending** — explainable credit decisions; adverse-action reasons; bias testing.
- **Accessibility law** — WCAG 2.2 AA is a legal requirement in the EU.