# Assumptions and Dependencies

## Assumptions

| # | Assumption | Impact if false |
|---|---|---|
| A1 | We can **buy a core** (Thought Machine Vault / Mambu; alternatives Temenos, Oracle FLEXCUBE, 10x, TCS BaNCS, Fineract) rather than build | Timeline + cost blow-up; 600-feature breadth at risk |
| A2 | A **banking licence or BaaS/partner-bank model** is secured (determines deposit insurance, KYC, regulator) | Cannot hold deposits |
| A3 | **Target markets/jurisdiction** (US/UK/EU) confirmed → specific regs (Reg E, PSD2/3, FCA, EAA, GDPR/CCPA) | Compliance rework; feature gating |
| A4 | **Open-weight + frontier LLMs** meet quality/cost bar; multi-provider routing (LiteLLM) avoids lock-in | Re-evaluate model routing / self-host path |
| A5 | **Regulators accept explainable AI + HITL + audit trail** for regulated decisions | Compliance rework; agentic decisions cannot ship |
| A6 | **Customers adopt passkeys & agentic assistant** (security whitespace: only ~5% of apps ship passkeys) | Security/UX investment at risk |
| A7 | **KYC/AML/fraud data is licensable** (ComplyAdvantage/Alloy/Unit21/Sift) | Build vs buy for data |
| A8 | **Event-driven core from day one** — every txn emits an event | AI plane impossible (real-time fraud/AML/analytics) |
| A9 | **Data residency** achievable via self-hosted stack in-VPC (Langfuse, Qdrant, Neo4j, vLLM) | Cannot serve regulated/regional customers |
| A10 | **Synthetic data + tagged eval suites** can stand in for scarce real outcomes (fraud/AML labels) | Eval quality / model improvement stalls |
| A11 | **Eval-gated CI is the definition of done** (every prompt/model/graph change gated) | Quality regressions reach production |
| A12 | **Open banking / PSD2/3 API access & consent framework** available in target markets | Aggregation / PISP features blocked |

## Dependencies

| Dependency | Owner | Needed by |
|---|---|---|
| Banking licence / BaaS partner | Legal + Executive | Phase 1 |
| Core banking platform (buy: Vault/Mambu; or build event-sourced ledger) | Procurement + Architecture | Phase 1 |
| Cloud landing zone (EKS/GKE, Terraform, ArgoCD) | Platform | Phase 1 |
| Identity provider (Keycloak/Ory, passkeys/FIDO2, risk-adaptive MFA) | Security | Phase 1 |
| Event backbone (Redpanda/Kafka + Flink) | Platform + Data | Phase 1 |
| KYC/AML/fraud data vendors | Compliance + Procurement | Phase 1 |
| Model providers / GPU capacity (vLLM/Ollama, LiteLLM gateway) | Data Science | Phase 1 |
| AI plane (LangGraph + Temporal + MCP, Qdrant/Neo4j, LlamaIndex) | AI Engineering | Phase 1 |
| Observability & eval (Langfuse, Ragas, Braintrust, OTel/Grafana) | Platform + AI | Phase 1 |
| Guardrails (NeMo Guardrails + Llama Guard) | Security | Phase 1 |
| Channels (React Native/Flutter, Next.js) | Product Engineering | Phase 1 |
| Accessibility (WCAG 2.2 AA, axe-core CI, EAA) | Design + Compliance | Phase 1 |
| Data platform (Iceberg/S3, ClickHouse, dbt, Dagster, Feast) | Data | Phase 2 |
| Open banking / PSD2/3 partner access & consent service | Partnerships + Legal | Phase 2 |
| Legal & compliance review (each phase gate) | Compliance | Each phase |

> Phase mapping: the tech-stack plan (`docs/02-technology-stack/01-tech-stack-recommendation.md` §7) uses P0–P4 (foundation → AI core → bank MVP → scale → moat); these roll up into Phase 1 (production release) / Phase 2 / Phase 3 used across the business docs.

## Risks to assumptions

Tracked in `11_risk_analysis_and_mitigation.md`.