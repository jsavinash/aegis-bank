# 01 — Codebase Mapping (aegis/ → tech stack)

Maps the `aegis/` package to the **AI/ML platform** (Layer 3 of `../../../03_system_and_architecture/02_tech_choices_and_adrs/03_ai_first_tech_stack_recommendation.md`) and the 7 AI pillars (`../../../03_system_and_architecture/02_tech_choices_and_adrs/04_ai_stack_pillars.md`). Everything ships with **offline-safe substitutes** so `make test` and `make serve` run with zero external services; each substitute is a **1:1 drop-in** for its production tool.

## Module → layer → production drop-in

| `aegis/` module | Role | Repo today (offline-safe) | Production tool (drop-in) |
|---|---|---|---|
| `aegis/agents/` | Specialist agents (Evidence, Typology, Network, Narrative, QA, Risk, Gap) | Pure-Python agents over `MockLLM` | LangGraph nodes + real LLM |
| `aegis/graphs/` | Case state graph | LangGraph-*shaped* `CaseGraph` | LangGraph `StateGraph` + Postgres checkpointer |
| `aegis/workflows/` | Durable execution | Temporal stubs + local runner | Temporal workflows (HITL, idempotent) |
| `aegis/tools/` | Tool registry / MCP | MCP-shaped `ToolRegistry` + `GuardrailEngine` gate | MCP servers (stdio/HTTP) |
| `aegis/rag/` | Retrieval | In-memory BM25 + char-trigram vector + `GraphStore` | LlamaIndex + Qdrant + Neo4j/Graphiti |
| `aegis/llm/` | LLM routing & structured output | `MockLLM`, `LLMRouter`, `two_pass`, `Budget` | `OllamaLLM`/`vLLMLLM` + LiteLLM gateway |
| `aegis/guardrails/` | Safety plane | Deterministic fail-closed rails | NeMo Guardrails + Llama Guard 4 |
| `aegis/eval/` | Evaluation | `LLMJudge` + `EvalSuite` + CI gate | + Ragas metrics + Braintrust CI |
| `aegis/risk/` | Real-time scoring | Deterministic `HotPathScorer` | GNN/GBM model (PyTorch) + Feast features |
| `aegis/compliance/` | Regulatory KB | In-memory obligations/controls/gaps | + Docling ingestion + Graphiti temporal KG |
| `aegis/db/` | Persistence | Postgres + pgvector DDL | + Qdrant for scale |
| `aegis/security/` | AuthN/AuthZ | JWT + RBAC + HMAC principals | Keycloak/Ory + OPA |
| `aegis/api/` | HTTP surface | FastAPI routers | + APISIX/WAF + GraphQL BFF |
| `aegis/memory/` | Agent memory | In-memory `MemoryStore` | Postgres-backed + LangGraph store |

## Pillar → code → dependency extra

| Pillar | Code | Extra |
|---|---|---|
| 1 Foundation/training | `aegis/risk/`, `aegis/llm/` | `ai`: `torch`, `transformers`, `peft`, `unsloth`, `sentence-transformers` |
| 2 Data/embeddings | `aegis/rag/`, `aegis/db/`, `aegis/compliance/` | `ai`: `docling`, `qdrant-client` |
| 3 Orchestration/RAG | `aegis/rag/`, `aegis/graphs/` | `ai`: `llama-index*` |
| 4 Agentic/workflows | `aegis/graphs/`, `aegis/tools/`, `aegis/workflows/` | core: `langgraph`, `langgraph-checkpoint-postgres`, `mcp`, `temporalio` |
| 5 Inference/serving | `aegis/llm/` | `ai`: `ollama`, `openai` (vLLM-compatible) |
| 6 Observability/eval | `aegis/telemetry.py`, `aegis/eval/` | `ai`: `langfuse`, `ragas` |
| 7 Guardrails | `aegis/guardrails/` | `ai`: `nemoguardrails` |

## What is NOT yet in this repo (build next)

The `aegis/` package is the **AI risk & compliance plane**. The full bank (per `../../../01-business-and-strategy/13_high_level_solution_overview.md` and `../../../03_system_and_architecture/02_tech_choices_and_adrs/03_ai_first_tech_stack_recommendation.md`) additionally needs:

- **Core banking** (buy: Thought Machine/Mambu, or build on an event-sourced ledger)
- **Event backbone** (Redpanda/Kafka + Flink)
- **Identity/edge** (Keycloak/Ory passkeys, APISIX + WAF)
- **Channels** (React Native/Flutter + Next.js) — `web/` is currently empty
- **Data platform** (Iceberg + ClickHouse + dbt + Dagster)
- **Infra** (Terraform, EKS, ArgoCD) — `infra/` is currently empty

See the phased plan in `../../../03_system_and_architecture/02_tech_choices_and_adrs/03_ai_first_tech_stack_recommendation.md` §7 for the build order (P0 → P4).