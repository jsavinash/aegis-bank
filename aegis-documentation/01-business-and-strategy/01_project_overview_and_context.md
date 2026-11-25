# 01 — Project Overview and Context

## Problem

Retail banks face three simultaneous, compounding pressures:

1. **Product breadth & depth gap** — Customers expect a full retail banking surface (~485 features across 37 domains: accounts, cards, payments, lending, wealth, business banking, open banking, super-app lifestyle, sustainability, digital assets, insurance, accessibility, compliance). 2026 market teardown (Chase, BofA, Wells Fargo, US Bank, SoFi, DBS, Nubank, Revolut, Monzo, Starling, Chime) shows incumbents have polished apps but narrow AI/security; neobanks have breadth but lack trust, compliance, and lending depth. No single player delivers the full stack with **incumbent-grade trust + neobank-grade UX + best-in-class security**.

2. **Escalating fraud & financial crime** — Legacy rule-based controls cannot keep pace with adversarial, non-stationary attacks. Ostorlab's 2026 scan of 2,686 banking apps: only 5% ship passkeys, 12% in-app fraud detection, 17% scam warnings. Industry AML false-positive rates exceed 90% (Transvision, Flagright benchmarks), consuming ~70% of analyst time on manual research. Fraud rules encode yesterday's typologies; real-time graph-native reasoning (GNN/GBM <150ms) is required to detect mule rings, synthetic identities, and device farms.

3. **Regulatory & compliance burden** — PSD2/PSD3, EU AI Act, EAA (WCAG 2.2 AA legal requirement), GDPR/CCPA, AML/KYC (FATF, FinCEN), fair-lending, and emerging stablecoin/CBDC rules all demand **explainable, auditable, human-accountable systems**. Legacy cores and bolted-on AI cannot produce regulator-ready audit trails, grounded SAR narratives, or temporal obligation-to-control mappings.

**The compounding failure:** buying a core banking platform solves the ledger but not the AI/security whitespace. Bolting chatbots onto legacy stacks fails regulatory scrutiny (no HITL, no grounding, no evals). Building everything from scratch takes 5+ years and $50M+. The market needs an **AI-first greenfield bank** that treats AI as the primary decision layer (fraud, AML, underwriting, service, compliance) — not a feature — with eval-gated CI, durable execution, and audit-ready traceability from day one.

## Opportunity

A greenfield AI-first bank can:

1. **Deliver the full product surface** — ~485 features across 37 sections (see [`14_feature_catalog.md`](14_feature_catalog.md)), built on a **config-driven core** (Thought Machine/Mambu) so 485 features ≠ 485 codebases. **Phase 1 production release** (accounts, cards, payments, security base) launches in ~3 months; Phase 2 adds full lending suite, multi-currency, passkeys, scam detection, robo-advisor; Phase 3 deepens with mortgages, agentic AI, business banking, embedded finance.

2. **Use AI as the decision engine across ~46 use cases** (see [`14_feature_catalog.md`](14_feature_catalog.md)) organized by four agent archetypes:
 - **Monitoring agents** (hot path): real-time fraud scoring (<150ms GNN/GBM), AML transaction monitoring, sanctions/PEP screening, cybersecurity triage.
 - **Task agents**: fraud alert triage, SAR narrative drafting, KYC/CDD automation, credit underwriting copilot, document extraction, developer augmentation.
 - **Workflow agents**: KYC onboarding, loan origination, reconciliation, collections, regulatory reporting, customer servicing (contact centre + account/card agent).
 - **Multi-agent systems**: Aegis-IR (AML investigation), credit underwriting pod, wealth advice pod, regulatory change intelligence (Lexicon).

 Sequencing rule: **fraud + servicing first** (lower fair-lending exposure, builds operating muscle) → AML as augmentation layer → underwriting only once governance model exists.

3. **Turn compliance into competitive advantage** — every AI decision is traceable to data, model, policy, and human via: eval-gated CI (Ragas + Braintrust), grounded generation (citation-required), durable HITL (Temporal `interrupt()`), fail-closed guardrails (NeMo + Llama Guard), full OTel traceability. Regulators get audit-ready artefacts; competitors get pilot factories.

4. **Capture the security whitespace** — ship passkeys (only 5% of apps), in-app scam detection (17%), behavioural biometrics, real-time fraud alerts with one-tap freeze, device binding — as table-stakes, not differentiators.

## Context

- **Market:** 2026 benchmarks show AI leaders (JPMorgan, DBS, Capital One, CommBank) pulling ahead; laggards run pilot factories.
- **Technology:** the open-source AI stack is production-ready (LangGraph, LlamaIndex, vLLM, Qdrant, NeMo, Langfuse, Ragas).
- **Regulation:** EU AI Act risk tiers, PSD2/PSD3, EAA, GDPR/CCPA, AML/KYC (FATF, FinCEN) all apply.

## Objectives

- Ship a **production-grade Phase 1 release** (accounts, cards, payments, security base) in ~3 months.
- Prove the AI operating model on 2–3 deep use cases (fraud, servicing, AML) before breadth.
- Reach 99.95% availability, <150ms fraud decisions, WCAG 2.2 AA.

## Constraints

- Data residency and regulatory scrutiny (must self-host where required).
- Fair-lending scrutiny on underwriting (sequence late, human-in-loop).
- Core-banking complexity (buy, don't build).

## Success metrics

See `03_business_goals_and_kpis.md` (includes north-star metric: monthly active customers completing a high-value digital journey).

## Coverage

**All features to implement (~485 features + ~46 AI use cases, treated as features), across 37 sections:** 1 Accounts & Deposits · 2 Payments & Transfers (AI: agentic payment initiation, instruction repair, cash-flow forecasting) · 3 Cards · 4 Bill Pay & Payment Mgmt · 5 Lending & Credit (AI: origination agent, underwriting copilot, income verification, alt-data scoring, collections) · 6 Savings, Wealth & Investing (AI: RM copilot, robo-advisory, financial planning, tax-loss harvesting, market-scout) · 7 Money Management & Insights · 8 Credit & Financial Health · 9 Security & Fraud (AI: real-time fraud scoring, alert triage, scam interception) · 10 Customer Service & AI (AI: contact-centre agent, account/card servicing, agent assist, voice banking, complaints, digital-human guide, coaching) · 11 Business & Commercial Banking · 12 Treasury & Cash Management (AI: liquidity monitoring, reconciliation, forecasting) · 13 Private Banking & Wealth (HNW) (AI: RM copilot) · 14 Kids, Teens & Family · 15 Open Banking & Ecosystem · 16 Super-App & Lifestyle · 17 Sustainability / Green · 18 Digital Assets & Stablecoins · 19 Rewards & Loyalty · 20 Insurance & Protection · 21 Accessibility & Inclusion · 22 Compliance, Risk & Regulatory (AI: AML monitoring, investigation agent, SAR/STR drafting, KYC/KYB, sanctions/PEP, regulatory reporting, change monitoring, disputes, agentic BI) · 23 Platform, Data & AI Infra (AI: developer augmentation, cyber triage, knowledge search, audit copilot, document/vision extraction) · 24 Notifications, Personalisation & UX · 25 International / Cross-border · 26 Payments Acceptance (merchant) · 27 Specialty / Niche · 28 Credit Cards — advanced tooling · 29 Super-App, Embedded Finance & Wallets · 30 Business Banking — depth · 31 Financial Inclusion, Rural & Agri · 32 Advanced AI & Agentic Banking (AI: agentic assistant, multi-agent orchestration, MCP tools) · 33 Financial Wellness & Personalisation · 34 Notifications, Alerts & UX · 35 Statements, Documents & Self-Service · 36 Rewards, Travel & Perks · 37 New / Emerging niche (AI: research automation, pitchbook, trading-ops, enterprise risk). (detail: [`14_feature_catalog.md`](14_feature_catalog.md))

## Related

- [00_executive_summary_and_signoffs/executive_summary.md](./00_executive_summary_and_signoffs/executive_summary.md) — executive summary & sign-off.
- [03_business_goals_and_kpis.md](./03_business_goals_and_kpis.md) — goals, KPI targets, measurement discipline.
- [09_non_functional_business_constraints.md](./09_non_functional_business_constraints.md) — NFR targets (availability, latency, scale, accessibility, compliance).
- [13_high_level_solution_overview.md](./13_high_level_solution_overview.md) — high-level architecture & component responsibilities.
- [14_feature_catalog.md](./14_feature_catalog.md) — combined feature + AI use-case catalog (~485 features + ~46 AI use cases).