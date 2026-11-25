# In Scope and Out of Scope

Aegis Bank is a **full-featured retail digital bank**. Every product surface in the source-of-truth feature catalog ([`../14_feature_catalog.md`](../14_feature_catalog.md), ~485 features across 37 sections) **and all ~46 AI use cases** are **in scope**, sequenced across Phase 1 (production release) → Phase 2 → Phase 3. Nothing in the catalog is deferred out of scope; "later" items are simply later phases. AI use cases are included as features in the relevant sections below.

## In scope (all phases, 37 sections)

| § | Area | In scope (consolidated, incl. AI features) |
|---|---|---|
| 1 | Accounts & Deposits | Checking, savings (instant/notice/term), joint, multi-currency, pots/spaces, salary sorting, round-ups, auto-save, sweep, escrow, dormant/escheatment |
| 2 | Payments & Transfers | Domestic, P2P, wires, standing orders, scheduled, request-to-pay, QR (domestic + cross-border), instant rails (FedNow/RTP/SEPA Instant), confirmation of payee, per-payment signing, batch, maker-checker, recall, FX, tap-to-pay, wearables; **AI:** agentic payment initiation, payment-instruction repair, cash-flow forecasting |
| 3 | Cards | Physical debit/credit, virtual, single-use, freeze/unfreeze, MCC/channel controls, geo-blocking, real-time alerts, PIN, replacement, wallet provisioning, card-linked offers, credit-building, metal tiers, multi-currency, eco cards |
| 4 | Bill Pay & Payment Mgmt | Biller directory, autopay, reminders, split bill, subscription manager, mobile top-up, mobile check deposit, standing instructions, bill negotiation |
| 5 | Lending & Credit | Personal, auto, mortgage (fixed/variable/offset/tracker), student, overdraft, BNPL, EWA, credit line, secured, origination workflow, ML decisioning, repayment/autopay, delinquency/collections, refinancing, HELOC, POS finance; **AI:** loan origination agent, underwriting copilot, income/document verification, alternative-data scoring, collections |
| 6 | Savings, Wealth & Investing | Goals, interest accounts, robo-advisor, self-directed investing, fractional shares, retirement, crypto, portfolio analytics, projection, auto-rebalance, tax-loss harvesting, round-up investing, ESG portfolios, planning, human advisor, IPO, options, commodities, trust/estate, model portfolios; **AI:** RM copilot, robo-advisory, financial planning, tax-loss harvesting, market-scout |
| 7 | Money Management & Insights | Spending dashboard, budgets, cash-flow forecast, net-worth 360°, personalised nudges, bill/subscription detection, education, gamified challenges, peer comparison, recurring detection, receipt capture, financial health score, wellness |
| 8 | Credit & Financial Health | Score monitoring, report/history, credit-building, identity/dark-web monitoring, score simulator, alerts, free report |
| 9 | Security & Fraud | Biometrics, passkeys/FIDO2, risk-adaptive MFA, OTP, behavioural biometrics, real-time AI fraud, instant alerts + freeze, scam/scam-call detection, malware/overlay detection, device binding, per-payment signing, geo-blocking, cooling-off, consent transparency, privacy export/delete, new-device alerts, session/remote logout, card controls, disputes/chargebacks, agentic case mgmt, deepfake/injection detection, Positive Pay/ACH filters; **AI:** real-time fraud scoring, alert triage, scam interception |
| 10 | Customer Service & AI | Virtual assistant, agentic assistant, live chat + human escalation, co-browsing, secure messaging, appointment booking, complaints/disputes, call-back, branch locator, AI-searchable FAQ, voice assistant, multilingual, accessibility support; **AI:** contact-centre agent, account/card servicing agent, real-time agent assist, voice banking, complaints handling, digital-human guide, financial coaching |
| 11 | Business & Commercial Banking | Business accounts, multi-user/permissions, invoicing, expenses/receipts, accounting integrations, payroll, tax (MTD), cash-flow forecasting, bulk payments, corporate/team cards, merchant services, business loans, invoice factoring, multi-entity/currency, contractor payouts, AP/AR automation, spend controls, approval rules, bookkeeping, tax forms, API/ERP sync, same-day ACH/wire/SWIFT, revenue-based financing, insured cash sweep |
| 12 | Treasury & Cash Management | Real-time cash visibility, cash pooling, ZBA/TBA, sweeps, ACH origination, lockbox, escrow, Positive Pay, 13-week forecasting, FX hedging, ERP liquidity, SWIFT/ISO 20022, earnings credit, working-capital optimisation; **AI:** liquidity monitoring, reconciliation, cash-flow forecasting |
| 13 | Private Banking & Wealth (HNW) | Relationship manager, tailored lending, concentrated-position planning, succession/estate, philanthropy, trust/fiduciary, family office, liquidity-event planning, exclusive rates; **AI:** RM copilot |
| 14 | Kids, Teens & Family | Teen accounts, kids cards/allowances, parental controls, chores→allowance, kids savings goals, teen literacy, family pots, family plans |
| 15 | Open Banking & Ecosystem | Aggregation, PISP, consent mgmt, marketplace, BaaS, embedded/white-label, developer portal/sandbox, webhooks, open-banking data sharing, QR interoperability |
| 16 | Super-App & Lifestyle | Top-up/bills, travel, ride-hailing, events, eSIM, shopping, food delivery, gaming, remittances, gig payouts, mini-apps, wallets, softPOS, agent banking, offline/USSD/SMS, chat/voice banking, programmable payments, loyalty currency |
| 17 | Sustainability / Green | Carbon tracker, offsets, ESG funds, green financing, eco cards, paperless, donations, sustainability insights, green savings, carbon marketplace, impact investing |
| 18 | Digital Assets & Stablecoins | Crypto trading, custodial wallet, stablecoin wallets/payouts/remittance, tokenised deposits, programmable money, cross-chain, staking/yield, custody/KYT, CBDC wallet, digital-credentials wallet |
| 19 | Rewards & Loyalty | Cashback, card-linked offers, referrals, tiered plans, points, personalised rewards, partner discounts, redemption portal, rewards expiry |
| 20 | Insurance & Protection | Travel, device/purchase, life/income, home/renters, pet, embedded insurance |
| 21 | Accessibility & Inclusion | WCAG 2.2 AA, screen-reader, keyboard/focus, contrast, 400% zoom, plain language, multi-sensory, tagged PDFs, support adjustments, voice/switch, reduced-motion/dyslexia fonts |
| 22 | Compliance, Risk & Regulatory | KYC/KYB/CDD, sanctions/PEP, AML monitoring, agentic AML/SAR, fraud case mgmt, regulatory reporting, immutable audit, data residency/GDPR/CCPA, consent, risk segmentation, periodic review, adverse media, no-code rule builder, HITL escalation; **AI:** AML monitoring, investigation agent (Aegis-IR), SAR/STR drafting, KYC/KYB due diligence, sanctions/PEP screening, regulatory reporting, change monitoring, disputes, agentic BI |
| 23 | Platform, Data & AI Infra | Real-time ledger, product engine, pricing/fees, GL/sub-ledgers, CIF, API gateway/portal, event streaming, feature store, data lake/warehouse, vector+graph store, LLM gateway/routing, guardrails/policy, OTel/audit, workflow orchestration, multi-tenancy/RBAC, consent/privacy service; **AI:** developer augmentation, cyber alert triage, enterprise knowledge search, internal audit copilot, document/vision extraction |
| 24 | Notifications, Personalisation & UX | Push/SMS/email, real-time txn alerts, customisable preferences, personalised feed, dark mode, messaging centre, onboarding, quick actions, widgets/watch/shortcuts |
| 25 | International / Cross-border | Multi-currency balances, low-FX, global QR, travel card, remittance corridors, stablecoin cross-border, localised accounts (IBAN), multi-language statements |
| 26 | Payments Acceptance (merchant) | Card acquiring/POS, e-commerce checkout/links, QR merchant, recurring billing, payouts, chargeback mgmt, merchant analytics |
| 27 | Specialty / Niche | Islamic/Shariah, microfinance/inclusion, gig/migrant bundle, payroll-as-a-service, real-estate/rent-to-own, agri/sector lending, diaspora, charity/non-profit, trust/estate, rural/agri-finance, tiered KYC, alternative credit scoring, parametric crop insurance, seasonal loans, value-chain finance, group lending/VSLA/SACCO |
| 28 | Credit Cards — advanced tooling | Instalments, advanced controls, rewards liability, credit-building tools |
| 29 | Super-App, Embedded Finance & Wallets | Wallets, embedded finance, programmable payments, mini-apps |
| 30 | Business Banking — depth | Payroll, MTD, multi-entity, AP/AR automation, spend controls |
| 31 | Financial Inclusion, Rural & Agri | Alternative-data underwriting, seasonal loans, value-chain finance, group lending |
| 32 | Advanced AI & Agentic Banking | Agentic assistant, multi-agent orchestration, MCP tools, HITL; **AI:** agentic assistant, multi-agent orchestration, MCP tool use |
| 33 | Financial Wellness & Personalisation | Wellness nudges, personalisation (no predatory nudges) |
| 34 | Notifications, Alerts & UX | Alert-fatigue controls, regulatory alerts, preferences |
| 35 | Statements, Documents & Self-Service | Statements, documents, self-service, retention |
| 36 | Rewards, Travel & Perks | Travel, perks, partner offers |
| 37 | New / Emerging niche | Tokenised deposits, CBDC, and other emerging domains; **AI:** research automation, pitchbook generation, trading-ops exception handling, enterprise risk assessment (adjacent) |

> **AI features:** ~46 use cases across 7 domains — Customer service & engagement (7), Fraud/financial crime/compliance (13), Lending & credit (6), Wealth & private banking (5), Payments/treasury/operations (6), Technology/cyber/productivity (5), Investment banking & markets (4, adjacent). Detail: [`08_ai_use_case_catalog.md`](../../03_system_and_architecture/01_requirements_and_capacity_estimation/08_ai_use_case_catalog.md).

## Out of scope

| Area | Why out |
|---|---|
| **Physical branch network** | Digital-first; branches handled via partners/locators, not owned |
| **Proprietary payment scheme / card network** | Rely on existing rails (Visa/Mastercard/ACH/FedNow/SEPA); not issuing own scheme |
| **Own core-banking ledger build** | Buy/configure a ledger product engine (build-vs-buy decision, see [`../10_cost_roi_and_alternatives/build_vs_buy_options.md`](../10_cost_roi_and_alternatives/build_vs_buy_options.md)) |

## Notes

- **Sequencing:** all in-scope areas ship across Phase 1 → Phase 2 → Phase 3 (see [`../04_current_vs_future_state/to_be_target_capabilities.md`](../04_current_vs_future_state/to_be_target_capabilities.md) for the capability → phase map). "Out of scope" means *never built by Aegis*, not *deferred*.
- **Assumptions & dependencies:** see [`assumptions_and_dependencies.md`](./assumptions_and_dependencies.md).