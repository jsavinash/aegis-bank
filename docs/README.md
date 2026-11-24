# Aegis Bank — Documentation

Documentation for the **Aegis** AI-first retail bank + AI risk/compliance platform. Organized in four numbered sections that follow the recommended AI-first tech stack (`02-technology-stack/`).

## Structure

```
docs/
├── README.md                        ← you are here (master index)
├── 00-product-and-vision/           WHAT & WHY
│   ├── 01-product-and-architecture.md
│   └── 02-feature-catalog.md
├── 01-ai-and-architecture/          HOW (AI-native designs)
│   ├── 01-ai-native-architectures.md
│   └── 02-ai-use-case-catalog.md
├── 02-technology-stack/             WITH WHAT (the stack)
│   ├── 01-tech-stack-recommendation.md
│   └── 02-ai-stack-pillars.md
└── 03-implementation/               BUILT HOW (code mapping)
    ├── 01-codebase-mapping.md
    └── 02-language-and-framework-mapping.md
```

## Reading order

| Step | Section | Document | What you get |
|---|---|---|---|
| 1 | **00 · Product & Vision** | [01-product-and-architecture.md](./00-product-and-vision/01-product-and-architecture.md) | The bank: market research, personas, modules, system/data diagrams, security, NFRs, roadmap |
| 2 | **00 · Product & Vision** | [02-feature-catalog.md](./00-product-and-vision/02-feature-catalog.md) | ~600 features across 37 sections, tagged `[TYPE] [PRIORITY] [PROVEN]`, with MVP shortlist |
| 3 | **01 · AI & Architecture** | [01-ai-native-architectures.md](./01-ai-and-architecture/01-ai-native-architectures.md) | Three AI-native systems (AML/SAR, real-time fraud, regulatory GraphRAG) + production baseline |
| 4 | **01 · AI & Architecture** | [02-ai-use-case-catalog.md](./01-ai-and-architecture/02-ai-use-case-catalog.md) | ~50 AI use cases across 7 domains, by agent archetype, with value/readiness/regulatory tags + prioritisation |
| 5 | **02 · Technology Stack** | [01-tech-stack-recommendation.md](./02-technology-stack/01-tech-stack-recommendation.md) | **The recommended AI-first stack** — layer-by-layer, reference architecture, build-vs-buy, phased plan |
| 6 | **02 · Technology Stack** | [02-ai-stack-pillars.md](./02-technology-stack/02-ai-stack-pillars.md) | The 7 AI pillars mapped onto the code, with dependency extras |
| 7 | **03 · Implementation** | [01-codebase-mapping.md](./03-implementation/01-codebase-mapping.md) | How `aegis/` maps to the stack; offline-safe substitutes + production drop-ins |
| 8 | **03 · Implementation** | [02-language-and-framework-mapping.md](./03-implementation/02-language-and-framework-mapping.md) | **Language & framework mapping** for the full project — all ~600 features (37 sections) + ~50 AI use cases, with polyglot rules and a coverage checklist |

## The stack at a glance (from `02-technology-stack/01-tech-stack-recommendation.md`)

| Layer | Primary picks |
|---|---|
| **Core banking** | Thought Machine Vault / Mambu (buy) · Redpanda/Kafka + Flink event bus |
| **Identity & edge** | Keycloak/Ory (passkeys + MFA) · Kong + WAF · GraphQL/REST BFF |
| **Channels** | React Native/Flutter · Next.js · agentic assistant as primary interface |
| **AI/ML platform** | LangGraph + Temporal + MCP · LlamaIndex + Qdrant + Neo4j · vLLM/Ollama + LiteLLM · Feast · BentoML |
| **Data** | Iceberg on S3 · ClickHouse · dbt · Dagster · OpenMetadata |
| **Observability & eval** | Langfuse + OTel/Grafana · Ragas + Braintrust (eval-as-CI) |
| **Security** | NeMo + Llama Guard · OPA · Vault · KYC/AML vendors |
| **Infra** | EKS/GKE · Terraform · ArgoCD · GitHub Actions |

## AI-first in one line

**Buy the core, stream everything, make AI a first-class layer, gate everything with evals, secure by construction.**

## Related code

The `aegis/` package implements the **AI risk & compliance plane** (agentic AML/SAR, real-time risk, regulatory intelligence, RAG, guardrails, eval). See [`03-implementation/01-codebase-mapping.md`](./03-implementation/01-codebase-mapping.md) and the root `README.md` for build/run instructions.

> **Note:** a separate `documentation/` folder at the repo root is a **different project's** template (Nestlé GLP-1 demand sensing) and is **not** part of Aegis. This `docs/` folder is the Aegis documentation set.