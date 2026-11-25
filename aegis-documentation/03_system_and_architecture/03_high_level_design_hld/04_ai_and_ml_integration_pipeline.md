# AI and ML Integration Pipeline
> **Scope:** all 37 feature-catalog sections + AI use cases (as features) are in scope per [`in_scope_and_out_of_scope.md`](../../01-business-and-strategy/05_scope_and_boundaries/in_scope_and_out_of_scope.md); out of scope: physical branches, proprietary payment scheme, own core-ledger build.

## Pipeline

`mermaid
flowchart LR
 EVT[Event Bus] --> FEAT[Feature Store Feast]
 EVT --> LAKE[(Iceberg/S3)]
 LAKE --> TRAIN[Training: PyTorch/HF/Unsloth]
 TRAIN --> REG[Model Registry]
 REG --> SERVE[vLLM / BentoML / KServe]
 FEAT --> SERVE
 SERVE --> RISK[AI Risk Plane]
 RISK --> GUARD[NeMo + Llama Guard]
 RISK --> LF[Langfuse Trace]
 LF --> EVAL[Ragas Eval + CI Gate]
 EVAL --> MON[Drift / Quality Monitor]
 MON --> LAKE
`

## Components

| Stage | Tool | Purpose |
|---|---|---|
| **Ingestion** | Kafka + Flink | Stream txn/behaviour events |
| **Features** | Feast | Online/offline parity |
| **Training** | PyTorch, HF, Unsloth (LoRA) | Fraud GNN, credit, classifiers |
| **Registry** | MLflow | Version models |
| **Serving** | vLLM (LLM), BentoML/KServe (ML) | Low-latency inference |
| **Orchestration** | LangGraph + Temporal | Agents, HITL |
| **RAG** | LlamaIndex + Qdrant + Neo4j | Retrieval, GraphRAG |
| **Guardrails** | NeMo + Llama Guard | Fail-closed safety |
| **Eval** | Ragas + Braintrust | Quality gates |
| **Tracing** | Langfuse + OTel | Observability |
| **Monitoring** | Evidently + custom | Drift, quality, fairness |
| **Cache** | Redis / semantic cache | Cost/latency reduction |

## Two-pass structured output

Where an agent must both act and emit strict JSON: pass 1 (tools on, no schema) → pass 2 (tools off, schema on) to avoid constraint-tax tool suppression.

## Human-in-the-loop

High-consequence actions (SAR, freeze, loan approval) pause via LangGraph `interrupt()` / Temporal, resuming after human approval.

## Model routing

LiteLLM gateway routes tiers (fast/balanced/reasoning) to vLLM/Ollama/frontier; budgets enforced per step.

## Feedback, drift & shadow

- **Feedback loop:** production traces + human verdicts → labelled dataset (Langfuse → Iceberg).
- **Drift monitors:** data/model drift + fairness metrics; alerts on breach.
- **Shadow / champion-challenger:** new models scored in shadow before promotion.

## Cost & latency

- Per-step token budget; semantic + response caching; route easy steps to cheaper tiers.
- Hot-path (fraud) uses precomputed graph embeddings; LLM never on the <150ms path.

## Security

- Prompt-injection rails on tool results; least-privilege MCP scopes; PII redaction before logging; eval for jailbreak/leakage.
