# Component Hierarchy and Design System

## Component hierarchy

```
AppShell
├── AppBar / BottomTabBar (Home · Payments · Cards · Save · Profile)
├── NavigationHost (React Navigation native / Next.js App Router web)
│   ├── Auth & Onboarding
│   │   ├── LoginScreen (passkey / OTP)
│   │   ├── OnboardingScreen
│   │   ├── KycScreen (ID + liveness, EDD)
│   │   └── StepUpAuthScreen
│   ├── HomeScreen (balance, quick actions, insights teaser)
│   ├── AccountsScreen
│   │   ├── AccountsListScreen
│   │   ├── AccountDetailScreen (transactions, statements)
│   │   └── PotsGoalsScreen
│   ├── PaymentsScreen
│   │   ├── SendMoneyScreen (domestic, P2P, wires, QR, instant)
│   │   ├── RequestMoneyScreen
│   │   ├── PayBillScreen (bill pay, autopay, scheduled)
│   │   ├── StandingOrdersScreen
│   │   ├── SplitBillScreen
│   │   ├── MobileDepositScreen
│   │   └── PaymentStatusScreen
│   ├── CardsScreen
│   │   ├── CardsListScreen
│   │   ├── CardDetailScreen (freeze, controls, limits, MCC/channel)
│   │   ├── CardPinScreen
│   │   ├── AddToWalletScreen
│   │   └── DisputeScreen
│   ├── SaveInvestScreen
│   │   ├── SavingsGoalsScreen
│   │   ├── RoboAdvisorScreen
│   │   ├── SelfDirectedScreen
│   │   ├── RetirementScreen
│   │   ├── CryptoScreen (digital assets, stablecoins)
│   │   └── PortfolioScreen (analytics, tax-loss harvesting)
│   ├── BorrowScreen
│   │   ├── LoansScreen (personal, auto, mortgage)
│   │   ├── BnplScreen
│   │   ├── OverdraftEwaScreen
│   │   ├── CreditLineScreen
│   │   ├── OriginationScreen
│   │   └── CreditHealthScreen (score, report, build)
│   ├── BusinessScreen
│   │   ├── BusinessAccountScreen
│   │   ├── InvoicesScreen
│   │   ├── ExpensesScreen
│   │   ├── PayrollScreen (MTD)
│   │   ├── TreasuryScreen
│   │   └── MerchantAcceptanceScreen
│   ├── InsightsScreen
│   │   ├── SpendingScreen
│   │   ├── BudgetsScreen
│   │   ├── CashFlowScreen
│   │   ├── NetWorthScreen
│   │   └── NudgesScreen
│   ├── WealthScreen (private banking / HNW, wealth projection)
│   ├── KidsFamilyScreen
│   ├── InternationalScreen (multi-currency, FX, remittance, travel)
│   ├── OpenBankingScreen (aggregation, connected accounts)
│   ├── LifestyleScreen (super-app, lifestyle, offers)
│   ├── SustainabilityScreen (green banking, ESG)
│   ├── RewardsScreen (rewards, travel, perks)
│   ├── InsuranceScreen (protection)
│   ├── SpecialtyScreen (niche products)
│   ├── AgriInclusionScreen (rural, agri, financial inclusion)
│   ├── EmergingScreen (new / emerging domains)
│   ├── AccessibilityScreen (adjustments)
│   ├── ActivityScreen (transactions, statements, documents, self-service)
│   ├── AssistantScreen (agentic chat/voice, escalation)
│   ├── SupportScreen (help center, contact, complaints)
│   ├── SearchScreen (global assistant-powered search)
│   └── ProfileScreen (settings, security, privacy, notifications)
└── GlobalOverlays (modals, toasts, alerts)
```

> The **agent/ops console** (web) reuses the same design system and component set; it is a separate app shell over the same tokens/components, not a separate design language:

```
OpsConsole (web)
├── CaseQueueScreen
├── CaseDetailScreen (evidence, narrative, QA, HITL)
├── KycReviewScreen
├── FraudReviewScreen
├── SarScreen
├── AuditScreen
└── AgentOversightScreen
```

## Page coverage (feature-catalog sections 1–37)

Every feature-catalog section maps to at least one page (source: `../../01-business-and-strategy/14_feature_catalog.md`):

| § | Feature domain | Page(s) |
|---|---|---|
| 1 | Accounts & Deposits | AccountsScreen |
| 2 | Payments & Transfers | PaymentsScreen |
| 3 | Cards | CardsScreen |
| 4 | Bill Pay & Payment Mgmt | PayBillScreen |
| 5 | Lending & Credit | BorrowScreen |
| 6 | Savings, Wealth & Investing | SaveInvestScreen |
| 7 | Money Management & Insights | InsightsScreen |
| 8 | Credit & Financial Health | CreditHealthScreen |
| 9 | Security & Fraud | StepUpAuthScreen, Profile › Security (+ Ops FraudReview) |
| 10 | Customer Service & AI | AssistantScreen, SupportScreen |
| 11 | Business & Commercial Banking | BusinessScreen |
| 12 | Treasury & Cash Management | TreasuryScreen |
| 13 | Private Banking & Wealth (HNW) | WealthScreen |
| 14 | Kids, Teens & Family | KidsFamilyScreen |
| 15 | Open Banking & Embedded Finance | OpenBankingScreen |
| 16 | Super-App & Lifestyle | LifestyleScreen |
| 17 | Sustainability / Green Banking | SustainabilityScreen |
| 18 | Digital Assets & Stablecoins | CryptoScreen |
| 19 | Rewards & Loyalty | RewardsScreen |
| 20 | Insurance & Protection | InsuranceScreen |
| 21 | Accessibility & Inclusion | AccessibilityScreen |
| 22 | Compliance, Risk & Regulatory | OpsConsole (Case/Kyc/Sar/Audit) |
| 23 | Platform, Data & AI Infrastructure | (no customer page — platform/infra) |
| 24 | Notifications, Personalisation & UX | Profile › Notifications, NudgesScreen |
| 25 | International / Cross-border | InternationalScreen |
| 26 | Payments Acceptance (merchant) | MerchantAcceptanceScreen |
| 27 | Specialty / Niche products | SpecialtyScreen |
| 28 | Credit Cards — advanced tooling | CardDetailScreen |
| 29 | Super-App, Embedded Finance & Wallets | LifestyleScreen, AddToWalletScreen |
| 30 | Business Banking — depth | BusinessScreen |
| 31 | Financial Inclusion, Rural & Agri | AgriInclusionScreen |
| 32 | Advanced AI & Agentic Banking | AssistantScreen |
| 33 | Financial Wellness & Personalisation | InsightsScreen (wellness) |
| 34 | Notifications, Alerts & UX | NudgesScreen |
| 35 | Statements, Documents & Self-Service | ActivityScreen |
| 36 | Rewards, Travel & Perks | RewardsScreen |
| 37 | New / Emerging niche domains | EmergingScreen |

## Design system

- **Foundations:** colour (WCAG AA contrast ≥4.5:1), typography (adjustable), spacing, motion (respects reduced-motion).
- **Components:** buttons, inputs, cards, lists, charts, sheets, alerts — all accessible (ARIA, keyboard, screen-reader).
- **Patterns:** forms, onboarding, empty/loading/error states, confirmation flows.
- **Theming:** light/dark, high-contrast; brand tokens.

## Accessibility (WCAG 2.2 AA)

- Semantic HTML/ARIA on web; accessibility props on native.
- Focus order, keyboard navigation, visible focus.
- Text resize to 200% without loss (1.4.4); reflow at 400% zoom / 320px width (1.4.10).
- Captions/audio for multi-sensory.
- Automated `axe-core` checks in CI + manual screen-reader testing.

## Consistency

One design system across mobile, web, and the ops console: **shared design tokens and specs**, with components implemented per platform (shared code via `react-native-web` where practical, since native and web use different renderers).