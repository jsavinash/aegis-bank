# Codebase Mapping (tech stack)

Maps the package to the **AI/ML platform** (Layer 3 of `../../../03_system_and_architecture/02_tech_choices_and_adrs/03_ai_first_tech_stack_recommendation.md`) and the 7 AI pillars (`../../../03_system_and_architecture/02_tech_choices_and_adrs/04_ai_stack_pillars.md`). Everything ships with **offline-safe substitutes** so `make test` and `make serve` run with zero external services; each substitute is a **1:1 drop-in** for its production tool.

## Module → layer → production drop-in

| module | Role | Repo today (offline-safe) | Production tool (drop-in) |
|---|---|---|---|
| | Specialist agents (Evidence, Typology, Network, Narrative, QA, Risk, Gap) | Pure-Python agents over `MockLLM` | LangGraph nodes + real LLM |
| | Case state graph | LangGraph-*shaped* `CaseGraph` | LangGraph `StateGraph` + Postgres checkpointer |
| | Durable execution | Temporal stubs + local runner | Temporal workflows (HITL, idempotent) |
| | Tool registry / MCP | MCP-shaped `ToolRegistry` + `GuardrailEngine` gate | MCP servers (stdio/HTTP) |
| | Retrieval | In-memory BM25 + char-trigram vector + `GraphStore` | LlamaIndex + Qdrant + Neo4j/Graphiti |
| | LLM routing & structured output | `MockLLM`, `LLMRouter`, `two_pass`, `Budget` | `OllamaLLM`/`vLLMLLM` + LiteLLM gateway |
| | Safety plane | Deterministic fail-closed rails | NeMo Guardrails + Llama Guard 4 |
| | Evaluation | `LLMJudge` + `EvalSuite` + CI gate | + Ragas metrics + Braintrust CI |
| | Real-time scoring | Deterministic `HotPathScorer` | GNN/GBM model (PyTorch) + Feast features |
| | Regulatory KB | In-memory obligations/controls/gaps | + Docling ingestion + Graphiti temporal KG |
| | Persistence | Postgres + pgvector DDL | + Qdrant for scale |
| | AuthN/AuthZ | JWT + RBAC + HMAC principals | Keycloak/Ory + OPA |
| | HTTP surface | FastAPI routers | + APISIX/WAF + GraphQL BFF |
| | Agent memory | In-memory `MemoryStore` | Postgres-backed + LangGraph store |

## Pillar → code → dependency extra

| Pillar | Code | Extra |
|---|---|---|
| 1 Foundation/training | , | `ai`: `torch`, `transformers`, `peft`, `unsloth`, `sentence-transformers` |
| 2 Data/embeddings | , , | `ai`: `docling`, `qdrant-client` |
| 3 Orchestration/RAG | , | `ai`: `llama-index*` |
| 4 Agentic/workflows | , , | core: `langgraph`, `langgraph-checkpoint-postgres`, `mcp`, `temporalio` |
| 5 Inference/serving | | `ai`: `ollama`, `openai` (vLLM-compatible) |
| 6 Observability/eval | , | `ai`: `langfuse`, `ragas` |
| 7 Guardrails | | `ai`: `nemoguardrails` |

## What is NOT yet in this repo (build next)

The package is the **AI risk & compliance plane**. The full bank (per `../../../01-business-and-strategy/13_high_level_solution_overview.md` and `../../../03_system_and_architecture/02_tech_choices_and_adrs/03_ai_first_tech_stack_recommendation.md`) additionally needs:

- **Core banking** (buy: Thought Machine/Mambu, or build on an event-sourced ledger)
- **Event backbone** (Redpanda/Kafka + Flink)
- **Identity/edge** (Keycloak/Ory passkeys, APISIX + WAF)
- **Channels** (React Native/Flutter + Next.js) — `web/` is currently empty
- **Data platform** (Iceberg + ClickHouse + dbt + Dagster)
- **Infra** (Terraform, EKS, ArgoCD) — `infra/` is currently empty

See the phased plan in `../../../03_system_and_architecture/02_tech_choices_and_adrs/03_ai_first_tech_stack_recommendation.md` §7 for the build order (P0 → P4).