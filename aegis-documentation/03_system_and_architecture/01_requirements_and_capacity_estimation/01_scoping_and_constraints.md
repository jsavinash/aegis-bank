# Scoping and Constraints

## Scope

### Phase 1 — Production-Grade Release (0–3 months)
Accounts, cards (physical + virtual, freeze, controls), payments (domestic + P2P), bill pay, mobile deposit, savings, budgeting, biometric + passkey auth, real-time fraud alerts, basic agentic assistant + human escalation, KYC, audit trail, WCAG 2.2 AA baseline.

### Phase 2 — AI core + differentiators (3–6 months)
Agentic AML/SAR, fraud warm-path triage, credit score, multi-currency, pots/salary-sort, robo-advisor, open banking (aggregation), in-app scam detection, business lite.

### Phase 3 — Scale + depth (6–12 months)
Mortgages, BNPL, crypto, wealth projection, full agentic assistant, kids/teen, business full (payroll/MTD), insurance, super-app, embedded finance/BaaS.

Out of scope (never built by Aegis): mortgage origination, crypto custody, insurance underwriting, merchant acquiring.

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
| AI agents (assistant, AML, fraud) | ✅ basic agentic + escalation | ✅ warm-path triage, SAR | ✅ full agentic across all domains |
| Credit & lending | lite | full underwriting | mortgages, BNPL |
| Wealth & investment | basic savings, budgeting | robo-advisor, multi-currency | wealth projection, full portfolio |
| Security & fraud | real-time alerts, one-tap freeze | warm-path triage, scam detection | full GNN/GBM across all channels |
| Open banking / BaaS | aggregation lite | full aggregation, business lite | super-app, embedded finance |
| Compliance & regulatory | KYC, audit trail, WCAG AA baseline | SAR, regulatory reporting | full model risk, cross-jurisdiction |
| Business banking | basic invoicing, payroll | full (MTD, payroll) | super-app, embedded finance |

## Out of scope for all phases

- Physical branches, full private banking, merchant acquiring.

(End of file - total 44 lines)