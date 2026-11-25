# To-Be Target Capabilities

> **Scope:** all features + AI use cases are in scope per [`in_scope_and_out_of_scope.md`](../05_scope_and_boundaries/in_scope_and_out_of_scope.md); out of scope: physical branches, proprietary payment scheme, own core-ledger build.

## Target capabilities

**Phase 1 (production release):** trust, security, compliance foundation — real-time ledger, fraud engine, agentic AML, onboarding, accessibility, audit-ready AI.
**Phase 2:** differentiation + scale — conversational banking, credit decisioning, regulatory intelligence, open banking, money management, lending suite, international/cross-border.
**Phase 3:** moat expansion — savings & wealth, business banking, kids/teen/family, rewards & loyalty, insurance, digital assets, super-app, sustainability, merchant acceptance, specialty/niche.

| Capability | Target state | Enabled by | Phase |
|---|---|---|---|
| **Real-time ledger** | Event-driven core; every txn emits an event | Core banking + Redpanda/Kafka + Flink | 1 |
| **AI fraud engine** | Sub-150ms GNN/GBM scoring + agentic triage | Python ML + vLLM warm path | 1 |
| **Agentic AML** | Multi-agent investigation + SAR drafting + HITL | LangGraph + Temporal + GraphRAG | 1 |
| **Digital onboarding** | <10 min, automated KYC/KYB | Docling + identity + workflow agent | 1 |
| **Accessibility** | WCAG 2.2 AA everywhere | Design system + axe-core CI | 1 |
| **Audit-ready AI** | Every decision traceable (data/model/policy/human) | Langfuse + OTel + audit log | 1 |
| **Conversational banking** | Agentic assistant (voice/chat) with human escalation | LangGraph + LlamaIndex + MCP | 2 |
| **Credit decisioning** | ML + LLM copilot, human sign-off, explainable | XGBoost + LangGraph + vLLM | 2 |
| **Regulatory intelligence** | Continuous obligation→control mapping, gap register | GraphRAG + Graphiti temporal KG | 2 |
| **Open banking** | Aggregation + payment initiation + consent | PSD2/3 APIs + consent service | 2 |
| **Money management & insights** | Spending dashboard, budgets, cash-flow forecast, net-worth 360°, AI nudges | Data platform + LlamaIndex + personalization | 2 |
| **Lending suite** | Personal, auto, mortgage, BNPL, overdraft, credit line, EWA, origination workflow | Lending engine + ML underwriting | 2 |
| **International / cross-border** | Multi-currency, low-FX conversion, global QR, travel card, remittance, stablecoin | FX engine + partner corridors | 2 |
| **Savings & wealth** | Goals, robo-advisor, self-directed investing, retirement, crypto, portfolio analytics | Wealth engine + AI projections | 3 |
| **Business banking** | SME accounts, invoicing, expenses, payroll, tax, accounting integrations, cash-flow forecasting | Business module + accounting APIs | 3 |
| **Kids/teen/family** | Teen accounts, kids cards, allowances, parental controls, family pots | Core + parental controls | 3 |
| **Rewards & loyalty** | Cashback, offers, referrals, tiers, points, personalized rewards | Loyalty engine + AI personalization | 3 |
| **Insurance & protection** | Travel, device, life, home, pet, embedded at checkout | Insurance partnerships + embedded finance | 3 |
| **Digital assets & stablecoins** | Crypto trading, custodial wallet, stablecoin rails, tokenised deposits | Digital assets module | 3 |
| **Super-app & lifestyle** | Top-up, travel, mobility, events, eSIM, food delivery, mini-apps, partner integrations | Lifestyle marketplace | 3 |
| **Sustainability / green banking** | Carbon tracker, offsets, ESG funds, green financing, eco cards, paperless, donations | ESG data + carbon APIs | 3 |
| **Payments acceptance (merchant)** | Card acquiring/POS, e-commerce checkout, QR merchant, subscriptions, payouts, chargebacks | Acquiring platform | 3 |
| **Specialty / niche** | Islamic, microfinance, gig/migrant, payroll-as-a-service, real-estate, agri-lending, diaspora, charity, trust/estate | Specialized modules | 3 |

## Future-state flow

```
Customer → Channel (RN/Next.js/Voice)
        → Edge (APISIX/WAF, Keycloak passkeys)
        → BFF (GraphQL/REST)
        → Domain services (Java) + Core ledger (bought)
        → Event bus (Kafka) → AI plane (fraud, AML, agents) + Data (Iceberg/ClickHouse)
        → Guardrails + Audit + Langfuse
```

## Success criteria

Each capability must pass its **eval gate** (offline regression + online judge) and satisfy **security/compliance review** before production. No AI capability ships without HITL where high-consequence.