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

| Requirement | Supplying component | Stack |
|---|---|---|
| Accounts/payments/cards | Core banking (bought) + Go services | Temenos/Mambu, Go |
| Fraud scoring | AI risk plane | GNN/GBM + vLLM |
| KYC/AML | Compliance plane (`aegis/`) | Docling, LlamaIndex, NeMo |
| Lending | Lending service | XGBoost + LangGraph |
| Assistant | AI platform | LangGraph + LlamaIndex |
| Data | Data platform | Kafka, Iceberg, ClickHouse |

## Demand-supply gap analysis

- **Core:** buy to meet demand; integrate via events.
- **Fraud/AML:** build the AI plane; scale horizontally.
- **Assistant:** stateless agents + shared vector/graph stores; scale via K8s.