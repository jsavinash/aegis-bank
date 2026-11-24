# Build vs Buy Options

## Decision framework

Score each component on: **strategic differentiation**, **commodity maturity**, **regulatory burden**, **time-to-market**, **total cost**.

## Recommendations

| Component | Decision | Rationale |
|---|---|---|
| **Core banking (ledger/product engine)** | **Buy** (Thought Machine Vault / Mambu) | Config-driven breadth; faster compliance; ledger is not our moat. Revisit build only if ledger *is* the thesis. |
| **KYC / KYB / sanctions data** | **Buy data** (ComplyAdvantage/Alloy/Unit21) + build orchestration | Data is a commodity; the agentic reasoning is the moat. |
| **Fraud data / consortium** | **Buy** (Sift/Forter/Featurespace) or in-house | Network effect + consortium data; augment with our GNN. |
| **LLM** | **Multi-provider** via LiteLLM; self-host open weights (vLLM) | Avoid lock-in; route cost/quality; residency. |
| **Observability / eval** | **Self-host** Langfuse + Grafana | Data residency; no per-seat cost. |
| **Agent orchestration** | **Build on** LangGraph + Temporal | Frameworks are commodities; our agent graphs + evals are IP. |
| **Identity** | **Buy/adopt** Keycloak or Ory | Don't build an IdP. |
| **Event backbone** | **Buy/adopt** Redpanda/Kafka + Flink | Event-driven from day one; every txn emits an event. |
| **Data platform** | **Buy/adopt** Iceberg/S3 + ClickHouse + dbt + Dagster | Open table formats; time-travel for AML temporal queries. |
| **Document AI** | **Buy/adopt** Docling | Best open parser for statements/KYC/regulatory PDFs. |
| **Feature store / ML serving** | **Buy/adopt** Feast + BentoML/KServe | Online/offline parity; low-latency GNN/GBM serving. |
| **Cloud / infra** | **Buy** (EKS/GKE) + Terraform | Standard. |

## Build-vs-buy for the AI plane

| Build | Buy |
|---|---|
| Agent graphs (AML, fraud, support) | Foundation models (frontier + open weights) |
| Eval suites & CI gates | Vector DB (Qdrant), graph DB (Neo4j) |
| Guardrails policy | Guardrail runtime (NeMo), safety model (Llama Guard) |
| Fraud GNN features | Fraud consortium data |
| RAG pipelines | Embedding/rerank models |
| Synthetic data pipelines (AML/fraud regen) | Document AI (Docling) |
| Feature engineering & feature-store tuning | Feature store (Feast), ML serving (BentoML/KServe) |

## Financial model

See `10_cost_roi_and_alternatives/cost_and_roi_summary.md`.