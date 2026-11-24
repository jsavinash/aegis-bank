# 03 — Implementation

How the recommended stack maps onto the **actual code** in this repository, which **languages/frameworks** implement each feature domain, and how to run it.

| # | Document | Contents |
|---|---|---|
| 01 | [01-codebase-mapping.md](./01-codebase-mapping.md) | Maps the `aegis/` package modules to the AI/ML platform layers, with the offline-safe substitutes and the production drop-ins for each pillar. |
| 02 | [02-language-and-framework-mapping.md](./02-language-and-framework-mapping.md) | **Language & framework mapping** for all ~600 features (37 sections, 15 types): polyglot rules (Python for AI, Go for money-at-speed, TypeScript for screens, buy-the-core), per-section language/framework table, AI-plane detail, framework shortlist, and decision cheat-sheet. |

**Repository layout (this repo implements the AI risk & compliance plane):**

```
aegis/
  models/        domain models (Pydantic)
  db/            Postgres + pgvector engine, session, repositories
  security/      JWT, RBAC, signed agent principals
  guardrails/    fail-closed input/output/tool-call rails
  llm/           multi-provider router, prompts, two-pass, budgets
  rag/           hybrid retriever, graph store, query router
  memory/        short + long-term memory
  agents/        specialist agents (evidence, typology, network, narrative, QA, risk, gap)
  graphs/        LangGraph-shaped case graph
  workflows/     Temporal durable workflows
  tools/         MCP-shaped tool registry
  risk/          real-time hot-path scorer
  compliance/    obligations, controls, gaps
  eval/          LLM-as-judge + eval suites + CI gate
  api/           FastAPI app + routers
  cli.py         typer CLI
```

**Quick start (offline, zero deps):**
```bash
python3.12 -m venv .venv && source .venv/bin/activate
pip install -e ".[dev]"
make test        # 25 tests
aegis investigate  # run a sample AML investigation
aegis score        # score a transaction
make serve         # http://localhost:8000/docs
```

**Production swap-ins (see `../02-technology-stack/02-ai-stack-pillars.md`):**
`pip install -e ".[dev,ai]"` adds PyTorch/HF/Unsloth, Docling/Qdrant, LlamaIndex, Langfuse/Ragas, NeMo, vLLM/Ollama clients.