# In Scope and Out of Scope

Aegis Bank is a **full-featured retail digital bank**. Every product surface in the source-of-truth feature catalog ([`../14_feature_catalog.md`](../14_feature_catalog.md), ~485 features) is **in scope**, sequenced across Phase 1 (production release) → Phase 2 → Phase 3. Nothing in the catalog is deferred out of scope; "later" items are simply later phases.

## In scope (all phases)

| Area | In scope |
|---|---|
| **Accounts & deposits** | Checking, savings (instant/notice/term), joint, multi-currency, pots/spaces, salary sorting, round-ups, auto-save, sweep, escrow, dormant/escheatment |
| **Payments & transfers** | Domestic, P2P, wires, standing orders, scheduled, request-to-pay, QR (domestic + cross-border), instant rails (FedNow/RTP/SEPA Instant), confirmation of payee, per-payment signing, batch, maker-checker, recall, FX, tap-to-pay, wearables |
| **Cards** | Physical debit/credit, virtual, single-use, freeze/unfreeze, MCC/channel controls, geo-blocking, real-time alerts, PIN, replacement, wallet provisioning, card-linked offers, credit-building, metal tiers, multi-currency, eco cards |
| **Bill pay** | Biller directory, autopay, reminders, split bill, subscription manager, mobile top-up, mobile check deposit, standing instructions, bill negotiation |
| **Lending & credit** | Personal, auto, mortgage (fixed/variable/offset/tracker), student, overdraft, BNPL, EWA, credit line, secured, origination workflow, ML decisioning, repayment/autopay, delinquency/collections, refinancing, HELOC, POS finance |
| **Savings & wealth** | Goals, interest accounts, robo-advisor, self-directed investing, fractional shares, retirement, crypto, portfolio analytics, projection, auto-rebalance, tax-loss harvesting, round-up investing, ESG portfolios, planning, human advisor, IPO, options, commodities, trust/estate, model portfolios |
| **Money management & insights** | Spending dashboard, budgets, cash-flow forecast, net-worth 360°, personalised nudges, bill/subscription detection, education, gamified challenges, peer comparison, recurring detection, receipt capture, financial health score |
| **Credit & financial health** | Score monitoring, report/history, credit-building, identity/dark-web monitoring, score simulator, alerts, free report |
| **Security & fraud** | Biometrics, passkeys/FIDO2, risk-adaptive MFA, OTP, behavioural biometrics, real-time AI fraud, instant alerts + freeze, scam/scam-call detection, malware/overlay detection, device binding, per-payment signing, geo-blocking, cooling-off, consent transparency, privacy export/delete, new-device alerts, session/remote logout, card controls, disputes/chargebacks, agentic case mgmt, deepfake/injection detection, Positive Pay/ACH filters |
| **Customer service & AI** | Virtual assistant, agentic assistant, live chat + human escalation, co-browsing, secure messaging, appointment booking, complaints/disputes, call-back, branch locator, AI-searchable FAQ, voice assistant, multilingual, accessibility support |
| **Business & commercial** | Business accounts, multi-user/permissions, invoicing, expenses/receipts, accounting integrations, payroll, tax (MTD), cash-flow forecasting, bulk payments, corporate/team cards, merchant services, business loans, invoice factoring, multi-entity/currency, contractor payouts, AP/AR automation, spend controls, approval rules, bookkeeping, tax forms, API/ERP sync, same-day ACH/wire/SWIFT, revenue-based financing, insured cash sweep |
| **Treasury & cash management** | Real-time cash visibility, cash pooling, ZBA/TBA, sweeps, ACH origination, lockbox, escrow, Positive Pay, 13-week forecasting, FX hedging, ERP liquidity, SWIFT/ISO 20022, earnings credit, working-capital optimisation |
| **Private banking (HNW)** | Relationship manager, tailored lending, concentrated-position planning, succession/estate, philanthropy, trust/fiduciary, family office, liquidity-event planning, exclusive rates |
| **Kids, teens & family** | Teen accounts, kids cards/allowances, parental controls, chores→allowance, kids savings goals, teen literacy, family pots, family plans |
| **Open banking & ecosystem** | Aggregation, PISP, consent mgmt, marketplace, BaaS, embedded/white-label, developer portal/sandbox, webhooks, open-banking data sharing, QR interoperability |
| **Super-app & lifestyle** | Top-up/bills, travel, ride-hailing, events, eSIM, shopping, food delivery, gaming, remittances, gig payouts, mini-apps, wallets, softPOS, agent banking, offline/USSD/SMS, chat/voice banking, programmable payments, loyalty currency |
| **Sustainability / green** | Carbon tracker, offsets, ESG funds, green financing, eco cards, paperless, donations, sustainability insights, green savings, carbon marketplace, impact investing |
| **Digital assets & stablecoins** | Crypto trading, custodial wallet, stablecoin wallets/payouts/remittance, tokenised deposits, programmable money, cross-chain, staking/yield, custody/KYT, CBDC wallet, digital-credentials wallet |
| **Rewards & loyalty** | Cashback, card-linked offers, referrals, tiered plans, points, personalised rewards, partner discounts, redemption portal, rewards expiry |
| **Insurance & protection** | Travel, device/purchase, life/income, home/renters, pet, embedded insurance |
| **Accessibility & inclusion** | WCAG 2.2 AA, screen-reader, keyboard/focus, contrast, 400% zoom, plain language, multi-sensory, tagged PDFs, support adjustments, voice/switch, reduced-motion/dyslexia fonts |
| **Compliance & regulatory** | KYC/KYB/CDD, sanctions/PEP, AML monitoring, agentic AML/SAR, fraud case mgmt, regulatory reporting, immutable audit, data residency/GDPR/CCPA, consent, risk segmentation, periodic review, adverse media, no-code rule builder, HITL escalation |
| **Platform, data & AI infra** | Real-time ledger, product engine, pricing/fees, GL/sub-ledgers, CIF, API gateway/portal, event streaming, feature store, data lake/warehouse, vector+graph store, LLM gateway/routing, guardrails/policy, OTel/audit, workflow orchestration, multi-tenancy/RBAC, consent/privacy service |
| **Notifications & UX** | Push/SMS/email, real-time txn alerts, customisable preferences, personalised feed, dark mode, messaging centre, onboarding, quick actions, widgets/watch/shortcuts |
| **International / cross-border** | Multi-currency balances, low-FX, global QR, travel card, remittance corridors, stablecoin cross-border, localised accounts (IBAN), multi-language statements |
| **Payments acceptance (merchant)** | Card acquiring/POS, e-commerce checkout/links, QR merchant, recurring billing, payouts, chargeback mgmt, merchant analytics |
| **Specialty / niche** | Islamic/Shariah, microfinance/inclusion, gig/migrant bundle, payroll-as-a-service, real-estate/rent-to-own, agri/sector lending, diaspora, charity/non-profit, trust/estate, rural/agri-finance, tiered KYC, alternative credit scoring, parametric crop insurance, seasonal loans, value-chain finance, group lending/VSLA/SACCO |

## Out of scope

| Area | Why out |
|---|---|
| **Physical branch network** | Digital-first; branches handled via partners/locators, not owned |
| **Proprietary payment scheme / card network** | Rely on existing rails (Visa/Mastercard/ACH/FedNow/SEPA); not issuing own scheme |
| **Own core-banking ledger build** | Buy/configure a ledger product engine (build-vs-buy decision, see `10_cost_roi_and_alternatives/build_vs_buy_options.md`) |

## Sequencing note

All in-scope areas ship across Phase 1 → Phase 2 → Phase 3 (see `04_current_vs_future_state/to_be_target_capabilities.md` for the capability → phase map). "Out of scope" above means *never built by Aegis*, not *deferred*.

## Assumptions & dependencies

See `05_scope_and_boundaries/assumptions_and_dependencies.md`.