# Aegis — AI Tech Stack Integration Map (7 Pillars)

**Version:** 1.0 · **Date:** 24 Nov 2026 · **Companion to:** `../../01-business-and-strategy/13_high_level_solution_overview.md`, `../../01-business-and-strategy/14_feature_catalog.md`

This document maps the canonical 7-pillar open-source AI stack onto the Aegis platform. For each pillar: the chosen tool, why it wins, **where it plugs into this codebase**, and concrete integration notes. It also records deliberate deviations (where the repo already ships an offline-safe substitute that the production tool replaces 1:1).

---

## The 7-pillar stack

| # | Pillar | Tool(s) | Role in Aegis |
|---|---|---|---|
| 1 | Foundation models & training | **PyTorch + Hugging Face Transformers**, **Unsloth** (LoRA) | Load/train/fine-tune open-weight models (fraud GNN, risk, embeddings, small classifiers) |
| 2 | Data, embeddings & vector DBs | **Docling** (ingestion) + **Qdrant** (vector DB) | Parse statements/PDFs/IDs → clean Markdown; store & search embeddings |
| 3 | Orchestration & RAG | **LlamaIndex** | Advanced RAG, indexing, structured retrieval (GraphRAG, hybrid) |
| 4 | Agentic AI & workflows | **LangGraph** + **MCP** | Stateful multi-agent case graph; universal tool/data connectivity |
| 5 | Inference, serving & local run | **Ollama** (local) / **vLLM** (production) | Dev on laptops; high-throughput GPU serving |
| 6 | Observability & evaluation | **Langfuse** (tracing) + **Ragas** (eval) | Trace every LLM/tool call; score RAG quality |
| 7 | Security, guardrails & governance | **NeMo Guardrails** / **Llama Guard** | Programmable conversational rails + input/output safety classifier |

---

## Pillar 1 — Foundation Models & Training

**Choice:** PyTorch + Hugging Face `transformers` + `datasets` + `peft` + **Unsloth**.

**Why:** PyTorch is the universal training substrate; Hugging Face is the model hub + `Trainer`/`pipeline` API; Unsloth does LoRA/QLoRA fine-tuning at ~2× speed and a fraction of the memory — the default for small teams fine-tuning 7B–14B models on a single GPU.

**Where it plugs in ():**
- **** — the GNN/GBM hot-path scorer is a PyTorch model. `HotPathScorer` is the deterministic reference; the production scorer is a fine-tuned graph model.
- **** — embedding models (sentence-transformers) and the reranker are HF models.
- **** — small classification models (typology, alert triage) fine-tuned with Unsloth/LoRA.

**Integration notes:**
`python
# requirements (optional extra)
torch>=2.4
transformers>=4.45
datasets>=3.0
peft>=0.13
unsloth>=2024.11 # optional, GPU only
sentence-transformers>=3.2
`

- **Model tiers** already defined in (`LLMRouter.TIERS`) — map each tier to a HF model id; local dev uses Ollama (Pillar 5), prod uses vLLM.
- **Fine-tuning workflow:** `datasets` → `peft`/Unsloth LoRA → HF `Trainer` → export → serve on vLLM/Ollama. Version the adapter alongside `PROMPT_VERSION` in so eval runs are reproducible.
- **Deviation:** the repo's `MockLLM` stands in for any model so tests run with zero deps. Swap in a `HFTransformersLLM`/`OllamaLLM` behind the same `BaseLLM` interface.

---

## Pillar 2 — Data, Embeddings & Vector DBs

**Choice:** **Docling** (IBM) for ingestion + **Qdrant** for vectors.

**Why:** Docling converts complex PDFs, tables, scans, and IDs into clean, layout-aware Markdown/JSON — the best open parser for bank statements, KYC docs, and regulatory filings. Qdrant gives high-performance HNSW, rich **metadata filtering** (critical for tenant/partition isolation), and seamless local→cloud scaling.

**Where it plugs in ():**
- **** — `HybridRetriever` + `GraphStore` + `Router`. The vector store is pluggable; Qdrant is the production backend.
- **** — `DocumentRepository` already models a `documents` table with `VECTOR(1536)`; Qdrant replaces this for scale.
- **** — Docling parses regulatory PDFs into `Obligation` objects.

**Integration notes:**
`python
# requirements (optional extra)
docling>=2.9
qdrant-client>=1.12
`

- **Ingestion pipeline:** `docling.DocumentConverter().convert(pdf)` → Markdown + table structure → chunk (semantic) → embed (HF) → `qdrant_client.upsert()` with payload metadata (`source`, `case_id`, `in_force_from`, `tenant`).
- **Filtering is the reason to choose Qdrant** — `ef_search` tuning + payload indexes give correct tenant-filtered recall (the 2026 finding: pgvector's HNSW can collapse to 0.71 recall on selective filters; Qdrant holds ~0.98).
- **Hybrid retrieval:** Qdrant dense + BM25 (sparse) + reranker → `HybridRetriever.rerank()`; route corpus-wide/temporal queries to `GraphStore` via `Router.should_use_graph()`.
- **Deviation:** `HybridRetriever` currently uses an in-memory char-trigram stand-in; production swaps `_vector_scores` for a Qdrant query and BM25 for Qdrant sparse vectors.

---

## Pillar 3 — Orchestration & RAG

**Choice:** **LlamaIndex**.

**Why:** purpose-built for advanced RAG — composable indexes (vector, summary, keyword, **knowledge-graph**), query engines, routing, and structured retrieval. Cleaner than general chains for document Q&A over obligations, policies, and case evidence.

**Where it plugs in ():**
- **** — LlamaIndex becomes the retrieval engine behind `Router`: `VectorStoreIndex` (Qdrant) + `KnowledgeGraphIndex` (Neo4j) + `QueryFusionRetriever` for hybrid, plus a `RouterQueryEngine` for hybrid-vs-graph routing.
- **** — regulatory Q&A over obligations/controls.
- **** — Evidence Gatherer uses LlamaIndex query engines for policy/KYB retrieval.

**Integration notes:**
`python
# requirements (optional extra)
llama-index>=0.12
llama-index-vector-stores-qdrant>=0.4
llama-index-graph-stores-neo4j>=0.4
llama-index-retrievers-bm25>=0.5
`

- **GraphRAG done right:** use `KnowledgeGraphIndex` over a *scoped* obligation/entity graph (not every document) with **temporal validity** edges — matches the 2026 GraphRAG-Bench conclusion (hybrid floor, graph for multi-hop/temporal).
- **Two-pass structured output** (`two_pass` in ) still applies — LlamaIndex's structured `PydanticProgram` maps cleanly to the agent `Output` schemas.
- **Deviation:** LlamaIndex is added as the RAG engine; the agent orchestration stays on LangGraph (Pillar 4) rather than LlamaIndex `Workflows`, to keep fine-grained control.

---

## Pillar 4 — Agentic AI & Workflows

**Choice:** **LangGraph** + **MCP** (Model Context Protocol).

**Why:** LangGraph is the leading framework for production-grade **stateful** multi-agent graphs — explicit nodes/edges, typed shared state, checkpointers, and `interrupt()` for human-in-the-loop. MCP is the universal standard for connecting agents to external tools/data (300M SDK downloads/mo; OAuth, elicitation, long-running tasks are enterprise-grade).

**Where it plugs in ():**
- **** — `CaseGraph` is currently a LangGraph-shaped pure-Python graph. **Replace with LangGraph `StateGraph`** using `CaseState` as the typed state and a **Postgres checkpointer** (`langgraph-checkpoint-postgres`, already a dep).
- **** — each specialist becomes a LangGraph node; edges encode the supervisor flow and the QA→(revise|approve) conditional.
- **** — `ToolRegistry` is an MCP-shaped registry. Expose each tool as an **MCP server** (stdio/HTTP) so agents call them via MCP; the `guardrails.check_tool_call` rail runs in front of every MCP call.
- **** — Temporal workflows wrap LangGraph runs for durable, days-long HITL (approval gates via `interrupt`/signals).

**Integration notes:**
`python
# requirements (optional extra)
langgraph>=1.2
langgraph-checkpoint-postgres>=2.0
mcp>=1.1
`

- **State machine:** `CaseState` (in ) is the LangGraph state schema. Nodes: `evidence → typology → network → narrative → qa`; conditional edge on `QAReviewer.confidence` (≥0.85 approve, else revise/pending_review).
- **MCP tool contracts:** `Tool.schema` already emits `{name, description, input_schema, roles, write}` — directly mappable to an MCP `tools/list` response. Group tools around intent (few, well-described) per Anthropic's MCP guidance.
- **HITL:** `interrupt()` at the approval gate; Temporal holds the run across a multi-day wait.
- **Governance:** MCP tool calls pass through `GuardrailEngine.check_tool_call` (least-privilege by role, write-approval gate) — the 2026 OWASP MCP Top-10 mitigations.

---

## Pillar 5 — Inference, Serving & Local Run

**Choice:** **Ollama** (local dev) / **vLLM** (production).

**Why:** Ollama is the frictionless local runtime for development (pull + run any HF model in one command). vLLM is the production standard for high-throughput GPU serving via **PagedAttention** + **continuous batching**, and exposes an OpenAI-compatible API with **structured outputs / constrained decoding** and `--tool-strict-level parameter` (needed for the two-pass anti-tool-suppression mitigation).

**Where it plugs in ():**
- **** — add `OllamaLLM` and `vLLMLLM` behind `BaseLLM`; `LLMRouter` selects provider by tier/env.
- **Dev:** `LLM_PROVIDER=ollama` with a local model (e.g. a 7–14B instruct model).
- **Prod:** `LLM_PROVIDER=vllm` → OpenAI-compatible endpoint; `structured()` uses JSON-schema constrained decoding.

**Integration notes:**
`python
# requirements (optional extra)
ollama>=0.4 # or just HTTP
openai>=1.57 # vLLM is OpenAI-compatible
`

- **vLLM launch:** `vllm serve <model> --tool-call-parser hermes --tool-strict-level parameter --enable-auto-tool-choice` → guarantees schema-valid tool calls.
- **Constraint-tax mitigation:** keep **two-pass** (`two_pass`) — pass 1 tools-on/no-schema (gather), pass 2 tools-off/schema-on (emit) — avoids the 2026 tool-suppression failure on open-weight models.
- **Routing:** `LLMRouter.TIERS` maps `fast→Ollama small`, `reasoning→vLLM large`, etc. `Budget` () tracks token/cost per step.
- **Deviation:** `MockLLM` remains the offline default for CI; `OllamaLLM`/`vLLMLLM` are drop-in.

---

## Pillar 6 — Observability & Evaluation

**Choice:** **Langfuse** (tracing) + **Ragas** (RAG eval).

**Why:** Langfuse is the leading **open-source, framework-agnostic** LLM engineering/tracing platform (self-hostable → data residency for a bank). Ragas is the standard for RAG evaluation (faithfulness, answer relevancy, context precision/recall).

**Where it plugs in ():**
- **Tracing:** instrument (agent runs), (tool calls), (retrieval), (model calls) with Langfuse spans. The repo already has OTel () — Langfuse is an OTel-compatible backend.
- **Eval:** (`LLMJudge`, `EvalSuite`, CI gate) gains Ragas metrics alongside the existing rubric judge. The tagged regression suite (fact/multi-hop/temporal/unanswerable) becomes the Ragas dataset.
- **Audit:** Langfuse traces feed the audit trail ( `audit_events`), satisfying regulated traceability.

**Integration notes:**
`python
# requirements (optional extra)
langfuse>=2.50
ragas>=0.2
datasets>=3.0
`

- **Trace shape:** each case = a Langfuse trace; each agent node/tool call/retrieval = a span with `input`, `output`, `latency`, `token usage`, `model`, `prompt_version`.
- **Eval gate:** Ragas `evaluate()` on the tagged suite → `EvalSuite.gate()` blocks the merge if faithfulness/answer-relevancy regresses.
- **Self-hosting:** Langfuse runs in the bank's VPC (Postgres + ClickHouse) → no data leaves the perimeter.

---

## Pillar 7 — Security, Guardrails & Governance

**Choice:** **NeMo Guardrails** (programmable rails) / **Llama Guard** (safety classifier).

**Why:** NeMo Guardrails (NVIDIA, Apache-2.0) is the most comprehensive open toolkit for **programmable conversational rails** — it validates not just text but **tool calls and tool results** (the riskiest surface for indirect prompt injection). Llama Guard is the most widely adopted safeguard model for classifying input/output safety risk.

**Where it plugs in ():**
- **** — `GuardrailEngine` already implements fail-closed input/output/**tool-call** rails with deterministic-first checks. This is the integration point for NeMo rails (`input`, `output`, `tool` rail types) and Llama Guard as the model-backed classifier behind a rail.
- **** — every MCP tool call routes through `GuardrailEngine.check_tool_call` (least-privilege by role + write-approval gate).
- **** — signed agent principals (HMAC), RBAC roles/scopes, JWT.

**Integration notes:**
`python
# requirements (optional extra)
nemoguardrails>=0.24
llama-cpp-python>=0.3 # or HF transformers for Llama Guard 4
`

- **Deterministic-first:** keep regex/schema checks first (~2.6ms) — only invoke NeMo/Llama Guard where rules can't decide.
- **Fail-closed:** `GUARDRAILS_FAIL_CLOSED=true` (already default) → if a model-backed check can't run, block and alert.
- **Tool-result screening:** NeMo validates every MCP tool **result** as untrusted (regulatory PDFs, merchant names, KYC docs are injection vectors) — `GuardrailEngine.check_tool_call` already flags this.
- **Governance:** audit every guardrail decision into `audit_events` with `rule`, `action`, `model`, `prompt_version`.

---

## Wiring it all together — the request lifecycle

`mermaid
flowchart LR
 U[User / Analyst] --> API[FastAPI]
 API --> LG[LangGraph Case Graph]
 LG --> NODE[Agent Nodes<br/>]
 NODE --> MCP[MCP Tools<br/>]
 NODE --> RAG[LlamaIndex RAG<br/>]
 RAG --> DOC[Docling Ingest]
 RAG --> QDR[(Qdrant)]
 RAG --> NEO[(Neo4j Graph)]
 NODE --> LLM[LLM Router]
 LLM --> OLL[Ollama dev]
 LLM --> VLL[vLLM prod]
 NODE --> GRAIL[Guardrail Engine]
 GRAIL --> NEMO[NeMo / Llama Guard]
 API --> LF[Langfuse Traces]
 NODE --> LF
 EVAL[Ragas + LLMJudge] --> LF
 EVAL --> CI[CI Gate]
`

**End-to-end trace (AML case):** Alert → `LangGraph CaseGraph` → `EvidenceGatherer` (LlamaIndex over Qdrant/Docling) → `TypologyAnalyst` (vLLM structured) → `NetworkAnalyst` (Neo4j graph) → `NarrativeWriter` (two-pass, citation-required) → `QAReviewer` → HITL `interrupt()` (Temporal). Every step: OTel→Langfuse trace, NeMo tool-rail on each MCP call, audit event in Postgres, and Ragas/Judge score sampled into the eval suite.

---

## Dependency summary (optional extras)

Add to `pyproject.toml` `[project.optional-dependencies]`:

`toml
ai = [
 "torch>=2.4", "transformers>=4.45", "datasets>=3.0", "peft>=0.13",
 "unsloth>=2024.11", "sentence-transformers>=3.2",
 "docling>=2.9", "qdrant-client>=1.12",
 "llama-index>=0.12", "llama-index-vector-stores-qdrant>=0.4",
 "llama-index-graph-stores-neo4j>=0.4", "llama-index-retrievers-bm25>=0.5",
 "langgraph>=1.2", "langgraph-checkpoint-postgres>=2.0", "mcp>=1.1",
 "ollama>=0.4", "openai>=1.57",
 "langfuse>=2.50", "ragas>=0.2",
 "nemoguardrails>=0.24",
]
`

Install with: `pip install -e ".[dev,ai]"` (GPU extras: `unsloth`, `torch` CUDA build).

---

## Reconciliation with the existing repo

| Pillar | Tool | Repo today | Change |
|---|---|---|---|
| 1 | PyTorch/HF/Unsloth | `MockLLM`, `LLMRouter` tiers | Add `HF`/`Ollama` LLMs; GNN scorer in |
| 2 | Docling + Qdrant | `pgvector` DDL, in-memory `HybridRetriever` | Qdrant backend + Docling ingestion |
| 3 | LlamaIndex | hand-rolled `HybridRetriever` + `GraphStore` | LlamaIndex query engines behind `Router` |
| 4 | LangGraph + MCP | LangGraph-*shaped* `CaseGraph` + `ToolRegistry` | Real `StateGraph` + Postgres checkpointer; MCP servers |
| 5 | Ollama / vLLM | `MockLLM` | `OllamaLLM` (dev) / `vLLMLLM` (prod) |
| 6 | Langfuse + Ragas | OTel + `LLMJudge`/`EvalSuite` | Langfuse spans + Ragas metrics in CI gate |
| 7 | NeMo / Llama Guard | `GuardrailEngine` (deterministic) | NeMo rails + Llama Guard classifier behind it |

> Everything in this stack is optional at runtime — the repo ships **offline-safe substitutes** (mock LLM, in-memory vector/graph, deterministic guardrails) so `make test` and `make serve` work with zero external services. Each substitute is a 1:1 drop-in for its production tool.

---

*End of tech-stack integration map.*
