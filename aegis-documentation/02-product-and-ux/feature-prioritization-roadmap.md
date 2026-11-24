# Feature Prioritization Roadmap

> **Coverage:** features from [`../01-business-and-strategy/14_feature_catalog.md`](../01-business-and-strategy/14_feature_catalog.md); use cases from [`../03_system_and_architecture/01_requirements_and_capacity_estimation/09_use_case_catalog_all.md`](../03_system_and_architecture/01_requirements_and_capacity_estimation/09_use_case_catalog_all.md); master map in [`../FEATURE_AND_USE_CASE_COVERAGE.md`](../FEATURE_AND_USE_CASE_COVERAGE.md).

## Prioritisation method

Score each feature: **value density** (customer value × business value) × **readiness** (data, feasibility) ÷ **effort/risk**. Sequence to prove the AI operating model first, then breadth.

## Roadmap

### Phase 1 — Production Release (0–3 months)
Accounts, cards (physical + virtual, freeze, controls), payments (domestic + P2P), bill pay, mobile deposit, savings, budgeting, biometric + passkey auth, real-time fraud alerts, agentic AML/SAR, basic agentic assistant + human escalation, KYC, audit trail, WCAG 2.2 AA baseline.

### Phase 2 — AI core + differentiators (3–6 months)
Fraud warm-path triage, credit score, multi-currency, pots/salary-sort, robo-advisor, open banking (aggregation), in-app scam detection, business lite.

### Phase 3 — Scale + depth (6–12 months)
Mortgages, BNPL, crypto, wealth projection, full agentic assistant, kids/teen, business full (payroll/MTD), insurance, super-app, embedded finance/BaaS.

## Prioritised backlog (top 10)

| # | Feature | Why | Phase |
|---|---|---|---|
| 1 | Real-time fraud + card controls | Highest ROI, security whitespace | 1 |
| 2 | Agentic assistant + escalation | Core AI-first UX | 1 |
| 3 | Fast onboarding + KYC | Activation | 1 |
| 4 | AML investigation + SAR | Compliance must-have | 1 |
| 5 | Passkeys + scam detection | Security differentiation | 1–2 |
| 6 | Savings goals + salary sort | Retention | 1–2 |
| 7 | Credit score + building | Engagement | 2 |
| 8 | Multi-currency / zero-FX | Traveller segment | 2 |
| 9 | Business banking (invoicing) | SME segment | 2–3 |
| 10 | Robo-advisor | Wealth expansion | 2–3 |

## Release principle

Each phase ships behind feature flags, eval-gated, with HITL where high-consequence. No AI ships without an eval gate and a kill switch.