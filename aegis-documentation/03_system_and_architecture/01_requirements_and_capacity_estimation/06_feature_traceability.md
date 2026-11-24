# Feature Traceability — Architecture Coverage

**Version:** 1.0 · **Date:** 24 Nov 2026 · **Companion to:** [../../01-business-and-strategy/14_feature_catalog.md](../../01-business-and-strategy/14_feature_catalog.md)

This document closes the gap between the **full feature catalog** (~485 features, 37 sections) and the **system architecture**. The catalog itself lives in `01-business-and-strategy/14_feature_catalog.md`; here we trace every feature **domain** to the architectural components, services, and data stores that implement it.

> The architecture folder previously covered only 8 high-level FRs (`02_functional_requirements_demand_supply.md`). This traceability extends that to the full catalog.

## Traceability matrix (37 domains → architecture)

| § | Feature domain | Implementing component(s) | Data / store | API surface |
|---|---|---|---|---|
| 1 | Accounts & Deposits | Core banking (bought) + Accounts service (Java) | Postgres (ledger) | REST `/accounts` |
| 2 | Payments & Transfers | Payments service (Java) + Flink | Kafka, Postgres | REST `/payments` |
| 3 | Cards | Cards service (Java) + tokenisation vault | Postgres, Redis | REST `/cards` |
| 4 | Bill Pay | Payments + Bill Pay service | Postgres | REST `/bills` |
| 5 | Lending & Credit | Lending service (Python decisioning + Java servicing) | Postgres, Feast | REST `/lending` |
| 6 | Savings, Wealth & Investing | Wealth service (Python) | Postgres, Iceberg | REST `/wealth` |
| 7 | Money Management & Insights | Insights service (Python) + Data platform | ClickHouse, Iceberg | GraphQL |
| 8 | Credit & Financial Health | Credit service (Python) + bureau APIs | Postgres | REST `/credit` |
| 9 | Security & Fraud | **Fraud (aegis/risk)** + Identity (Keycloak) | Qdrant, Neo4j, Redis | gRPC + REST |
| 10 | Customer Service & AI | **AI Platform (LangGraph/LlamaIndex)** | Qdrant, Neo4j, vLLM | REST/stream `/assistant` |
| 11 | Business & Commercial Banking | Business service (TS UI + Python automation) | Postgres | REST `/business` |
| 12 | Treasury & Cash Management | Treasury service (Java + Python forecasting) | Postgres, ClickHouse | REST `/treasury` |
| 13 | Private Banking & Wealth | Wealth + RM copilot (Python) | Postgres, Qdrant | REST `/wealth/private` |
| 14 | Kids, Teens & Family | Accounts + parental controls (Java) | Postgres | REST `/accounts` |
| 15 | Open Banking & Ecosystem | Open Banking service (Java) + Consent service | Postgres | PSD2 APIs |
| 16 | Super-App & Lifestyle | Lifestyle services (TS + Python) | Postgres | REST/GraphQL |
| 17 | Sustainability / Green | ESG service (Python) | Postgres | REST `/esg` |
| 18 | Digital Assets & Stablecoins | Digital Assets service (Python + Java) | Postgres, custody/KYT | REST `/assets` |
| 19 | Rewards & Loyalty | Rewards service (TS + Java) | Postgres | REST `/rewards` |
| 20 | Insurance & Protection | Insurance service (Python) | Postgres | REST `/insurance` |
| 21 | Accessibility & Inclusion | Channels (RN/Next.js) + axe-core CI | — | UI (WCAG 2.2 AA) |
| 22 | Compliance, Risk & Regulatory | **AI Risk (aegis/compliance)** | Postgres, Neo4j, Qdrant | REST `/compliance` |
| 23 | Platform, Data & AI Infra | Platform (K8s) + Data (Iceberg/ClickHouse/Feast) | S3, ClickHouse, Feast | Internal |
| 24 | Notifications & UX | Notifications service (Java/Python) + Channels | Redis, FCM/APNs | Push/SMS/email |
| 25 | International / Cross-border | Payments + FX service (Java) | Postgres | REST `/fx` |
| 26 | Payments Acceptance (merchant) | Acquiring service (Java) | Postgres | REST/webhooks |
| 27 | Specialty / Niche | Domain services (Python + Java) | Postgres | REST |
| 28 | Credit Cards — advanced tooling | Cards service (Java) + Controls UI | Postgres | REST `/cards` |
| 29 | Super-App / Embedded / Wallets | Lifestyle + Wallet services (TS + Python) | Postgres | REST/GraphQL |
| 30 | Business Banking — depth | Business service (TS + Python) | Postgres | REST `/business` |
| 31 | Financial Inclusion / Rural / Agri | Lending + Onboarding (Python) | Postgres, Qdrant | REST |
| 32 | Advanced AI & Agentic Banking | **AI Platform (LangGraph + Temporal + MCP)** | Qdrant, Neo4j, vLLM | REST/stream |
| 33 | Financial Wellness & Personalisation | Insights service (Python) | ClickHouse, Iceberg | GraphQL |
| 34 | Notifications / Alerts / UX | Notifications + Channels | Redis | Push/SMS/email |
| 35 | Statements / Documents / Self-Service | Documents service (Python/Docling) + Channels | S3, Postgres | REST |
| 36 | Rewards / Travel / Perks | Rewards service (TS + Java) | Postgres | REST |
| 37 | New / Emerging niche | Varies (tokenised deposits, CBDC, etc.) | Varies | Varies |

## Feature-type → component summary

| Type | Primary component(s) | Count |
|---|---|---|
| **CORE** | Core banking + Accounts | 32 |
| **PAY** | Payments | 42 |
| **CARD** | Cards | 29 |
| **LEND** | Lending | 32 |
| **WEALTH** | Wealth | 43 |
| **BIZ** | Business | 57 |
| **SEC** | Fraud + Identity (`aegis/`) | 32 |
| **AI** | AI Platform (`aegis/`) | 44 |
| **DATA** | Data platform | 45 |
| **OPEN** | Open Banking | 7 |
| **LIFE** | Lifestyle / Channels | 39 |
| **ESG** | ESG | 16 |
| **COMP** | Compliance (`aegis/`) | 25 |
| **ACC** | Channels (WCAG 2.2 AA) | 12 |
| **PLAT** | Platform / Data / Infra | 25 |

## Coverage notes

- **Implemented today:** `aegis/` (AI + Risk + Compliance plane) covers types **SEC, AI, COMP** and parts of **LEND/WEALTH/DATA**.
- **To build:** CORE, PAY, CARD, BIZ, LIFE, OPEN, ESG, PLAT transactional services (see `03_high_level_design_hld/01_end_to_end_system_architecture.md`).
- **AI use cases:** see `03_high_level_design_hld/07_ai_use_case_catalog.md` (~46 use cases mapped to the same components).
- **Language/framework per domain:** see `../../04_engineering_and_code/01_codebase_organization_and_standards/04_language_and_framework_mapping.md`.

## Full feature detail

The authoritative feature list is [../../01-business-and-strategy/14_feature_catalog.md](../../01-business-and-strategy/14_feature_catalog.md) (37 sections, ~485 features, tagged `[TYPE] [PRIORITY] [PROVEN]`).