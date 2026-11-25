# Extended and Custom Requirements

> Baseline NFRs are in [`03_non_functional_requirements_matrix.md`](./03_non_functional_requirements_matrix.md); this document adds **domain-specific** and **cross-cutting** obligations for all 37 feature-catalog sections.

## Domain-specific requirements (all 37 sections)

| § | Area | Requirement |
|---|---|---|
| 1 | Accounts & Deposits | Real-time balance; multi-currency; joint accounts; statement/regulatory reporting |
| 2 | Payments & Transfers | Confirmation of payee; ISO 20022; instant rails; idempotency; limits |
| 3 | Cards | Tokenisation; PCI-DSS; freeze/granular controls; chargeback handling |
| 4 | Bill Pay & Payment Mgmt | Payee verification; autopay; scheduled; request-to-pay |
| 5 | Lending & Credit | Adverse-action reasons; fair-lending bias testing; affordability checks |
| 6 | Savings, Wealth & Investing | Suitability; best execution; tax reporting |
| 7 | Money Management & Insights | Categorisation accuracy; budget rules; cash-flow forecast |
| 8 | Credit & Financial Health | Data accuracy; dispute handling; score explainability |
| 9 | Security & Fraud | Real-time scoring; scam-payment interception; step-up auth |
| 10 | Customer Service & AI | AI disclosure; human escalation; no dark patterns |
| 11 | Business & Commercial Banking | KYB; invoicing; expenses; payroll |
| 12 | Treasury & Cash Management | Cash positioning; FX; liquidity reporting |
| 13 | Private Banking & Wealth (HNW) | Suitability; HNW onboarding; discretionary mandates |
| 14 | Kids, Teens & Family | Parental controls; consent; age gating |
| 15 | Open Banking & Embedded Finance | Consent lifecycle (grant/revoke/expiry); PSD2 SCA; FAPI |
| 16 | Super-App & Lifestyle | Offers consent; data minimisation |
| 17 | Sustainability / Green Banking | Green taxonomy; ESG reporting |
| 18 | Digital Assets & Stablecoins | Custody; AML; travel rule |
| 19 | Rewards & Loyalty | T&Cs; expiry; points liability |
| 20 | Insurance & Protection | Underwriting; claims; disclosure |
| 21 | Accessibility & Inclusion | WCAG 2.2 AA; EAA; multi-sensory; plain language |
| 22 | Compliance, Risk & Regulatory | SAR filing within deadline; 5-year retention; explainable triage |
| 23 | Platform, Data & AI Infrastructure | Data residency; lineage; reproducibility |
| 24 | Notifications, Personalisation & UX | Consent; quiet hours; deliverability |
| 25 | International / Cross-border | FX disclosure; sanctions; remittance corridors |
| 26 | Payments Acceptance (merchant) | PCI; settlement; chargebacks |
| 27 | Specialty / Niche products | Niche-specific compliance |
| 28 | Credit Cards — advanced tooling | PCI; rewards liability; instalments |
| 29 | Super-App, Embedded Finance & Wallets | Wallet tokenisation; embedded-finance compliance |
| 30 | Business Banking — depth | Payroll tax; MTD; multi-entity |
| 31 | Financial Inclusion, Rural & Agri | Seasonal lending; alternative-data underwriting |
| 32 | Advanced AI & Agentic Banking | HITL; eval gates; fail-closed guardrails |
| 33 | Financial Wellness & Personalisation | No predatory nudges; explainability |
| 34 | Notifications, Alerts & UX | Alert-fatigue controls; regulatory alerts |
| 35 | Statements, Documents & Self-Service | Retention; admissibility; self-service |
| 36 | Rewards, Travel & Perks | Partner data; T&Cs |
| 37 | New / Emerging niche domains | Regulatory horizon scanning |

## Cross-cutting extended requirements

| Area | Requirement |
|---|---|
| **Privacy** | GDPR/CCPA; data minimisation; consent; DSAR fulfilment |
| **Data residency** | In-VPC / region-locked processing and storage where required |
| **Localisation** | i18n/l10n; locale currency/date; plain language |
| **Integration** | BaaS/core APIs; ISO 8583/20022; webhook/event contracts |
| **Business continuity** | Tiered DR (RTO/RPO); runbooks; failover drills |
| **Fairness & bias** | Bias testing for credit/AML/fraud models |
| **Explainability** | Reason codes for adverse decisions |
| **Consent management** | Central consent ledger with grant/revoke/expiry |
| **Third-party risk** | Vendor due diligence; exit strategy; concentration limits |

## Model-risk requirements (EU AI Act / SR 11-7)

- Model inventory, validation, monitoring, documentation.
- Human oversight for high-risk AI (credit, AML).
- Bias & fairness testing.
- Explainability for adverse decisions.

## Auditability

- Immutable audit log of every AI decision: inputs, model version, prompt version, policy, output, human approver.
- Reconstructable decision chains for examiners.
