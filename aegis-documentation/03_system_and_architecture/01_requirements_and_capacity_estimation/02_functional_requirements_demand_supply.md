# Functional Requirements — Demand & Supply

> These are the high-level FRs. The **full ~485-feature catalog** (37 sections, incl. AI use cases) is in [`14_feature_catalog.md`](../../01-business-and-strategy/14_feature_catalog.md); its **architecture traceability** is in [`06_feature_traceability.md`](./06_feature_traceability.md); **all use cases** (business + system + AI) are in [`07_use_case_catalog_all.md`](./07_use_case_catalog_all.md). Every feature domain is covered by a demand driver and a supplying component below.

## Demand (functional requirements)

| ID | Requirement | Volume (illustrative) |
|---|---|---|
| D-01 | Accounts (open, manage) | 5M accounts |
| D-02 | Payments (domestic, P2P, cross-border) | 5M/month |
| D-03 | Card authorization | 20M/month |
| D-04 | Fraud scoring | Every card txn |
| D-05 | KYC/AML checks | Onboarding + ongoing |
| D-06 | Loan applications | 100k/month |
| D-07 | Assistant queries | 2M/month |
| D-08 | Statements/documents | On demand |

## Supply (components that satisfy demand)

| Requirement | Supplying component | Open-source stack | Commercial alternative |
|---|---|---|---|
| Accounts/payments/cards | Core banking engine + Java microservices (Spring Boot / Quarkus) | **Apache Fineract** (Apache 2.0, ledger + REST API) or **Mifos X** (MPL 2.0, Fineract + web/mobile), **TigerBeetle** (high-throughput double-entry ledger) | Temenos, Mambu, Thought Machine |
| Card issuing & payment orchestration | Payments layer | **Hyperswitch** (Apache 2.0, Rust; 100+ processors, card vault), **Apache Fineract** for ledger | Marqeta, Stripe Issuing |
| Fraud scoring | AI risk plane | GNN/GBM + vLLM (open models) | Feedzai, Featurespace |
| KYC/AML | Compliance plane (`aegis/`) | Docling, LlamaIndex, NeMo, **Apache Fineract** audit journal | Actimize, NICE |
| Lending | Lending service | XGBoost + LangGraph; **Apache Fineract** loan/savings product engine | Temenos, Mambu |
| Assistant | AI platform | LangGraph + LlamaIndex | — |
| Data | Data platform | Kafka/Redpanda, Iceberg, ClickHouse | Snowflake, Databricks |

## Feature-domain demand & supply (all 37 catalog sections)

| § | Feature domain | Demand (illustrative) | Supplying component | AI features |
|---|---|---|---|---|
| 1 | Accounts & Deposits | 5M accounts | Core (ledger / product engine / CIF) | — |
| 2 | Payments & Transfers | 5M/month | Payments service (Java) + Hyperswitch | Agentic payment initiation, instruction repair, cash-flow forecasting |
| 3 | Cards | 20M/month auth | Cards service + card issuing (Hyperswitch/Marqeta) | — |
| 4 | Bill Pay & Payment Mgmt | On demand | Payments service | — |
| 5 | Lending & Credit | 100k/month apps | Lending service + XGBoost/LangGraph + Fineract | Origination agent, underwriting copilot, income verification, alt-data scoring, collections |
| 6 | Savings, Wealth & Investing | On demand | Core + Wealth (AI/Data) + robo-advisor | RM copilot, robo-advisory, financial planning, tax-loss harvesting, market-scout |
| 7 | Money Management & Insights | On demand | Data platform + AI insights | — |
| 8 | Credit & Financial Health | On demand | Data + AI (credit score) | — |
| 9 | Security & Fraud | Every txn | `aegis/` Fraud + Identity | Real-time fraud scoring, alert triage, scam interception |
| 10 | Customer Service & AI | 2M/month queries | AI platform (agents) + BFF | Contact-centre agent, account/card servicing, agent assist, voice banking, complaints, digital-human guide, coaching |
| 11 | Business & Commercial Banking | On demand | Business services | — |
| 12 | Treasury & Cash Management | On demand | Treasury services | Liquidity monitoring, reconciliation, forecasting |
| 13 | Private Banking & Wealth (HNW) | On demand | Wealth (HNW) | RM copilot |
| 14 | Kids, Teens & Family | On demand | Core + Accounts | — |
| 15 | Open Banking & Embedded Finance | On demand | Open Banking service + APIs | — |
| 16 | Super-App & Lifestyle | On demand | Lifestyle + Channels | — |
| 17 | Sustainability / Green Banking | On demand | ESG service + Data | — |
| 18 | Digital Assets & Stablecoins | On demand | Digital-asset service + Core | — |
| 19 | Rewards & Loyalty | On demand | Rewards service | — |
| 20 | Insurance & Protection | On demand | Insurance service | — |
| 21 | Accessibility & Inclusion | Always (WCAG) | Channels (WCAG 2.2 AA) | — |
| 22 | Compliance, Risk & Regulatory | KYC onboarding + ongoing | `aegis/` Compliance + Audit | AML monitoring, investigation agent, SAR/STR drafting, KYC/KYB, sanctions/PEP, regulatory reporting, change monitoring, disputes, agentic BI |
| 23 | Platform, Data & AI Infrastructure | Event-driven | Platform + Data + AI plane | Developer augmentation, cyber triage, knowledge search, audit copilot, document/vision extraction |
| 24 | Notifications, Personalisation & UX | On demand | Notification service + AI personalisation | — |
| 25 | International / Cross-border | On demand | Payments (FX) + International service | — |
| 26 | Payments Acceptance (merchant) | On demand | Merchant-acceptance service | — |
| 27 | Specialty / Niche products | On demand | Specialty service | — |
| 28 | Credit Cards — advanced tooling | On demand | Cards service | — |
| 29 | Super-App, Embedded Finance & Wallets | On demand | Lifestyle + Wallet | — |
| 30 | Business Banking — depth | On demand | Business services | — |
| 31 | Financial Inclusion, Rural & Agri | On demand | Agri/inclusion service | — |
| 32 | Advanced AI & Agentic Banking | On demand | AI platform (LangGraph) | Agentic assistant, multi-agent orchestration, MCP tools |
| 33 | Financial Wellness & Personalisation | On demand | Data + AI | — |
| 34 | Notifications, Alerts & UX | On demand | Notification service | — |
| 35 | Statements, Documents & Self-Service | On demand | Document service + Data | — |
| 36 | Rewards, Travel & Perks | On demand | Rewards service | — |
| 37 | New / Emerging niche domains | On demand | Emerging service | Research automation, pitchbook, trading-ops, enterprise risk (adjacent) |

> **AI features:** ~46 use cases across 7 domains, embedded above as features (detail: [`14_feature_catalog.md`](../../01-business-and-strategy/14_feature_catalog.md) §AI Use Cases).

## Demand-supply gap analysis

- **Core:** buy the ledger/product engine (Thought Machine/Mambu) per D-001 and integrate via events; Apache Fineract/Mifos X is the open-source fallback only if we build. If we build, budget for operational ownership (hardening, upgrades, backup/DR, no vendor SLA).
- **Fraud/AML:** build the AI plane; scale horizontally.
- **Assistant:** stateless agents + shared vector/graph stores; scale via K8s.
- **Data:** Kafka/Redpanda + Iceberg + ClickHouse; scale storage/compute independently.
- **Platform/infra:** K8s + Terraform + ArgoCD; multi-region for Tier 0.
- **Long-tail domains (11–37):** mostly config-driven products on the bought core + shared AI/data plane, not bespoke codebases.