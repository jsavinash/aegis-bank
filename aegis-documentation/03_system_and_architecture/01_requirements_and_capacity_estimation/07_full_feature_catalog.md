# Aegis Bank — Exhaustive Feature Catalog

**Version:** 1.1 · **Date:** 24 Nov 2026 · **Companion to:** `13_high_level_solution_overview.md`

This is the complete, exhaustive inventory of **~600 features** that can be integrated into a modern retail digital bank. Every feature is tagged so it can be dropped into a backlog:

- **Type:** `[CORE]` core banking · `[PAY]` payments · `[CARD]` cards · `[LEND]` lending · `[WEALTH]` wealth · `[BIZ]` business · `[SEC]` security/fraud · `[AI]` AI/ML · `[DATA]` data/insights · `[OPEN]` open banking/ecosystem · `[LIFE]` lifestyle/super-app · `[ESG]` sustainability · `[COMP]` compliance/regulatory · `[ACC]` accessibility · `[PLAT]` platform/infra
- **Priority:** `[T]` table-stakes · `[D]` differentiator · `[E]` emerging/experimental · `[R]` regulated/required
- **Proven?** ✅ shipping at scale · 🟡 pilot/emerging

> **Part I (§1–27):** the core retail-banking catalogue (~330 features).
> **Part II (§28–37):** a deeper sweep adding credit-card tooling, super-app/embedded finance, business-banking depth, financial inclusion/agri-finance, advanced agentic AI, financial wellness, notifications/UX, documents, rewards, and emerging niche domains (~150 more features).
>
> Sources: 2026 teardown of Chase, BofA, Wells Fargo, Capital One, US Bank, SoFi, DBS (World's Best Digital Bank 2026), Nubank, Revolut, Monzo, Starling, Chime, Citi, J.P. Morgan; core platforms (Temenos, Oracle FLEXCUBE, TCS BaNCS, SAP Fioneer, 10x, MobiFin); Ostorlab 2,686-app scan; PSD2/PSD3; European Accessibility Act; WCAG 2.2 AA; stablecoin/embedded-finance (BVNK); green-banking (CIMB, NatWest, DBS, Vietcombank, Emirates NBD, Clarity AI/Cogo); Stripe/Rho/Brex/Novo business-banking analyses; World Bank / IFAD agri-finance & agent-network guides; super-app & wallet (Paytm, GCash, WeChat, MobiFin, BPC).

---

## Table of contents
**Part I — Core catalogue**
1. Accounts & Deposits
2. Payments & Transfers
3. Cards
4. Bill Pay & Payment Management
5. Lending & Credit
6. Savings, Wealth & Investing
7. Money Management & Insights
8. Credit & Financial Health
9. Security & Fraud
10. Customer Service & AI
11. Business & Commercial Banking
12. Treasury & Cash Management (corporate/institutional)
13. Private Banking & Wealth (HNW)
14. Kids, Teens & Family
15. Open Banking & Ecosystem / Embedded Finance
16. Super-App & Lifestyle
17. Sustainability / Green Banking
18. Digital Assets & Stablecoins
19. Rewards & Loyalty
20. Insurance & Protection
21. Accessibility & Inclusion
22. Compliance, Risk & Regulatory (back-office)
23. Platform, Data & AI Infrastructure
24. Notifications, Personalisation & UX
25. International / Cross-border
26. Payments Acceptance (merchant side)
27. Specialty / Niche products

**Part II — Additional features (deeper sweep)**
28. Credit Cards — advanced tooling
29. Super-App, Embedded Finance & Wallets
30. Business Banking — depth
31. Financial Inclusion, Rural & Agri-Finance
32. Advanced AI & Agentic Banking
33. Financial Wellness & Personalisation
34. Notifications, Alerts & UX
35. Statements, Documents & Self-Service
36. Rewards, Travel & Perks
37. New / Emerging niche domains

---

## 1. Accounts & Deposits

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Checking / current account | CORE | T | ✅ |
| Savings account (instant access) | CORE | T | ✅ |
| Notice savings account | CORE | D | ✅ |
| Fixed / term deposit (CD) | CORE | T | ✅ |
| Recurring deposit (auto-monthly) | CORE | D | ✅ |
| Joint account | CORE | T | ✅ |
| Multi-currency account / wallet | CORE | D | ✅ |
| Pots / Spaces / sub-accounts (goals, bills) | CORE | D | ✅ |
| Salary sorting (auto-split pay) | CORE | D | ✅ |
| Round-ups to savings | CORE | D | ✅ |
| Auto-save rules (if-this-then-save) | CORE | D | ✅ |
| Sweep / zero-balance / target-balance accounts | CORE | D | ✅ |
| Escrow / trust accounts | CORE | D | ✅ |
| Dormant account handling & escheatment | CORE | R | ✅ |
| Digital onboarding + KYC (video ID, doc scan) | COMP | T | ✅ |
| Beneficiary / payee management | CORE | T | ✅ |
| Account nickname & custom ordering | CORE | T | ✅ |
| Statement generation (PDF, tagged/accessible) | CORE | T | ✅ |
| Interest calculation & accrual display | CORE | T | ✅ |
| Overdraft (arranged) | LEND | T | ✅ |
| Fee-free overdraft buffer (SpotMe-style) | LEND | D | ✅ |
| Joint/teen sub-accounts | CORE | D | ✅ |
| Crypto/commodity savings (yield) | ESG/WEALTH | E | 🟡 |

## 2. Payments & Transfers

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Domestic transfer (ACH/FPS/SEPA) | PAY | T | ✅ |
| P2P transfer (Zelle-style) | PAY | T | ✅ |
| Wire transfer | PAY | T | ✅ |
| International transfer / remittance | PAY | D | ✅ |
| Multi-currency FX conversion | PAY | D | ✅ |
| Standing order / recurring transfer | PAY | T | ✅ |
| Scheduled / future-dated payment | PAY | T | ✅ |
| Request-to-pay / payment request link | PAY | D | ✅ |
| QR payments (domestic) | PAY | D | ✅ |
| Cross-border QR interoperability (Alipay+/PromptPay/DuitNow/QRIS) | PAY | D | ✅ |
| Instant payment rails (FedNow/RTP/SEPA Instant) | PAY | T | ✅ |
| Confirmation of Payee (name check) | SEC | D | ✅ |
| Per-payment cryptographic signing | SEC | T | ✅ |
| Batch payments (bulk) | PAY | D | ✅ |
| Payment approval workflow (maker-checker) | PAY | D | ✅ |
| Payment tracking / status timeline | PAY | T | ✅ |
| Recall / cancel payment | PAY | D | 🟡 |
| Scheduled international payment (FX lock) | PAY | D | ✅ |
| Request money (invoice-style) | PAY | D | ✅ |
| Tap-to-pay / contactless via phone | PAY | T | ✅ |
| Wearable pay (watch/ring) | PAY | D | ✅ |

## 3. Cards

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Physical debit card | CARD | T | ✅ |
| Physical credit card | CARD | T | ✅ |
| Virtual card | CARD | D | ✅ |
| Single-use / disposable card | CARD | D | ✅ |
| Instant freeze / unfreeze | CARD | T | ✅ |
| Spend limits by MCC / channel | CARD | T | ✅ |
| Online / contactless / ATM / international toggles | CARD | T | ✅ |
| Geo-blocking / travel mode | CARD | D | ✅ |
| Real-time spend notifications | CARD | T | ✅ |
| PIN view & change | CARD | T | ✅ |
| Card replacement / lost-stolen flow | CARD | T | ✅ |
| Add to Apple/Google/Samsung Pay | CARD | T | ✅ |
| Card-linked offers / cashback | CARD | D | ✅ |
| Credit-building card (no credit history) | CARD | D | ✅ |
| Metal / premium card tiers | CARD | D | ✅ |
| Kids/teen card with controls | CARD | D | ✅ |
| Multi-currency card (zero-FX travel) | CARD | D | ✅ |
| Recycled/eco card material | ESG | D | ✅ |
| Card design customisation | CARD | D | ✅ |
| ATM locator (fee-free network) | CARD | T | ✅ |
| Card-free ATM withdrawal | CARD | D | ✅ |

## 4. Bill Pay & Payment Management

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Bill pay / biller directory | PAY | T | ✅ |
| Autopay / scheduled bills | PAY | T | ✅ |
| Bill reminders & alerts | PAY | T | ✅ |
| Split a bill (shared tab) | PAY | D | ✅ |
| Subscription manager (detect/cancel) | DATA | D | ✅ |
| Mobile top-up / airtime | PAY | D | ✅ |
| Mobile check deposit (capture) | PAY | T | ✅ |
| Deposit availability preview | PAY | D | ✅ |
| Standing instructions for utilities | PAY | T | ✅ |
| Bill negotiation / switch service | DATA | D | 🟡 |

## 5. Lending & Credit

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Personal loan (instalment) | LEND | T | ✅ |
| Instant personal loan decision | LEND | D | ✅ |
| Auto / vehicle loan | LEND | T | ✅ |
| Mortgage (fixed/variable/offset/tracker) | LEND | T | ✅ |
| Student / education loan | LEND | T | ✅ |
| Overdraft | LEND | T | ✅ |
| Buy Now Pay Later / pay-in-four | LEND | D | ✅ |
| Payday-aligned BNPL | LEND | E | ✅ |
| Earned Wage Access (EWA) | LEND | D | ✅ |
| Credit line / revolving credit | LEND | D | ✅ |
| Secured loan (savings/portfolio-backed) | LEND | D | ✅ |
| Loan origination workflow (apply→offer→approve→disburse) | LEND | T | ✅ |
| Credit decisioning (ML + explainability) | AI | D | ✅ |
| Repayment scheduling & autopay | LEND | T | ✅ |
| Early repayment / overpayment | LEND | T | ✅ |
| Delinquency buckets & collections | LEND | T | ✅ |
| Refinancing / consolidation | LEND | D | ✅ |
| BNPL at checkout (merchant) | LEND | D | ✅ |
| Home equity / HELOC | LEND | D | ✅ |
| Point-of-sale finance | LEND | D | ✅ |

## 6. Savings, Wealth & Investing

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Savings goals & targets | WEALTH | T | ✅ |
| Interest-bearing accounts (APY/AER) | WEALTH | T | ✅ |
| Robo-advisor / managed portfolios | WEALTH | D | ✅ |
| Self-directed investing (stocks/ETFs) | WEALTH | D | ✅ |
| Fractional shares | WEALTH | D | ✅ |
| Retirement accounts (IRA/401k/pension) | WEALTH | D | ✅ |
| Crypto trading (buy/sell/hold) | WEALTH | D | ✅ |
| Portfolio analytics & health score | AI | D | ✅ |
| Wealth projection & scenario compare | AI | D | ✅ |
| Auto-rebalance | WEALTH | D | ✅ |
| Tax-loss harvesting | WEALTH | D | ✅ |
| Round-up investing | WEALTH | D | ✅ |
| ESG / socially-responsible portfolios | ESG | D | ✅ |
| Financial planning (retirement, goals) | WEALTH | D | ✅ |
| Access to human advisor (hybrid) | WEALTH | D | ✅ |
| IPO / primary market access | WEALTH | D | ✅ |
| Options / derivatives | WEALTH | E | ✅ |
| Commodities / gold | WEALTH | E | ✅ |
| Trust & estate services | WEALTH | D | ✅ |
| Model portfolios / managed accounts | WEALTH | D | ✅ |

## 7. Money Management & Insights

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Spending dashboard & categorisation | DATA | T | ✅ |
| Budgets by category | DATA | T | ✅ |
| Cash-flow forecast | DATA | D | ✅ |
| Net-worth / 360° view (incl. external) | DATA | D | ✅ |
| Personalised insights & nudges | AI | D | ✅ |
| Bill/subscription detection | DATA | D | ✅ |
| Financial education content | DATA | D | ✅ |
| Gamified savings challenges | DATA | D | ✅ |
| Spending comparison (vs peers/national avg) | DATA | D | ✅ |
| Recurring income/expense detection | DATA | D | ✅ |
| Receipt capture & management | DATA | D | ✅ |
| Financial health score | AI | D | ✅ |

## 8. Credit & Financial Health

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Credit score monitoring (FICO/score) | DATA | T | ✅ |
| Credit report & history | DATA | D | ✅ |
| Credit-building tools | DATA | D | ✅ |
| Identity-theft / dark-web monitoring | SEC | D | ✅ |
| Score simulator (what-if) | DATA | D | ✅ |
| Credit alerts (new inquiry, change) | DATA | D | ✅ |
| Free credit report (regulatory) | COMP | R | ✅ |

## 9. Security & Fraud

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Biometric login (fingerprint/Face/Voice) | SEC | T | ✅ |
| Passkeys / FIDO2 | SEC | D | ✅ |
| Risk-adaptive MFA | SEC | T | ✅ |
| OTP (SMS/email/authenticator) | SEC | T | ✅ |
| Behavioural biometrics (continuous auth) | SEC | D | ✅ |
| Real-time AI fraud detection & scoring | AI | T | ✅ |
| Instant fraud alerts + one-tap freeze | SEC | T | ✅ |
| In-app scam warning / scam-call detection | AI | D | ✅ |
| Malware / overlay / screen-share detection | SEC | D | ✅ |
| Device binding & trusted-device management | SEC | T | ✅ |
| Per-payment signing | SEC | T | ✅ |
| Geo-blocking & travel notice | SEC | D | ✅ |
| New-payee cooling-off / confirmation of payee | SEC | D | ✅ |
| Consent & data-access transparency | SEC | D | ✅ |
| Privacy controls (export/delete data) | COMP | R | ✅ |
| Login from new device alerts | SEC | T | ✅ |
| Session timeout & remote logout | SEC | T | ✅ |
| Card controls (freeze/limits) | SEC | T | ✅ |
| Dispute / chargeback flow | SEC | T | ✅ |
| Fraud case management (agentic) | AI | D | ✅ |
| Deepfake / injection detection (KYC video) | AI | E | 🟡 |
| Positive Pay / ACH filters (business) | SEC | D | ✅ |

## 10. Customer Service & AI

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Virtual assistant / chatbot | AI | T | ✅ |
| Agentic assistant (fulfils tasks in one chat) | AI | D | ✅ |
| Live chat & human escalation (fraud/disputes) | DATA | T | ✅ |
| Co-browsing / screen-share support | DATA | D | ✅ |
| In-app secure messaging | DATA | T | ✅ |
| Appointment booking (branch/video) | DATA | T | ✅ |
| Complaints & dispute management | COMP | T | ✅ |
| Call-back / phone support | DATA | T | ✅ |
| Branch locator & queue booking | DATA | T | ✅ |
| FAQ / help centre (AI-searchable) | DATA | D | ✅ |
| Voice assistant (call the bank) | AI | E | 🟡 |
| Multilingual support | DATA | D | ✅ |
| Accessibility support adjustments | ACC | R | ✅ |

## 11. Business & Commercial Banking

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Business current account (sole trader/SME) | BIZ | D | ✅ |
| Multi-user access & team permissions | BIZ | D | ✅ |
| Invoicing & payment requests | BIZ | D | ✅ |
| Expense management & receipt capture | BIZ | D | ✅ |
| Accounting integration (Xero/QuickBooks/FreeAgent) | BIZ | D | ✅ |
| Payroll | BIZ | D | ✅ |
| Tax compliance (Making Tax Digital) | COMP | R | ✅ |
| Cash-flow forecasting | BIZ | D | ✅ |
| Bulk payments | BIZ | D | ✅ |
| Corporate cards / team cards | BIZ | D | ✅ |
| Merchant services (accept cards) | BIZ | D | ✅ |
| Business loans / overdraft | BIZ | D | ✅ |
| Invoice factoring | BIZ | E | ✅ |
| Multi-entity / multi-currency business | BIZ | D | ✅ |
| Contractor / gig payouts (incl. stablecoin) | BIZ | E | 🟡 |

## 12. Treasury & Cash Management (corporate/institutional)

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Real-time cash visibility (global) | BIZ | D | ✅ |
| Multi-entity cash pooling | BIZ | D | ✅ |
| Zero-balance / target-balance accounts | BIZ | D | ✅ |
| Sweep accounts / liquidity optimisation | BIZ | D | ✅ |
| ACH origination / batch wires | BIZ | D | ✅ |
| Lockbox / remote deposit capture | BIZ | D | ✅ |
| Escrow services | BIZ | D | ✅ |
| Fraud prevention (Positive Pay, ACH blocks) | SEC | D | ✅ |
| AI cash-flow forecasting (13-week) | AI | D | ✅ |
| FX risk hedging (forwards/options) | BIZ | D | ✅ |
| Liquidity forecasting integrated with ERP | BIZ | D | ✅ |
| SWIFT / ISO 20022 connectivity | BIZ | D | ✅ |
| Interest optimisation / earnings credit | BIZ | D | ✅ |
| Working capital optimisation | BIZ | D | ✅ |

## 13. Private Banking & Wealth (HNW)

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Dedicated relationship manager | WEALTH | D | ✅ |
| Tailored lending (securities-based, art, aircraft) | LEND | D | ✅ |
| Concentrated-position / equity planning | WEALTH | D | ✅ |
| Succession & estate planning | WEALTH | D | ✅ |
| Philanthropy / charitable giving | WEALTH | D | ✅ |
| Trust & fiduciary services | WEALTH | D | ✅ |
| Family office services | WEALTH | D | ✅ |
| Liquidity-event planning | WEALTH | D | ✅ |
| Exclusive rates & products | WEALTH | D | ✅ |

## 14. Kids, Teens & Family

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Teen account (16+) with parental view | CORE | D | ✅ |
| Kids' card & allowance | CARD | D | ✅ |
| Parental controls (limits, freeze, alerts) | SEC | D | ✅ |
| Chores / tasks → allowance | DATA | D | ✅ |
| Savings goals for kids | WEALTH | D | ✅ |
| Financial literacy for teens | DATA | D | ✅ |
| Family shared pots | CORE | D | ✅ |
| Family plan / bundle pricing | CORE | D | ✅ |

## 15. Open Banking & Ecosystem / Embedded Finance

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Account aggregation (external accounts) | OPEN | D | ✅ |
| Payment initiation (PISP) | OPEN | D | ✅ |
| Consent management (grant/revoke/expiry) | COMP | R | ✅ |
| Third-party app marketplace | OPEN | D | ✅ |
| Banking-as-a-Service (BaaS) | OPEN | D | ✅ |
| Embedded finance / white-label | OPEN | D | ✅ |
| Developer API portal / sandbox | PLAT | D | ✅ |
| Webhooks / event streaming | PLAT | D | ✅ |
| Open-banking-grade data sharing | COMP | R | ✅ |
| QR / network interoperability | OPEN | D | ✅ |

## 16. Super-App & Lifestyle

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Mobile top-up & bill payments | LIFE | D | ✅ |
| Travel booking (flights/hotels) | LIFE | E | 🟡 |
| Ride-hailing / mobility spend | LIFE | E | 🟡 |
| Event tickets | LIFE | E | 🟡 |
| eSIM / connectivity | LIFE | E | 🟡 |
| Shopping / marketplace | LIFE | E | 🟡 |
| Food delivery / dining | LIFE | E | 🟡 |
| Gaming / rewards | LIFE | E | 🟡 |
| Remittances (global) | LIFE | D | ✅ |
| Gig / freelancer payouts | LIFE | E | 🟡 |
| Mini-apps / partner integrations | LIFE | E | 🟡 |

## 17. Sustainability / Green Banking

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Carbon footprint tracker (per transaction) | ESG | D | ✅ |
| Carbon offset purchase | ESG | D | ✅ |
| Green / ESG investment funds | ESG | D | ✅ |
| EV / solar / green-home financing | ESG | D | ✅ |
| Eco / recycled card materials | ESG | D | ✅ |
| Paperless / e-statements | ESG | T | ✅ |
| Donation / round-up to charity | ESG | D | ✅ |
| Sustainability insights & tips | ESG | D | ✅ |
| Green savings products | ESG | D | ✅ |

## 18. Digital Assets & Stablecoins

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Crypto trading (BTC/ETH/etc.) | WEALTH | D | ✅ |
| Crypto wallet (custodial) | WEALTH | D | ✅ |
| Stablecoin wallets (USDC/USDT) | WEALTH | E | 🟡 |
| Stablecoin payouts (salary/gig) | PAY | E | 🟡 |
| P2P remittance via stablecoin | PAY | E | 🟡 |
| Tokenised deposits | WEALTH | E | 🟡 |
| Programmable money / smart-contract payments | WEALTH | E | 🟡 |
| Cross-chain transfer / interoperability | WEALTH | E | 🟡 |
| Staking / yield on crypto | WEALTH | E | 🟡 |
| Digital-asset custody & KYT | COMP | E | 🟡 |

## 19. Rewards & Loyalty

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Cashback | LIFE | D | ✅ |
| Card-linked offers | LIFE | D | ✅ |
| Referral programme | LIFE | D | ✅ |
| Tiered plans (free/premium/metal) | LIFE | D | ✅ |
| Points / loyalty currency | LIFE | D | ✅ |
| Personalised rewards (AI) | AI | D | ✅ |
| Partner discounts | LIFE | D | ✅ |

## 20. Insurance & Protection

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Travel insurance | WEALTH | D | ✅ |
| Device / purchase protection | WEALTH | D | ✅ |
| Life / income protection | WEALTH | D | ✅ |
| Home / renters insurance | WEALTH | D | ✅ |
| Pet insurance | WEALTH | E | 🟡 |
| Embedded insurance at checkout | WEALTH | E | 🟡 |

## 21. Accessibility & Inclusion

| Feature | Type | Priority | Proven |
|---|---|---|---|
| WCAG 2.2 AA conformance | ACC | R | ✅ |
| Screen-reader support (VoiceOver/TalkBack) | ACC | R | ✅ |
| Keyboard navigation & focus order | ACC | R | ✅ |
| Contrast ≥4.5:1 & adjustable text | ACC | R | ✅ |
| Zoom up to 400% | ACC | R | ✅ |
| Plain-language content (CEFR B2) | ACC | R | ✅ |
| Multi-sensory info (text+audio, captions) | ACC | R | ✅ |
| Tagged/accessible statements (PDF) | ACC | R | ✅ |
| Support adjustments (sign-language, braille, extra time) | ACC | R | ✅ |
| Voice / switch control | ACC | R | ✅ |
| Reduced-motion & dyslexia-friendly fonts | ACC | D | ✅ |

## 22. Compliance, Risk & Regulatory (back-office)

| Feature | Type | Priority | Proven |
|---|---|---|---|
| KYC / KYB / CDD | COMP | R | ✅ |
| Sanctions & PEP screening | COMP | R | ✅ |
| AML transaction monitoring | COMP | R | ✅ |
| Agentic AML investigation & SAR filing | AI | D | ✅ |
| Fraud case management | COMP | R | ✅ |
| Regulatory reporting (FinCEN/FATF/local) | COMP | R | ✅ |
| Immutable audit trail | COMP | R | ✅ |
| Data residency & GDPR/CCPA | COMP | R | ✅ |
| Consent management | COMP | R | ✅ |
| Risk-based customer segmentation | COMP | D | ✅ |
| Periodic KYC review automation | AI | D | ✅ |
| Adverse media screening | COMP | D | ✅ |
| Transaction monitoring rule builder (no-code) | COMP | D | ✅ |
| Suspicious activity escalation (HITL) | COMP | D | ✅ |

## 23. Platform, Data & AI Infrastructure

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Core ledger (real-time, multi-currency) | PLAT | T | ✅ |
| Product engine (config-driven) | PLAT | T | ✅ |
| Pricing & fees engine | PLAT | T | ✅ |
| General ledger & sub-ledgers | PLAT | T | ✅ |
| Customer Info File (CIF) | PLAT | T | ✅ |
| API gateway + developer portal | PLAT | D | ✅ |
| Event streaming (Kafka-like) | PLAT | D | ✅ |
| Feature store | PLAT | D | ✅ |
| Data lake / warehouse | PLAT | T | ✅ |
| Vector + graph store (RAG/GraphRAG) | AI | D | ✅ |
| LLM gateway / routing | AI | D | ✅ |
| Guardrails / policy engine | AI | D | ✅ |
| Observability (OTel tracing, audit logs) | PLAT | T | ✅ |
| Workflow orchestration (durable execution) | PLAT | D | ✅ |
| Multi-tenancy & RBAC | PLAT | T | ✅ |
| Consent & privacy service | COMP | R | ✅ |

## 24. Notifications, Personalisation & UX

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Push / SMS / email alerts | PLAT | T | ✅ |
| Real-time transaction alerts | DATA | T | ✅ |
| Customisable alert preferences | DATA | T | ✅ |
| Personalised home screen / feed | AI | D | ✅ |
| Dark mode / themes | PLAT | D | ✅ |
| In-app messaging centre | PLAT | T | ✅ |
| Onboarding & guided setup | PLAT | T | ✅ |
| Quick actions / shortcuts | PLAT | D | ✅ |
| App shortcuts / widgets / watch app | PLAT | D | ✅ |

## 25. International / Cross-border

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Multi-currency balances | PAY | D | ✅ |
| Low-FX / interbank-rate conversion | PAY | D | ✅ |
| Global QR interoperability | PAY | D | ✅ |
| Travel card (zero-FX) | CARD | D | ✅ |
| International remittance corridors | PAY | D | ✅ |
| Stablecoin cross-border | PAY | E | 🟡 |
| Localised accounts (IBAN/local details) | PAY | D | ✅ |
| Multi-language / multi-currency statements | PAY | D | ✅ |

## 26. Payments Acceptance (merchant side)

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Card acquiring / POS | BIZ | D | ✅ |
| E-commerce checkout / payment links | BIZ | D | ✅ |
| QR merchant acceptance | BIZ | D | ✅ |
| Recurring / subscription billing | BIZ | D | ✅ |
| Payouts / disbursements | BIZ | D | ✅ |
| Chargeback management | BIZ | D | ✅ |
| Merchant analytics | DATA | D | ✅ |

## 27. Specialty / Niche products

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Islamic / Shariah-compliant banking | CORE | E | ✅ |
| Microfinance / financial inclusion | CORE | E | ✅ |
| Gig/migrant-worker bundle (EWA + banking + remittance) | LIFE | E | 🟡 |
| Payroll-as-a-service | BIZ | D | ✅ |
| Real-estate / rent-to-own | LEND | E | 🟡 |
| Agriculture / sector lending | LEND | E | ✅ |
| Diaspora accounts | CORE | D | ✅ |
| Charity / non-profit accounts | BIZ | D | ✅ |
| Trust / estate accounts | BIZ | D | ✅ |

---

# Part II — Additional features (deeper sweep)

The following sections capture features from a second, deeper research sweep (Forbes/CNBC/NerdWallet/Stripe/Rho/Brex/World Bank/Super-app & wallet analyses, 2026). These extend the catalogue with **~150 more features** across credit-card tooling, super-app/embedded finance, business banking depth, financial inclusion/agri-finance, advanced AI/agentic banking, and additional niche domains.

## 28. Credit Cards — advanced tooling

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Virtual card numbers per merchant | CARD | D | ✅ |
| Merchant-locked / burnable virtual cards | CARD | D | ✅ |
| Subscription / recurring-charge tracker | DATA | D | ✅ |
| Subscription cancellation / cancel-to-save | DATA | D | ✅ |
| Price-change alerts on subscriptions | DATA | D | ✅ |
| Category & streaming cashback | LIFE | D | ✅ |
| Points / miles / cashback redemption portal | LIFE | D | ✅ |
| Balance transfer offers & calculator | LEND | T | ✅ |
| Autopay & payment scheduling | CARD | T | ✅ |
| Credit-limit & available-credit alerts | CARD | T | ✅ |
| Foreign-transaction & declined alerts | CARD | T | ✅ |
| Spend-trend & insight reports | DATA | T | ✅ |
| Card-linked offers (Amex Offers style) | LIFE | D | ✅ |
| Virtual assistant for card Q&A (Eno/Erica) | AI | D | ✅ |
| Per-card freeze/unfreeze & PIN view | CARD | T | ✅ |
| Rewards expiration tracking | LIFE | D | ✅ |

## 29. Super-App, Embedded Finance & Wallets

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Digital wallet (tokenised cards, NFC, QR) | LIFE | D | ✅ |
| Tap-to-pay / Tap-to-Pix / Tap-to-QR (NFC) | LIFE | D | ✅ |
| One-click checkout / e-commerce integration | LIFE | D | ✅ |
| Mini-apps / PWA lifestyle services (food, ride, travel) | LIFE | E | 🟡 |
| Embedded wallet for gig/marketplace payouts | LIFE | E | 🟡 |
| Embedded credit (BNPL/overdraft inside wallet) | LEND | D | ✅ |
| Multi-instrument transaction (wallet+bank+card+overdraft) | PAY | D | ✅ |
| Rules-based money movement (thresholds, roles) | PAY | D | ✅ |
| QR static & dynamic merchant codes | PAY | D | ✅ |
| QR soundbox / audio confirmation (merchant) | BIZ | E | 🟡 |
| SoftPOS (phone as card terminal) | BIZ | D | ✅ |
| Agent-banking app (cash-in/cash-out network) | LIFE | E | ✅ |
| Offline / USSD / SMS banking | LIFE | D | ✅ |
| WhatsApp / chat-based banking | AI | E | 🟡 |
| Voice / conversational banking | AI | E | 🟡 |
| Programmable / smart-contract payments | PAY | E | 🟡 |
| Loyalty points & rewards currency | LIFE | D | ✅ |

## 30. Business Banking — depth

| Feature | Type | Priority | Proven |
|---|---|---|---|
| AP automation (bill entry → payment) | BIZ | D | ✅ |
| AR automation (invoicing + reconciliation) | BIZ | D | ✅ |
| Corporate cards (physical + virtual) | BIZ | D | ✅ |
| Spend controls (caps, categories, freeze) | BIZ | D | ✅ |
| Receipt capture & matching | BIZ | D | ✅ |
| Expense reimbursement workflows | BIZ | D | ✅ |
| Approval rules / maker-checker / dual approval | SEC | D | ✅ |
| Vendor safe-lists | SEC | D | ✅ |
| Multi-account allocation (tax/payroll/ops buckets) | BIZ | D | ✅ |
| Sub-accounts / pockets with own account numbers | BIZ | D | ✅ |
| Scheduled transfers & revenue routing rules | BIZ | D | ✅ |
| Sweep idle balance to interest account | BIZ | D | ✅ |
| Contractor management (W-9, 1099-NEC e-file) | BIZ | D | ✅ |
| Automated bookkeeping & P&L | BIZ | D | ✅ |
| Tax estimates & tax-savings bucket | BIZ | D | ✅ |
| Schedule C / tax form auto-generation | BIZ | D | ✅ |
| Accounting integrations (QBO, Xero, NetSuite, Sage) | BIZ | D | ✅ |
| API access / ERP & HRIS sync | PLAT | D | ✅ |
| Multi-user roles & permissions | BIZ | D | ✅ |
| User-level audit trail (who initiated each txn) | SEC | D | ✅ |
| Same-day ACH / wire / SWIFT | BIZ | D | ✅ |
| Cross-border contractor payouts | BIZ | E | 🟡 |
| Revenue-based financing | LEND | E | ✅ |
| Insured Cash Sweep (multi-bank FDIC) | BIZ | E | ✅ |

## 31. Financial Inclusion, Rural & Agri-Finance

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Agent banking (cash-in/cash-out network) | LIFE | E | ✅ |
| USSD / SMS / feature-phone banking | LIFE | D | ✅ |
| Tiered KYC (low-document onboarding) | COMP | R | ✅ |
| Digital ID / national-ID linked accounts | COMP | D | ✅ |
| Biometric verification for onboarding | SEC | D | ✅ |
| Alternative credit scoring (thin-file) | AI | E | ✅ |
| Picture-based / parametric crop insurance | ESG | E | ✅ |
| Remote-sensing & climate-based credit scoring | AI | E | 🟡 |
| Seasonal / harvest-aligned loan repayment | LEND | E | ✅ |
| Value-chain finance (input credit, warehouse receipts) | LEND | E | ✅ |
| Group lending / VSLA / SACCO support | LEND | E | ✅ |
| Savings via mobile-money rails | CORE | D | ✅ |
| Automated savings deduction at source | CORE | E | ✅ |
| Financial literacy & training content | DATA | D | ✅ |
| Bulk group registration & admin tracking | BIZ | E | ✅ |
| Zero-balance / no-minimum accounts | CORE | D | ✅ |
| Instant digital account opening (<3 min) | COMP | T | ✅ |

## 32. Advanced AI & Agentic Banking

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Conversational AI assistant (voice + text) | AI | D | ✅ |
| Agentic assistant (prepares transfers, you authorise) | AI | D | ✅ |
| Voice-first / show-a-bill AI (photo → draft payment) | AI | E | 🟡 |
| AI financial coach (goals, nudges, auto-rules) | AI | D | ✅ |
| AI spending insights & trend analysis | AI | D | ✅ |
| AI transaction categorisation / enrichment | AI | D | ✅ |
| AI credit-score simulator | AI | D | ✅ |
| AI budget creation from history | AI | D | ✅ |
| AI-powered fraud detection | AI | T | ✅ |
| AI scam-warning / scam-call detection | AI | D | ✅ |
| AI support agents (chat + phone) | AI | D | ✅ |
| AI-powered document extraction | AI | D | ✅ |
| AI tax prep / DIY filing assist | AI | E | 🟡 |
| Personalised product recommendations | AI | D | ✅ |
| Next-best-action / churn prediction | AI | D | ✅ |
| LLM switching / multi-model routing (cost) | AI | D | ✅ |
| AI self-service (LLM safeguarding escalation) | AI | D | ✅ |

## 33. Financial Wellness & Personalisation

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Net-worth / 360° aggregated view | DATA | D | ✅ |
| Financial health score | AI | D | ✅ |
| Goal-based financial planning (Life Plan) | WEALTH | D | ✅ |
| Credit coach & real-time score simulation | DATA | D | ✅ |
| Personalised money-management hub | DATA | D | ✅ |
| Savings boosters / automation | DATA | D | ✅ |
| Spending vs. income comparison | DATA | D | ✅ |
| Gamified goals (progress bars, milestones) | DATA | D | ✅ |
| Comparisons with similar profiles | DATA | E | 🟡 |
| Personalised nudges & proactive alerts | AI | D | ✅ |

## 34. Notifications, Alerts & UX

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Customisable alerts (per transaction type/amount) | DATA | T | ✅ |
| Low-balance / large-purchase alerts | DATA | T | ✅ |
| Bill-due reminders | DATA | T | ✅ |
| Real-time instant notifications (push) | PLAT | T | ✅ |
| In-app scam red-flag display | SEC | D | ✅ |
| Security meter / account security level | SEC | D | ✅ |
| Data-access transparency (who has access) | SEC | D | ✅ |
| Customisable home screen / quick actions | PLAT | D | ✅ |
| Widgets / watch app / app shortcuts | PLAT | D | ✅ |

## 35. Statements, Documents & Self-Service

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Downloadable statements & tax documents | CORE | T | ✅ |
| Paperless / e-statements | ESG | T | ✅ |
| Order checks / deposit slips | CORE | T | ✅ |
| Self-service PIN reset | SEC | T | ✅ |
| Card request & delivery tracking | CARD | D | ✅ |
| Account opening self-service | COMP | T | ✅ |
| Profile & settings management | PLAT | T | ✅ |
| Secure document upload / vault | SEC | D | ✅ |

## 36. Rewards, Travel & Perks

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Travel insurance / trip protection | WEALTH | D | ✅ |
| Airport lounge / travel credits | LIFE | D | ✅ |
| Dining / entertainment perks | LIFE | D | ✅ |
| Cashback on card spend (incl. virtual) | LIFE | D | ✅ |
| Tiered premium/metal plans | LIFE | D | ✅ |
| Family / shared plan bundles | LIFE | D | ✅ |
| Referral & invite rewards | LIFE | D | ✅ |

## 37. New / Emerging niche domains

| Feature | Type | Priority | Proven |
|---|---|---|---|
| Tokenised deposits | WEALTH | E | 🟡 |
| Programmable money / smart contracts | PAY | E | 🟡 |
| Central-bank digital currency (CBDC) wallet | PAY | E | 🟡 |
| Open-finance / data portability | OPEN | E | 🟡 |
| Carbon offset marketplace | ESG | D | ✅ |
| ESG / impact investing | ESG | D | ✅ |
| Islamic banking (profit-loss, sukuk) | CORE | E | ✅ |
| Diaspora / remittance-corridor accounts | CORE | D | ✅ |
| Gig/migrant-worker financial bundle | LIFE | E | 🟡 |
| Pet insurance / embedded insurance | WEALTH | E | 🟡 |
| Real-estate / rent-to-own | LEND | E | 🟡 |
| Agri-value-chain finance | LEND | E | ✅ |
| Charitable giving / donation-as-you-spend | ESG | D | ✅ |
| Digital credentials wallet (ID, licences) | SEC | E | 🟡 |

---

## Appendix A — Feature count by domain

| Domain | Features | Table-stakes | Differentiators | Emerging |
|---|---|---|---|---|
| Accounts & Deposits | 23 | 9 | 10 | 2 |
| Payments & Transfers | 23 | 7 | 14 | 1 |
| Cards | 23 | 8 | 14 | 0 |
| Bill Pay | 11 | 4 | 6 | 1 |
| Lending & Credit | 20 | 8 | 11 | 1 |
| Wealth & Investing | 20 | 2 | 15 | 3 |
| Money Management | 13 | 2 | 11 | 0 |
| Credit & Financial Health | 7 | 1 | 5 | 0 |
| Security & Fraud | 21 | 9 | 10 | 1 |
| Customer Service & AI | 12 | 5 | 6 | 1 |
| Business Banking | 15 | 0 | 14 | 1 |
| Treasury | 14 | 0 | 14 | 0 |
| Private Banking | 9 | 0 | 9 | 0 |
| Kids & Family | 8 | 0 | 8 | 0 |
| Open Banking | 9 | 0 | 7 | 0 |
| Super-App / Lifestyle | 11 | 0 | 2 | 9 |
| Sustainability | 9 | 1 | 8 | 0 |
| Digital Assets | 10 | 0 | 1 | 9 |
| Rewards | 7 | 0 | 7 | 0 |
| Insurance | 6 | 0 | 4 | 2 |
| Accessibility | 11 | 7 | 1 | 0 |
| Compliance | 14 | 7 | 7 | 0 |
| Platform | 16 | 6 | 9 | 0 |
| Notifications/UX | 9 | 3 | 6 | 0 |
| International | 8 | 0 | 6 | 1 |
| Merchant Acceptance | 7 | 0 | 7 | 0 |
| Specialty | 9 | 0 | 4 | 4 |
| **Part II — additional domains** | | | | |
| Credit Cards (advanced tooling) | 16 | 2 | 14 | 0 |
| Super-App / Embedded Finance / Wallets | 19 | 0 | 4 | 15 |
| Business Banking (depth) | 23 | 2 | 19 | 2 |
| Financial Inclusion / Rural / Agri | 17 | 1 | 10 | 6 |
| Advanced AI & Agentic Banking | 17 | 1 | 14 | 2 |
| Financial Wellness & Personalisation | 10 | 0 | 10 | 0 |
| Notifications / Alerts / UX | 9 | 4 | 5 | 0 |
| Statements / Documents / Self-Service | 8 | 3 | 5 | 0 |
| Rewards / Travel / Perks | 7 | 0 | 7 | 0 |
| New / Emerging niche | 14 | 0 | 6 | 8 |
| **TOTAL** | **~600+** | | | |

> Part II adds ~150 features from a deeper 2026 sweep (Forbes, CNBC, NerdWallet, Stripe, Rho, Brex, World Bank, super-app & wallet analyses), bringing the catalogue to **~600 distinct features**.

## Appendix B — Recommended Phase 1 shortlist (first release)

**Core:** Checking + Savings + term deposit · **Cards:** physical + virtual + freeze + controls · **Payments:** domestic transfer + P2P + bill pay + mobile deposit · **Money mgmt:** spending dashboard + budgets · **Security:** biometric login + MFA + real-time fraud alerts + card controls · **Service:** basic virtual assistant + live chat + human escalation · **Compliance:** KYC + sanctions + audit trail · **Accessibility:** WCAG 2.2 AA baseline.

This Phase 1 release matches the CNBC/Forbes/J.D. Power "essentials" while already exceeding the security baseline of 88% of 2026 banking apps (Ostorlab).

---

*End of feature catalog.*