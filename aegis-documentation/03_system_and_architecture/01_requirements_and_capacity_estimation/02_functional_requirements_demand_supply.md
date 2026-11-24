# Functional Requirements — Demand & Supply

> These are the high-level FRs. The **full ~600-feature catalog** (37 sections) is in [`07_full_feature_catalog.md`](./07_full_feature_catalog.md) (and `../../01-business-and-strategy/14_feature_catalog.md`); its **architecture traceability** is in [`06_feature_traceability.md`](./06_feature_traceability.md); **all use cases** (business + system + AI) are in [`09_use_case_catalog_all.md`](./09_use_case_catalog_all.md) and [`08_ai_use_case_catalog.md`](./08_ai_use_case_catalog.md).

## Demand (functional requirements)

| ID | Requirement | Volume (illustrative) |
|---|---|---|
| FR-01 | Accounts (open, manage) | 1M accounts |
| FR-02 | Payments (domestic, P2P, cross-border) | 5M/month |
| FR-03 | Card authorization | 20M/month |
| FR-04 | Fraud scoring | Every card txn |
| FR-05 | KYC/AML checks | Onboarding + ongoing |
| FR-06 | Loan applications | 100k/month |
| FR-07 | Assistant queries | 2M/month |
| FR-08 | Statements/documents | On demand |

## Supply (components that satisfy demand)

| Requirement | Supplying component | Open-source stack | Commercial alternative |
|---|---|---|---|
| Accounts/payments/cards | Core banking engine + Go services | **Apache Fineract** (Apache 2.0, ledger + REST API) or **Mifos X** (MPL 2.0, Fineract + web/mobile), **TigerBeetle** (high-throughput double-entry ledger), Go microservices | Temenos, Mambu, Thought Machine |
| Card issuing & payment orchestration | Payments layer | **Hyperswitch** (Apache 2.0, Rust; 100+ processors, card vault), **Apache Fineract** for ledger | Marqeta, Stripe Issuing |
| Fraud scoring | AI risk plane | GNN/GBM + vLLM (open models) | Feedzai, Featurespace |
| KYC/AML | Compliance plane (`aegis/`) | Docling, LlamaIndex, NeMo, **Apache Fineract** audit journal | Actimize, NICE |
| Lending | Lending service | XGBoost + LangGraph; **Apache Fineract** loan/savings product engine | Temenos, Mambu |
| Assistant | AI platform | LangGraph + LlamaIndex | — |
| Data | Data platform | Kafka/Redpanda, Iceberg, ClickHouse | Snowflake, Databricks |

### Best open-source core banking options (2026)

| Project | License | Best for | Notes |
|---|---|---|---|
| **Apache Fineract** | Apache 2.0 (ASF top-level) | Ledger, loans, savings, deposits, double-entry accounting via REST API | Most actively released open core (1.16.1, Oct 2026); aimed at lenders/neobanks, not tier-1. Docker images **not production-ready** — you own image hardening, upgrades, EOD batch. |
| **Mifos X** | MPL 2.0 (engine Apache 2.0) | Financial-inclusion bank with ready web + mobile front ends | Distribution of Fineract + clients; 60+ implementation partners. |
| **TigerBeetle** | Apache 2.0 | Ultra-high-throughput double-entry financial transactions DB | Pairs as the hot accounting ledger beneath a composable core; not a full product engine. |
| **Hyperswitch** | Apache 2.0 (Rust) | Payment orchestration, routing across 100+ processors, card vault | Payments layer beside Fineract; card vault brings PCI DSS scope. |
| **Galoy core** | Mostly open source | Software-native banks; event-sourced multi-asset (fiat/stablecoin) double-entry core | Newer; custody-independent; AI-native but early. |

> **Recommendation:** **Apache Fineract** (+ Mifos X front end) as the open-source core ledger/product engine, **Hyperswitch** for card/payment orchestration, and **TigerBeetle** where raw ledger throughput dominates. Budget for the operational work open source shifts onto your team (image hardening, EOD batch, upgrades, no vendor SLA) — or contract a Fineract hosting/support partner.

## Demand-supply gap analysis

- **Core:** run an open-source engine (Apache Fineract) to avoid per-account license tax; integrate via events. Budget for operational ownership (hardening, upgrades, backup/DR, no vendor SLA).
- **Fraud/AML:** build the AI plane; scale horizontally.
- **Assistant:** stateless agents + shared vector/graph stores; scale via K8s.