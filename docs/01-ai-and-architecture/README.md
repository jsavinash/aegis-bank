# 01 — AI & Architecture

The AI-native reference architectures that make Aegis more than a bank with a chatbot, plus the full AI use-case catalog.

| # | Document | Contents |
|---|---|---|
| 01 | [01-ai-native-architectures.md](./01-ai-native-architectures.md) | Three AI-native, production-grade architectures — **Aegis-IR** (agentic AML investigation & SAR), **Sentinel-RT** (real-time fraud, hot GNN + warm LLM), **Lexicon** (regulatory GraphRAG) — each with value proposition, 4-layer AI implementation matrix, tech stack, data-flow trace, and risk analysis. Includes the cross-cutting production baseline (durable execution, least-privilege tools, fail-closed guardrails, grounding, two-pass structured output, eval-as-CI, OTel tracing, HITL). |
| 02 | [02-ai-use-case-catalog.md](./02-ai-use-case-catalog.md) | **AI use-case catalog** — ~50 use cases across 7 domains, classified by the HKMA/McKinsey agent archetypes (task / workflow / monitoring / multi-agent), each with value, data readiness, regulatory exposure, and 2026 precedents (Gartner, Evident, Deloitte, Blits, JPMorgan, HKMA, McKinsey). Includes a value-density prioritisation, stall-avoidance patterns, agentic governance controls, and stack mapping. |

**Why this matters:** `01` defines the three AI-native reference systems; `02` catalogues the full surface of AI use cases across the bank and how to sequence them. Together they are the concrete requirements the technology stack (`../02-technology-stack/`) must satisfy.

**Key engineering conclusions carried into the stack:**
- Agent orchestration + durable execution (LangGraph + Temporal)
- Hybrid RAG as the floor, GraphRAG as a routed capability (LlamaIndex + Qdrant + Neo4j)
- Sub-150ms ML hot path with the LLM only on the warm tail (vLLM/GNN)
- Eval-as-CI + LLM-as-judge + full OTel tracing (Langfuse + Ragas + Braintrust)
- Fail-closed guardrails with tool-result screening (NeMo + Llama Guard)