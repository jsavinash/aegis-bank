# Non-Functional Requirements Matrix

> Baseline NFRs apply to **all 37 feature-catalog domains**; domain-specific overlays are noted where relevant (e.g., PCI-DSS for cards, GDPR for EU data, WCAG for channels). Targets are consistent with [`05_back_of_envelope_capacity_estimation.md`](./05_back_of_envelope_capacity_estimation.md), the DR plan, and the observability/security docs.

## Performance & latency

| Requirement | Target | Verification |
|---|---|---|
| Balance latency (p99) | <200ms | Load test / RUM |
| Fraud decision (p99) | <150ms | Load test |
| Payment end-to-end (p95) | <2s | Load test |
| Assistant first token (p95) | <1s | RUM |
| App cold start (p95) | <2s | RUM |
| Search / query (p95) | <500ms | Load test |

## Scalability & capacity

| Requirement | Target | Verification |
|---|---|---|
| Peak throughput | 10k+ TPS (design headroom; bottom-up ~24/sec card-auth at 5M customers) | Load test |
| Users | Millions (5M customers) | Load test |
| Card authorizations | 20M/month | Load test |
| Payments | 5M/month | Load test |
| Assistant queries | 2M/month | Load test |
| Data history | ~TB-scale, 5-year online | Capacity review |

## Availability & reliability (per tier)

| Tier | Availability | RTO | RPO | Verification |
|---|---|---|---|---|
| **Tier 0** (ledger, payments, auth, fraud) | 99.95%+ | <1h | <5min | SLO dashboards, DR drill |
| **Tier 1** (AI agents, RAG, compliance) | 99.9% | <4h | <1h | SLO dashboards, DR drill |
| **Tier 2** (insights, super-app) | 99.5% | <24h | <24h | SLO dashboards |

Tier 0 error rate target: **<0.1%** (SLO dashboards).

## Security & privacy

| Requirement | Target | Verification |
|---|---|---|
| Certifications | PCI-DSS, SOC 2, ISO 27001 | Audit |
| Authentication | Passkeys + MFA + step-up for high-value | Pen-test |
| Encryption | AES-256 at rest; TLS 1.3 in transit | Audit |
| Secrets | Vault/HSM, rotation, no secrets in code | Audit |
| Application security | SAST + DAST + dependency scan + pen-test | CI + external |
| Privacy | GDPR/CCPA DSAR; data minimisation; consent | Audit |
| Data residency | In-VPC processing/storage where required | Audit |
| Retention | 7y transactions; 5y KYC/AML | Audit |

## Compliance & auditability

| Requirement | Target | Verification |
|---|---|---|
| AML/KYC, PSD2/3, EU AI Act | Conformant | Audit |
| Audit trail | Immutable, every action | Audit log |
| Every AI decision | Traceable (inputs, tools, verdict) | Audit log |
| Model risk (Tier 1 AI) | SR 11-7 style validation | Model validation |

## Accessibility

| Requirement | Target | Verification |
|---|---|---|
| WCAG 2.2 AA | 100% of customer journeys | axe-core + manual |
| Text resize / reflow | 200% resize; 400%/320px reflow | Manual / audit |

## Observability

| Requirement | Target | Verification |
|---|---|---|
| Distributed traces | 100% of requests | OTel |
| Metrics | RED (rate/errors/duration) + USE | Dashboards |
| Audit log | 100% of Tier 0/1 actions | Audit |

## AI-specific

| Requirement | Target | Verification |
|---|---|---|
| Eval gate | Blocking on CI | CI |
| Guardrails | Fail-closed (NeMo + Llama Guard) | Test + audit |
| HITL | Required for high-consequence actions | Process audit |
| Grounding | Citations required; ungrounded rejected | Eval |
| Tool-call safety | Two-pass; least-privilege scopes | Test |
| LLM cost | Per-step budget enforced (LiteLLM) | Budget report |

## Maintainability & cost

| Requirement | Target | Verification |
|---|---|---|
| Eval-gated CI | Blocking | CI |
| Contract-first | OpenAPI/AsyncAPI before code | Review |
| Test coverage | Gate on Tier 0 code | CI |
| Architecture decisions | Recorded as ADRs | Review |
| FinOps | Model routing + caching + budgets | Cost review |

## Criticality tiers

- **Tier 0:** ledger, payments, auth, fraud hot path — highest reliability/security.
- **Tier 1:** AI agents, RAG, compliance — high, but degradable to fallback.
- **Tier 2:** insights, recommendations, super-app — best-effort.
