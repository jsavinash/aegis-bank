# Aegis

**AI-native financial crime compliance platform.** A large-scale, production-grade monorepo that unifies three reference architectures into one system:

1. **AML Investigation & SAR Engine** — agentic case investigation with human sign-off, durable execution, hybrid + GraphRAG retrieval, and citation-required narrative generation.
2. **Real-Time Transaction Risk** — a sub-150ms deterministic hot path (GNN/GBM-shaped) plus an agentic warm path for the ambiguous tail, with constrained structured verdicts.
3. **Regulatory Change Intelligence** — obligation ingestion, a temporal knowledge graph, control mapping, gap analysis, and an eval-driven self-improvement loop.

Everything runs offline with zero external dependencies (mock LLM, in-memory graph, SQLite-friendly stubs) so you can `make dev` and exercise the full system in minutes. Swap `LLM_PROVIDER=openai|anthropic`, add a Postgres+pgvector and Neo4j, and you have a deployable stack.

## Documentation

All project documentation lives in [`docs/`](./docs/README.md), organized in four tech-stack-aligned sections:

| Section | Documents | Contents |
|---|---|---|
| **00 · Product & Vision** | [`01-product-and-architecture.md`](./docs/00-product-and-vision/01-product-and-architecture.md) · [`02-feature-catalog.md`](./docs/00-product-and-vision/02-feature-catalog.md) | Retail banking product & architecture spec; exhaustive ~600-feature catalog |
| **01 · AI & Architecture** | [`01-ai-native-architectures.md`](./docs/01-ai-and-architecture/01-ai-native-architectures.md) | Three AI-native architecture deep-dives (AML, real-time risk, regulatory intelligence) |
| **02 · Technology Stack** | [`01-tech-stack-recommendation.md`](./docs/02-technology-stack/01-tech-stack-recommendation.md) · [`02-ai-stack-pillars.md`](./docs/02-technology-stack/02-ai-stack-pillars.md) | **Recommended AI-first stack** (layer-by-layer, reference architecture, build-vs-buy, phased plan); 7 AI pillars mapped to code |
| **03 · Implementation** | [`01-codebase-mapping.md`](./docs/03-implementation/01-codebase-mapping.md) | How `aegis/` maps to the stack; offline-safe substitutes + production drop-ins |

---

## Feature map

| Domain | Features |
|---|---|
| **Agentic orchestration** | Supervisor + 6 specialist agents (Evidence Gatherer, Typology Analyst, Network Analyst, Narrative Writer, QA Reviewer, Risk Analyst, Gap Analyst); LangGraph-shaped case graph; typed `CaseState`; two-pass structured output |
| **Durable execution** | Temporal workflows for case investigation and multi-day human approval; idempotent side effects; crash-proof resume |
| **Hybrid RAG + GraphRAG** | BM25 + vector hybrid retrieval with reranking; temporal knowledge graph (validity intervals); query router (hybrid vs graph) |
| **Guardrails** | Fail-closed input/output/tool-call rails; deterministic-first (regex/schema ~2.6ms); prompt-injection + secret detection; least-privilege tool policy; write-approval gates |
| **Risk engine** | Hot-path scorer (velocity, geo, device, shared-device, merchant, chargebacks); decision routing; LLM warm-path analyst with grounded rationale |
| **Compliance** | Obligation/control/gap models; temporal obligation validity; gap analysis agent |
| **Evaluation** | Calibrated LLM-as-judge (rubric + pairwise, bias-controlled, pinned); tagged regression suites (fact/multi-hop/temporal/unanswerable/global); CI gate |
| **Observability** | Structured JSON logging; OpenTelemetry tracing; audit events |
| **Security** | JWT auth (HS256), RBAC roles/scopes, signed agent principals (HMAC) |
| **API** | FastAPI v1: cases, alerts, entities, risk, compliance, eval; OpenAPI docs at `/docs` |
| **Infra** | Dockerfile, docker-compose (pg+pgvector, redis, neo4j, temporal), Alembic migrations, Makefile |

---

## Quick start (offline, no infra)

```bash
python3.12 -m venv .venv && source .venv/bin/activate
pip install -e ".[dev]"
make test          # run the full test suite
aegis investigate  # run a sample AML investigation
aegis score        # score a sample transaction
aegis eval         # run the eval suite + CI gate
make serve         # start the API at http://localhost:8000/docs
```

## With real infrastructure

```bash
make up            # postgres+pgvector + redis
pip install -e ".[graph,anthropic]"
export DATABASE_URL=postgresql+asyncpg://aegis:aegis@localhost:5432/aegis
export LLM_PROVIDER=anthropic ANTHROPIC_API_KEY=sk-...
make migrate
make serve
```

---

## Architecture (high level)

```mermaid
flowchart TD
    Client([Client / Analyst]) --> API[FastAPI]
    API --> Cases[Cases Router]
    API --> Risk[Risk Router]
    API --> Comp[Compliance Router]
    Cases --> Graph[Case Graph]
    Graph --> Agents[Specialist Agents]
    Agents --> Tools[Tool Registry / MCP]
    Agents --> RAG[Hybrid + GraphRAG]
    Agents --> LLM[LLM Router]
    Agents --> Guard[Guardrail Engine]
    Tools --> Guard
    Guard --> Ext[Systems of Record]
    Risk --> Hot[Hot Path Scorer]
    Risk --> Warm[Warm Path Agent]
    Cases --> WF[Temporal Workflows]
    Agents --> Audit[(Audit / Postgres)]
    Agents --> Obs[OTel / Logs]
```

## Layout

```
aegis/
  config.py          settings
  telemetry.py       logging + tracing
  models/            domain models (Pydantic)
  db/                engine, session, repositories
  security/          JWT, RBAC, signed principals
  guardrails/        fail-closed rails
  llm/               router, prompts, two-pass, budgets
  rag/               hybrid retriever, graph store, router
  memory/            short + long-term memory
  agents/            specialist agents
  graphs/            case graph
  workflows/         temporal durable workflows
  tools/             tool registry / MCP-style tools
  risk/              hot-path scorer + routing
  compliance/        obligations, controls, gaps
  eval/              LLM judge + eval suites + CI gate
  api/               FastAPI app + routers
  cli.py             typer CLI
tests/               unit + integration tests
migrations/          alembic
```

## Testing

```bash
make test        # all tests
make test-cov    # with coverage
make lint        # ruff
```

## Notes on production hardening

- Replace the in-memory case store with the SQLAlchemy repositories (already stubbed in `aegis/db/repositories.py`).
- The mock LLM (`LLM_PROVIDER=mock`) is deterministic and dependency-free; production uses Anthropic/OpenAI with structured outputs and constrained decoding.
- Guardrails fail closed by default (`GUARDRAILS_FAIL_CLOSED=true`).
- Every agent run is designed to be a Temporal workflow; `aegis/workflows` ships the worker definitions.

License: Apache-2.0.