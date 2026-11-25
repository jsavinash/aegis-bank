# 08 — High-Level Functional Requirements

> **Coverage:** the full ~485-feature catalog is in [`14_feature_catalog.md`](14_feature_catalog.md) (37 sections, 15 feature types: CORE, PAY, CARD, LEND, WEALTH, BIZ, SEC, AI, DATA, OPEN, LIFE, ESG, COMP, ACC, PLAT); master map in `FEATURE_AND_USE_CASE_COVERAGE.md`. Every requirement maps to a Phase (1/2/3) per `04_current_vs_future_state/to_be_target_capabilities.md`.

| ID | Requirement | Feature types | Priority | Phase |
|---|---|---|---|---|
| FR-01 | Open & manage deposit accounts (checking, savings, term, joint, multi-currency) | CORE | Must | 1 |
| FR-02 | Domestic & international payments, P2P, standing orders, wires, QR, instant rails | PAY | Must | 1 |
| FR-03 | Issue & manage physical & virtual cards, freeze, granular MCC/channel controls | CARD | Must | 1 |
| FR-04 | Bill pay, scheduled payments, autopay, split bills, request-to-pay, mobile deposit | PAY | Must | 1 |
| FR-05 | Personal loans, auto/vehicle, mortgage, BNPL, overdraft, EWA, credit line, origination workflow (lite in Phase 1; full suite Phase 2) | LEND | Must | 1 |
| FR-06 | Savings goals, robo-advisor, self-directed investing, retirement, crypto, portfolio analytics, tax-loss harvesting | WEALTH | Should | 1 |
| FR-07 | Spending dashboard, budgets, cash-flow forecast, net-worth 360°, AI personalised nudges | DATA | Must | 1 |
| FR-08 | Credit score monitoring, credit-building tools, report/history, identity/dark-web monitoring | SEC + DATA | Must | 1 |
| FR-09 | Biometric/passkey authentication, risk-adaptive MFA, device binding, trusted-device management | SEC | Must | 1 |
| FR-10 | Real-time fraud detection (sub-150ms), alerts, one-tap card freeze, behavioural biometrics, scam-call/email detection | SEC + AI | Must | 1 |
| FR-11 | Agentic virtual assistant (voice + chat) with human escalation for fraud/disputes (never trap users in self-service) | AI + SEC | Must | 2 |
| FR-12 | KYC/KYB onboarding (automated <10 min), sanctions/PEP screening, CDD, periodic review | COMP | Must | 1 |
| FR-13 | AML transaction monitoring (GNN/ML scoring), agentic investigation (multi-agent, GraphRAG), SAR filing with HITL sign-off | AI + COMP | Must | 1 |
| FR-14 | Regulatory reporting (FinCEN/FATF/local), immutable audit trail, audit-ready logs | COMP | Must | 1 |
| FR-15 | Business accounts (SME), multi-user access/permissions, invoicing, expense mgmt + receipt capture, accounting integration (Xero/QBO), payroll, tax (MTD), cash-flow forecasting, bulk payments, corporate/team cards | BIZ + DATA | Should | 2 |
| FR-16 | Open banking: account aggregation (PISP), payment initiation, consent management (grant/revoke/expiry), PSD2/3 APIs | OPEN | Should | 2 |
| FR-17 | WCAG 2.2 AA accessibility across all digital journeys; EAA compliance in EU; axe-core CI + manual screen-reader testing | ACC | Must | 1 |
| FR-18 | Multi-currency wallets, low-FX conversion, global QR, travel card (zero-FX), remittance corridors, stablecoin cross-border | PAY + DATA | Should | 2 |
| FR-19 | Notifications & alerts (push/SMS/email), customisable per transaction type/amount; real-time instant notifications; data-access transparency; security meter | PLAT + SEC + DATA | Must | 1 |
| FR-20 | Statements, documents, self-service (PIN reset, card freeze, limits, password change, secure upload/vault); downloadable statements & tax docs; paperless/e-statements | CORE + SEC + ESG + ACC | Must | 1 |
| FR-21 | Card-linked offers, referral programme, tiered plans, points/loyalty currency, personalised rewards | LIFE + DATA | Should | 2 |
| FR-22 | Travel insurance, device/purchase protection, life/income protection, home/renters insurance, pet insurance, embedded insurance at checkout | ESG + WEALTH | Should | 2 |
| FR-23 | Carbon footprint tracker (per transaction), offset purchase, ESG funds, green financing, eco cards, paperless, donations | ESG | Should | 3 |
| FR-24 | Crypto trading, custodial wallet, stablecoin wallets/payouts/remittance, tokenised deposits, programmable money, cross-chain, staking/yield, custody/KYT | Digital assets | Should | 3 |
| FR-25 | Super-app & lifestyle: top-up, travel booking, ride-hailing, event tickets, eSIM, food delivery, mini-apps, partner integrations, chat/voice banking, programmable payments, loyalty currency | LIFE | Should | 3 |
| FR-26 | Islamic/Shariah-compliant banking, microfinance/financial inclusion, gig/migrant-worker bundle, payroll-as-a-service, real-estate/rent-to-own, agri-value-chain finance, diaspora accounts, charity/non-profit accounts, trust/estate accounts | CORE + BIZ + LEND + ESG + SEC | Should | 3 |
| FR-27 | Audit-ready AI: every decision traceable (data/model/policy/human); Langfuse + OTel + audit log; fail-closed guardrails; two-pass structured output; citation-required generation; numbers computed in code | AI + COMP | Must | 1 |

> **Phase mapping:** Phase 1 (production release) = trust, security, compliance foundation: FR-01–FR-10, FR-12–FR-14, FR-17, FR-19–FR-20, FR-27. Phase 2 (differentiation + scale) = conversational banking, credit decisioning, regulatory intelligence, open banking, money management, full lending suite, international/cross-border: FR-11, FR-15–FR-16, FR-18, FR-21–FR-22. Phase 3 (moat expansion) = savings & wealth, business banking, kids/teen/family, rewards & loyalty, insurance, digital assets, super-app, sustainability, merchant acceptance, specialty/niche: FR-23–FR-26. Within each phase, "Must" requirements ship first, then "Should", then items scoped only for later phases.

> **Source-of-truth alignment:** every requirement maps to one or more sections of [`14_feature_catalog.md`](14_feature_catalog.md) and to AI use cases in [`14_feature_catalog.md`](14_feature_catalog.md).