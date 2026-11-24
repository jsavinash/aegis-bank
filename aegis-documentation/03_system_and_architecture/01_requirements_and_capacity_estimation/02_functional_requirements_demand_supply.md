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
| Accounts/payments/cards | Core banking engine + Java microservices (Spring Boot / Quarkus) | **Apache Fineract** (Apache 2.0, ledger + REST API) or **Mifos X** (MPL 2.0, Fineract + web/mobile), **TigerBeetle** (high-throughput double-entry ledger) | Temenos, Mambu, Thought Machine |
| Card issuing & payment orchestration | Payments layer | **Hyperswitch** (Apache 2.0, Rust; 100+ processors, card vault), **Apache Fineract** for ledger | Marqeta, Stripe Issuing |
| Fraud scoring | AI risk plane | GNN/GBM + vLLM (open models) | Feedzai, Featurespace |
| KYC/AML | Compliance plane (`aegis/`) | Docling, LlamaIndex, NeMo, **Apache Fineract** audit journal | Actimize, NICE |
| Lending | Lending service | XGBoost + LangGraph; **Apache Fineract** loan/savings product engine | Temenos, Mambu |
| Assistant | AI platform | LangGraph + LlamaIndex | — |
| Data | Data platform | Kafka/Redpanda, Iceberg, ClickHouse | Snowflake, Databricks |

## Demand-supply gap analysis

- **Core:** run an open-source engine (Apache Fineract) to avoid per-account license tax; integrate via events. Budget for operational ownership (hardening, upgrades, backup/DR, no vendor SLA).
- **Fraud/AML:** build the AI plane; scale horizontally.
- **Assistant:** stateless agents + shared vector/graph stores; scale via K8s.