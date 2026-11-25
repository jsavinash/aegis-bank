# Scoping and Constraints

## Coverage
This document scopes the **full product surface** — all 37 feature-catalog sections (~485 features) are in scope and sequenced across Phase 1 → Phase 2 → Phase 3; nothing is deferred out of scope. Authoritative sources:

- **Features & AI use cases:** [`../../01-business-and-strategy/14_feature_catalog.md`](../../01-business-and-strategy/14_feature_catalog.md) (37 sections, ~485 features; ~46 AI use cases)
- **Traceability:** [`06_feature_traceability.md`](./06_feature_traceability.md)
- **Scope boundaries:** [`../../01-business-and-strategy/05_scope_and_boundaries/in_scope_and_out_of_scope.md`](../../01-business-and-strategy/05_scope_and_boundaries/in_scope_and_out_of_scope.md)

## Scope

### Phase 1 — Production-Grade Release (0–3 months)
Accounts, cards (physical + virtual, freeze, controls), payments (domestic + P2P), bill pay, mobile deposit, savings, budgeting, biometric + passkey auth, real-time fraud alerts, agentic AML/SAR, basic agentic assistant + human escalation, KYC, audit trail, WCAG 2.2 AA baseline.

### Phase 2 — AI core + differentiators (3–6 months)
Fraud warm-path triage, credit score, multi-currency, pots/salary-sort, robo-advisor, open banking (aggregation), in-app scam detection, business lite.

### Phase 3 — Scale + depth (6–12 months)
Mortgages, BNPL, crypto, wealth projection, full agentic assistant, kids/teen, business full (payroll/MTD), insurance, super-app, embedded finance/BaaS.

Out of scope (never built by Aegis): physical branch network, proprietary card/payment scheme, own core-banking ledger build (see [`in_scope_and_out_of_scope.md`](../../01-business-and-strategy/05_scope_and_boundaries/in_scope_and_out_of_scope.md)).

## Feature-domain scope (all 37 catalog sections)

| § | Feature domain | Phase(s) | Notes |
|---|---|---|---|
| 1 | Accounts & Deposits | 1 | Core; multi-currency later |
| 2 | Payments & Transfers | 1 | Domestic, P2P; wires/QR/instant |
| 3 | Cards | 1 | Physical + virtual, freeze, controls |
| 4 | Bill Pay & Payment Mgmt | 1 | Autopay, scheduled, split, RTI |
| 5 | Lending & Credit | 1 → 2 | Lite in P1; full underwriting P2 |
| 6 | Savings, Wealth & Investing | 1 → 3 | Savings P1; robo P2; projection P3 |
| 7 | Money Management & Insights | 1 → 2 | Budgeting P1; full insights P2 |
| 8 | Credit & Financial Health | 2 | Score, report, build |
| 9 | Security & Fraud | 1 → 3 | Alerts/freeze P1; scam P2; full GNN P3 |
| 10 | Customer Service & AI | 1 → 3 | Basic assistant P1; full agentic P3 |
| 11 | Business & Commercial Banking | 2 → 3 | Lite P2; full P3 |
| 12 | Treasury & Cash Management | 3 | Corporate/institutional |
| 13 | Private Banking & Wealth (HNW) | 3 | HNW |
| 14 | Kids, Teens & Family | 3 | Family accounts |
| 15 | Open Banking & Embedded Finance | 2 → 3 | Aggregation P2; embedded/BaaS P3 |
| 16 | Super-App & Lifestyle | 3 | Lifestyle |
| 17 | Sustainability / Green Banking | 3 | ESG |
| 18 | Digital Assets & Stablecoins | 3 | Crypto |
| 19 | Rewards & Loyalty | 2 | Rewards |
| 20 | Insurance & Protection | 2 → 3 | Basic P2; deep P3 |
| 21 | Accessibility & Inclusion | 1 | WCAG 2.2 AA baseline, ongoing |
| 22 | Compliance, Risk & Regulatory | 1 → 3 | KYC/AML/audit P1; model risk P3 |
| 23 | Platform, Data & AI Infrastructure | 1 → 3 | Foundation P1; scale P2–P3 |
| 24 | Notifications, Personalisation & UX | 1 → 2 | Basic P1; personalisation P2 |
| 25 | International / Cross-border | 2 | Multi-currency, remittance |
| 26 | Payments Acceptance (merchant) | 3 | Merchant side |
| 27 | Specialty / Niche products | 3 | Niche |
| 28 | Credit Cards — advanced tooling | 3 | Advanced card tooling |
| 29 | Super-App, Embedded Finance & Wallets | 3 | Wallets |
| 30 | Business Banking — depth | 3 | Payroll/MTD depth |
| 31 | Financial Inclusion, Rural & Agri | 3 | Agri/inclusion |
| 32 | Advanced AI & Agentic Banking | 2 → 3 | Agentic |
| 33 | Financial Wellness & Personalisation | 2 → 3 | Wellness |
| 34 | Notifications, Alerts & UX | 2 | Alerts/UX |
| 35 | Statements, Documents & Self-Service | 1 → 2 | Statements P1; self-service P2 |
| 36 | Rewards, Travel & Perks | 3 | Travel/perks |
| 37 | New / Emerging niche domains | 3 | Emerging |

## AI use-case scope (7 domains, ~46 use cases)

All AI use cases are in scope and sequenced by domain (detail: [`../../01-business-and-strategy/14_feature_catalog.md`](../../01-business-and-strategy/14_feature_catalog.md)):

| AI domain | Use cases | Phase(s) | Notes |
|---|---|---|---|
| Customer service & engagement | 7 | 1 → 3 | Basic assistant P1; full conversational P2–P3 |
| Fraud, financial crime & compliance | 13 | 1 → 2 | Real-time fraud + AML/KYC P1; reg reporting P2 |
| Lending & credit | 6 | 2 | Underwriting after governance model exists |
| Wealth & private banking | 5 | 2 → 3 | RM copilot/robo P2; full P3 |
| Payments, treasury & operations | 6 | 2 → 3 | Payment agent/reconciliation P2–P3 |
| Technology, cyber & internal productivity | 5 | 2 → 3 | Ops/knowledge agents P2–P3 |
| Investment banking & markets | 4 | 3 | Adjacent, scale ambition |
| **Total** | **~46** | | |

## Constraints

| Type | Constraint |
|---|---|
| **Regulatory** | Banking licence / BaaS partner; PSD2/3; AML/KYC; GDPR/CCPA; EU AI Act; EAA/WCAG 2.2 AA |
| **Data residency** | Self-host AI stack in-VPC where required |
| **Latency** | Fraud hot path <150ms; balance <200ms |
| **Availability** | 99.95%+ |
| **Team** | Small initial team; AI-first delivery |
| **Timeline** | Phase 1 ~3 months; Phase 2 ~3 months; Phase 3 ~6 months |

## Non-negotiables

- Buy the core; build the AI/risk plane.
- Event-driven from day one.
- Eval-gated CI; HITL for high-consequence actions.
- WCAG 2.2 AA.

## Phase summary

| Feature area | Phase 1 | Phase 2 | Phase 3 |
|---|---|---|---|
| Core banking (accounts, cards, payments) | ✅ in scope | ✅ extended | ✅ deepened |
| AI agents (assistant, AML, fraud) | ✅ basic agentic + escalation, SAR | ✅ warm-path triage | ✅ full agentic across all domains |
| Credit & lending | lite | full underwriting | mortgages, BNPL |
| Wealth & investment | basic savings, budgeting | robo-advisor, multi-currency | wealth projection, full portfolio |
| Security & fraud | real-time alerts, one-tap freeze | warm-path triage, scam detection | full GNN/GBM across all channels |
| Open banking / BaaS | aggregation lite | full aggregation, business lite | super-app, embedded finance |
| Compliance & regulatory | KYC, SAR, audit trail, WCAG AA baseline | regulatory reporting | full model risk, cross-jurisdiction |
| Business banking | — | lite (invoicing) | full (MTD, payroll) |

## Out of scope for all phases

- Physical branches, proprietary payment scheme, own core-ledger build.
