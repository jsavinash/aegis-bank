# Non-Functional Requirements Matrix

| Category | Requirement | Target | Verification |
|---|---|---|---|
| **Performance** | Balance latency (p99) | <200ms | Load test |
| **Performance** | Fraud decision (p99) | <150ms | Load test |
| **Performance** | App cold start | <2s | RUM |
| **Scalability** | Peak TPS | 10k+ (design headroom) | Load test |
| **Scalability** | Users | Millions | Load test |
| **Availability** | Uptime | 99.95%+ | SLO dashboards |
| **Reliability** | RTO / RPO | <1h / <5min | DR drill |
| **Security** | Certifications | PCI-DSS, SOC 2, ISO 27001 | Audit |
| **Security** | Auth | Passkeys + MFA | Pen-test |
| **Accessibility** | WCAG 2.2 AA | 100% journeys | axe-core + manual |
| **Compliance** | Data residency | In-VPC | Audit |
| **Observability** | Traces | 100% of requests | OTel |
| **Auditability** | Every AI decision | Traceable | Audit log |
| **Maintainability** | Eval gate on CI | Blocking | CI |
| **Cost** | Per-step LLM budget | Enforced | LiteLLM |

## Criticality tiers

- **Tier 0:** ledger, payments, auth, fraud hot path — highest reliability/security.
- **Tier 1:** AI agents, RAG, compliance — high, but degradable to fallback.
- **Tier 2:** insights, recommendations, super-app — best-effort.