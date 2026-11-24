# 07 — Personas and Business Scenarios

## Personas

| Persona | Goals | Pain points | Key scenarios |
|---|---|---|---|
| **Everyday Retail** | Get paid, spend, pay bills, send money, budget, save, build credit | Hidden fees, slow support, unsupported fraud escalation | Pay a bill, split with a friend, freeze a card, build credit |
| **Saver / Goal-setter** | Automate savings, earn interest, reach goals | Low rates, no automation | Create a goal, round-up save, salary sort, automate savings |
| **Borrower** | Personal loan, auto / mortgage, BNPL, overdraft | Slow decisions, opaque pricing, thin-file exclusion | Apply for a loan, use BNPL at checkout, get overdraft buffer |
| **Self-directed Investor** | Trade, track portfolio (stocks/ETFs/crypto), build wealth | High fees, poor tools | Buy fractional shares, view portfolio health, trade crypto |
| **Hands-off Investor** | Robo-advisor, auto-rebalance, retirement planning | Advice reserved for the wealthy | Onboard robo, auto-rebalance, plan retirement |
| **Small Business / Sole Trader** | Business account, invoicing, expenses, payroll, tax (MTD) | Admin overload, manual AP/AR | Send invoice, reconcile expenses, file tax |
| **Parent / Teen** | Kids' cards, allowances, controls, family pots | No safe way to give kids cards | Set allowance, freeze teen card, parental controls |
| **Traveller / Expat** | Multi-currency, low-FX conversion, global QR, travel insurance | FX markups, remittance friction | Hold USD/EUR, pay by QR abroad, transfer money globally |
| **Vulnerable / Accessibility** | Accessible, supportive service; WCAG 2.2 AA compliance | Inaccessible apps, motor/cognitive barriers | Complete a journey with a screen reader, support adjustments |
| **Compliance Analyst** | Investigate alerts, file SARs | Alert overload, manual research, regulatory change | Investigate an alert, draft a SAR with HITL sign-off |
| **Fraud Analyst** | Triage alerts, stop fraud, confirm cardholder fraud | Too many false positives, escalation debt | Review a triaged alert, confirm fraud, freeze a card |

## Business scenarios

1. **Onboard** a new customer end-to-end in <10 minutes (digital KYC / KYC-KYB automation).
2. **Detect & stop** a card-testing fraud ring in real time (real-time fraud engine, sub-150ms, agentic triage).
3. **Investigate** an AML alert and file a SAR with a human sign-off (agentic AML, HITL, audit trail).
4. **Answer** a customer's "where is my transfer?" via the agentic assistant (conversational banking, escalation).
5. **Approve** a straightforward personal loan straight-through; escalate complex ones (ML + LLM copilot, human sign-off).

## End-state coverage

Each scenario maps to target capabilities (Phase 1 → Phase 2 → Phase 3) and to AI use cases in `docs/01-ai-and-architecture/02-ai-use-case-catalog.md`:

| Scenario | Persona | AI use case | Phase |
|---|---|---|---|
| Onboard | Everyday Retail / Small Business | KYC/KYB & CDD automation (Workflow agent) | 1 |
| Detect & stop | Fraud Analyst | Real-time fraud scoring + agentic triage (Monitoring) | 1 |
| Investigate & file SAR | Compliance Analyst | AML investigation / SAR drafting (Multi-agent) | 1 |
| Answer | Everyday Retail | Customer service / agentic assistant (Workflow) | 2 |
| Approve loan | Borrower | Credit underwriting copilot (Task) | 2 |
