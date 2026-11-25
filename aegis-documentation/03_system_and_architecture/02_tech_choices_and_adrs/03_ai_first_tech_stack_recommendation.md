# Aegis Bank — AI-First Technology Stack Recommendation

**Version:** 1.0 · **Date:** 24 Nov 2026 · **Scope:** full-stack technology selection for the Aegis large-scale, AI-first retail bank + risk/compliance plane.

**Companion docs:** `../../01-business-and-strategy/13_high_level_solution_overview.md` (product/architecture), `../../01-business-and-strategy/14_feature_catalog.md` (~485 features), `../03_high_level_design_hld/06_ai_native_reference_architectures.md` (3 AI-native reference architectures), `04_ai_stack_pillars.md` (7-pillar AI stack).

---

## 1. What the docs describe (analysis summary)

| Document | What it defines | Implication for the stack |
|---|---|---|
| **../../01-business-and-strategy/13_high_level_solution_overview.md** | A full retail bank: accounts, cards, payments, lending, wealth, business, open banking, compliance; real-time ledger; 99.95% uptime; <150ms fraud decisions; WCAG 2.2 AA; PSD2/3, GDPR | Needs a **core banking system of record**, **event-driven** backbone, **strong security/identity**, and **AI risk plane** |
| **../../01-business-and-strategy/14_feature_catalog.md** | ~485 features across 37 domains (consumer, business, wealth, super-app, agri, emerging) | Breadth → **product-engine-driven** core (config, not code), heavy **API surface**, many **integrations** |
| **../03_high_level_design_hld/06_ai_native_reference_architectures.md** | Three AI-native systems: (A) agentic AML/SAR, (B) real-time fraud (hot GNN + warm LLM), (C) regulatory GraphRAG | Needs **agent orchestration + durable execution**, **graph + vector RAG**, **sub-150ms ML serving**, **eval/observability**, **guardrails** |
| **04_ai_stack_pillars.md** | 7 AI pillars already mapped (PyTorch/HF, Docling/Qdrant, LlamaIndex, LangGraph/MCP, Ollama/vLLM, Langfuse/Ragas, NeMo/Llama Guard) | This is the **AI plane**; this doc extends it into a full production bank stack |

**The central design fact:** the product is not "a bank with a chatbot." The **AI layer is the product** — it is the fraud engine, the investigator, the compliance analyst, the virtual assistant, and the underwriting model. Therefore the stack must be **AI-first**: AI is a first-class layer in the architecture, the data model, the delivery pipeline, and the operating model — not an add-on feature.

---

## 2. AI-first design principles

1. **AI as the primary interface.** The default customer and employee interface is conversational/agentic (voice + text), with traditional screens as a fallback for complex/authority actions. (DBS Joy, BofA Erica, Monzo/OnePay assistants — 2026 standard.)
2. **AI in every workflow, not just chat.** Every domain (fraud, AML, underwriting, KYC, support, marketing, ops) has an AI decision or copilot, each with a human escalation path.
3. **AI-native data layer as first-class.** Vector + graph + feature store are core infrastructure, not sidecars. Every transaction emits an event that feeds real-time ML and agents.
4. **Eval-driven engineering.** Every prompt, model, graph, and retrieval change is gated by an automated eval suite in CI. "It works" is defined by evals, not vibes.
5. **Human accountability designed in.** High-consequence actions (file SAR, freeze account, approve loan, move money) require human sign-off with a full audit trail.
6. **Model-agnostic & self-hostable.** Abstract the LLM layer so you can route across providers and self-host for data residency; never hard-wire a single vendor.
7. **Deterministic-first, AI-second.** Money math, authorization, and compliance rules are deterministic code; AI explains, decides ambiguity, and handles language — it never invents numbers.

---

## 3. The stack (layer by layer)

> Format: **Primary pick** — why · *Alternative(s)* · *Existing repo status*.

### Layer 0 — Core Banking (System of Record)

| Concern | Primary pick | Why | Alternative | Repo today |
|---|---|---|---|---|
| Core ledger / product engine | **Thought Machine Vault** or **Mambu** (buy) · **build** on an event-sourced ledger if full control needed | Config-driven products (accounts/loans/deposits = config, not code) → 485-feature breadth without 485 codebases; real-time multi-currency ledger | Temenos / Oracle FLEXCUBE / 10x / TCS BaNCS; open-source: **Apache Fineract**, **Frappe/Meta** | — (not yet built) |
| General ledger | Core-native GL + double-entry sub-ledgers | Auditability, regulatory reporting | — | — |
| Event backbone | **Redpanda** (Kafka-compatible) or **Apache Kafka** + **Flink** | Every txn emits an event → real-time fraud/AML/analytics; 10x pattern | NATS / Pulsar / Kinesis | (Postgres) only |
| Transaction DB | **PostgreSQL 16** (primary) + **pgvector** | One DB for ledger, audit, and (small) vectors; transactional JOINs | CockroachDB / Yugabyte for multi-region | ✅ |

**Verdict:** Buy the core (Vault/Mambu) if speed-to-market matters; build only if "ledger as a differentiator" is core to the thesis. Either way, **event-driven from day one** — the AI plane is impossible without a real-time event stream.

---

### Layer 1 — Identity, Edge & API

| Concern | Primary pick | Why | Alternative | Repo today |
|---|---|---|---|---|
| Identity / CIAM | **Keycloak** (self-host) or **Ory Kratos/Hydra** — with **FIDO2/passkeys** + risk-adaptive MFA | Passkeys in only 5% of apps (Ostorlab) → competitive edge; self-host for data residency | Auth0 / Okta CIC / AWS Cognito | (JWT + RBAC) |
| API gateway / WAF | **APISIX** or **Cloudflare** + **AWS WAF** | Rate limiting, mTLS, schema validation, bot defense; richer AI plugin set (ai-proxy, ai-rate-limiting, ai-cache, ai-rag); 36 HTTP extended Gateway API features | Kong (Enterprise) / Envoy / AWS API Gateway | — |
| BFF / API layer | **GraphQL (Apollo)** for app BFF + **REST/OpenAPI** for partner/open-banking | GraphQL for flexible client queries; REST for PSD2/partner contracts | tRPC / gRPC | ✅ FastAPI routers |
| API docs / contract | **OpenAPI 3.1** + **Spectral** linting in CI | Contract-first; gates breaking changes | — | FastAPI auto-docs |

---

### Layer 2 — Channels (Customer & Employee Experience)

| Concern | Primary pick | Why | Alternative | Repo today |
|---|---|---|---|---|
| Mobile app | **React Native** (Expo) or **Flutter**; native modules for biometrics/secure enclave | One codebase for iOS+Android; 2026 neobank standard; native modules for passkeys/secure key | Native Swift/Kotlin (max performance) | `web/` empty |
| Web banking | **Next.js** (TypeScript) + **Tailwind + shadcn/ui** | SSR/SEO, fast, accessible components | Remix / SvelteKit | — |
| Conversational AI layer | **Agentic assistant** (LangGraph) as primary nav + traditional UI fallback | AI-first UX; escalate to human for fraud/disputes (J.D. Power) | — | |
| Design system | **WCAG 2.2 AA**-compliant component library | EAA legal requirement in EU | — | — |
| Accessibility | Automated axe-core checks in CI + manual screen-reader testing | WCAG 2.2 AA is law | — | — |

---

### Layer 3 — AI/ML Platform (the heart of "AI-first")

This is where AI-first is won or lost. The 7 pillars from `04_ai_stack_pillars.md` are the base; here is the production hardening.

| Concern | Primary pick | Why | Alternative | Repo today |
|---|---|---|---|---|
| **Agent orchestration** | **LangGraph** (typed state graphs) | Fine-grained control, `interrupt` HITL, checkpointers | PydanticAI (single-agent), CrewAI (role-based) | (LangGraph-shaped) |
| **Durable execution** | **Temporal** | Crash-proof, days-long HITL waits, idempotent side effects | DBOS / Restate / Inngest | (Temporal stubs) |
| **Tool connectivity** | **MCP** servers per system-of-record | Universal agent-tool standard; OAuth, elicitation | Direct function calling | (MCP-shaped) |
| **RAG engine** | **LlamaIndex** (vector + KG + fusion + router) | Purpose-built RAG; GraphRAG + hybrid + routing | LangChain, Haystack | (hand-rolled) |
| **Vector DB** | **Qdrant** | Rich metadata filtering (tenant isolation), HNSW, local→cloud | pgvector (<10M), Weaviate, Milvus | (pgvector DDL) |
| **Graph DB** | **Neo4j** + **Graphiti** (temporal KG) | Temporal validity (superseded rules), mule-ring detection | TigerGraph, Memgraph, Postgres+Age | (in-memory GraphStore) |
| **Inference (prod)** | **vLLM** (PagedAttention, constrained decoding, `--tool-strict-level parameter`) | High-throughput, OpenAI-compatible, schema-valid outputs | TGI, SGLang, TensorRT-LLM | (MockLLM) |
| **Inference (dev)** | **Ollama** | One-command local models | LM Studio | — |
| **Model gateway** | **LiteLLM** (or Portkey) | Multi-provider routing, fallbacks, budgets, caching | OpenRouter | LLMRouter |
| **Training / fine-tune** | **PyTorch + HF Transformers + PEFT/Unsloth** | LoRA/QLoRA on one GPU; standard | — | — |
| **Embeddings / rerank** | **sentence-transformers / BGE** + cross-encoder reranker | Reranking is the biggest retrieval-quality lever | Cohere/AI21 rerank API | |
| **Feature store** | **Feast** (offline: Iceberg/S3, online: Redis) | Online/offline parity prevents training-serving skew | Tecton, Hopsworks | — |
| **ML serving (non-LLM)** | **BentoML** or **KServe** / **Ray Serve** | GNN/GBM fraud models at low latency | Triton, Seldon | (deterministic scorer) |
| **Document AI** | **Docling** (IBM) → Markdown/JSON | Best open parser for statements/KYC/regulatory PDFs | LlamaParse, Unstructured | — |

---

### Layer 4 — Data Platform

| Concern | Primary pick | Why | Alternative | Repo today |
|---|---|---|---|---|
| **Streaming** | **Redpanda/Kafka** + **Flink** | Event-time correctness, exactly-once, stream enrichment | Kinesis + Lambda | — |
| **Lakehouse** | **Apache Iceberg** on **S3** | Open table format; time-travel (critical for AML temporal queries) | Delta Lake, Hudi | — |
| **Warehouse / OLAP** | **ClickHouse** (real-time analytics) or **Snowflake/BigQuery** | Fast analytical queries over txn events | Druid, DuckDB | — |
| **Transformation** | **dbt** | Versioned, tested SQL models | — | — |
| **Orchestration (data)** | **Dagster** or **Airflow** | Asset-aware pipelines for ingestion/training | Prefect | — |
| **Data catalog / lineage** | **OpenMetadata** or **DataHub** | Governance for a regulated bank | — | — |
| **Reverse ETL** | **Census / Hightouch** | Sync ML scores back into core/CRM | — | — |

---

### Layer 5 — Observability, Evaluation & AIOps

| Concern | Primary pick | Why | Alternative | Repo today |
|---|---|---|---|---|
| **LLM tracing** | **Langfuse** (self-hosted in VPC) | Framework-agnostic, open-source, data residency | LangSmith, Arize Phoenix | (OTel) |
| **RAG eval** | **Ragas** + **Braintrust** (eval-as-CI) | Faithfulness/relevancy metrics; CI gates on PR | DeepEval | (LLMJudge + suite) |
| **LLM-as-judge** | Calibrated, bias-controlled, pinned (existing) | Consistent scoring across model/prompt changes | — | ✅ |
| **Metrics/logs/traces** | **OpenTelemetry** → **Grafana** (Tempo/Loki/Prometheus) or **Datadog** | One standard for every layer | — | ✅ OTel |
| **APM / real user monitoring** | **Grafana Faro** / Datadog RUM | Front-end + API performance | Sentry | — |
| **AIOps / anomaly** | **Grafana ML** / Datadog Watchdog | Detect fraud-model drift, latency anomalies | — | — |

---

### Layer 6 — Security, Guardrails & Governance

| Concern | Primary pick | Why | Alternative | Repo today |
|---|---|---|---|---|
| **Guardrails** | **NeMo Guardrails** + **Llama Guard 4** | Only framework validating tool calls/results; fail-closed | Guardrails AI | |
| **Secrets management** | **HashiCorp Vault** | Dynamic secrets, rotation, HSM-backed | AWS Secrets Manager | — |
| **Policy / authz** | **Open Policy Agent (OPA)** / Cedar | Externalized, auditable authorization | — | (RBAC) |
| **KYC / KYB / AML** | **ComplyAdvantage / Alloy / Unit21** (or in-house ) | Sanctions/PEP/adverse-media data + orchestration | Sumsub, Onfido | ✅ |
| **Fraud data** | **Sift / Forter / Featurespace** (or in-house) | Network effect + consortium data | — | ✅ |
| **SIEM** | **Elastic Security / Splunk** | Compliance-grade logging, threat detection | — | audit_events |
| **Data privacy** | Consent manager + **GDPR/CCPA** tooling (OneTrust / open-source) | Consent lifecycle, DSAR automation | — | — |

---

### Layer 7 — Platform, Infra & Delivery

| Concern | Primary pick | Why | Alternative | Repo today |
|---|---|---|---|---|
| **Compute** | **Kubernetes (EKS/GKE)** + Karpenter | Elastic, multi-service; GPU node pools for vLLM | Serverless (Lambda/Cloud Run) for spiky warm path | `Dockerfile`, `docker-compose` |
| **IaC** | **Terraform** (or **Pulumi**) | Reproducible multi-env infra | CDK | — |
| **GitOps / CD** | **Argo CD** + **GitHub Actions** | Declarative deploys, rollback | Flux, Spinnaker | — |
| **Service mesh** | **Istio** (or Linkerd) | mTLS, retries, traffic splitting for canary | — | — |
| **CI** | **GitHub Actions** with eval gates | Eval-gated merges are the AI-first quality moat | GitLab CI | — |
| **Secrets/CI** | GitHub Actions OIDC → cloud (no long-lived keys) | Secure, auditable | — | — |
| **Environments** | dev → staging → prod, all IaC-defined | Reproducible; prod parity | — | `.env.example` |

---

## 4. Reference architecture (AI-first)

`mermaid
flowchart TD
 subgraph CH["Channels"]
 Mob["Mobile (React Native/Flutter)"]
 Web["Web (Next.js)"]
 Voice["Voice / WhatsApp"]
 Ops["Ops Console (agents)"]
 end

 subgraph EDGE["Edge & Identity"]
 GW["APISIX + WAF"]
 ID["Keycloak / Ory<br/>Passkeys · MFA · Device binding"]
 BFF["BFF (GraphQL) + REST"]
 end

 subgraph CORE["Core Banking (System of Record)"]
 VAULT["Core Ledger + Product Engine<br/>(Thought Machine / Mambu / Fineract)"]
 GL["General Ledger"]
 CIF["Customer Info File"]
 EVT["Event Bus (Redpanda/Kafka + Flink)"]
 end

 subgraph AIPLANE["AI Platform"]
 LG["LangGraph Agents"]
 MCP["MCP Tool Servers"]
 RAG["LlamaIndex RAG"]
 QDR[("Qdrant")]
 NEO[("Neo4j + Graphiti")]
 LLM["LiteLLM Gateway"]
 VLLM["vLLM / Ollama"]
 FEAT["Feast Feature Store"]
 end

 subgraph DATA["Data Platform"]
 ICE[("Iceberg on S3")]
 OLAP[("ClickHouse / Snowflake")]
 DBT["dbt"]
 end

 subgraph RISK["Risk & Compliance "]
 FRAUD["Real-time Fraud (GNN)"]
 AML["Agentic AML / SAR"]
 REG["Regulatory GraphRAG"]
 end

 subgraph GUARD["Guardrails & Governance"]
 NEMO["NeMo + Llama Guard"]
 OPA["OPA / Cedar"]
 VAULTSEC["Vault"]
 AUDIT[("Audit Log")]
 end

 subgraph OBS["Observability & Eval"]
 LF["Langfuse + OTel"]
 EVAL["Ragas + Braintrust"]
 end

 CH --> GW --> ID --> BFF
 BFF --> CORE
 BFF --> AIPLANE
 CORE --> EVT --> DATA
 EVT --> RISK
 AIPLANE --> RAG --> QDR & NEO
 AIPLANE --> LLM --> VLLM
 RISK --> NEMO
 BFF --> NEMO
 AIPLANE --> OPA
 CORE --> AUDIT
 AIPLANE --> LF
 EVAL --> LF
 DATA --> FEAT
`

**AI-first request trace (customer):** "Pay this bill." → Mobile/voice → BFF → **Agent** (LangGraph) reads intent → **MCP tools** (billers, balance) via **NeMo rail** → **LLM** drafts payment (you authorise) → **Fraud scorer** (<150ms) → core ledger posts → event to Kafka → Langfuse trace + audit. The **agent is the interface**, the **event bus is the nervous system**, and **every step is traced and gated**.

---

## 5. AI-first delivery methodology (how you build)

The stack is only half of AI-first; the **way you build** is the other half.

| Practice | Tooling | What it means |
|---|---|---|
| **Eval-driven development** | Braintrust/Ragas + GitHub Action gate | No prompt/model/graph change merges without passing the tagged regression suite. Evals are the definition of "done." |
| **AI coding agents** | Claude Code / Cursor / Copilot + repo rules (AGENTS.md) | Agents write boilerplate, tests, migrations; humans review. Keep an `AGENTS.md` so agents know your conventions. |
| **Spec-first** | This doc set + OpenAPI schemas | Specs drive codegen (clients, mocks, tests). |
| **Prompt & model versioning** | Git + Langfuse + `PROMPT_VERSION` | Every prompt/model is versioned and reproducible. |
| **Synthetic data** | Strong model + validator | Generate labelled fraud/AML/regulatory scenarios for evals and fine-tuning (real outcomes are scarce). |
| **Human-in-the-loop by default** | LangGraph `interrupt()` + Temporal | High-consequence actions wait for a human, durably. |
| **Continuous eval + drift** | Langfuse online eval + shadow scoring | Production traces sampled → judge → dataset; drift monitors alert. |
| **Security-left** | SAST/DAST + MCP governance + threat modeling | OWASP MCP Top-10 mitigations, least-privilege tools, fail-closed guardrails. |

---

## 6. Build vs buy — decision guide

| Decision | Recommendation | Why |
|---|---|---|
| **Core banking** | **Buy** (Thought Machine / Mambu) for MVP; revisit build if ledger is the differentiator | 485 features on a config-driven core beats 485 hand-built modules; faster to compliance |
| **KYC/AML/fraud data** | **Buy data** (ComplyAdvantage/Alloy/Unit21/Sift) + **build the intelligence plane** () | Data is a commodity; the agentic reasoning + eval loop is your moat |
| **LLM** | **Multi-provider via LiteLLM**; self-host open weights (vLLM) for residency | Avoid lock-in; route cost/quality per step |
| **Observability** | **Self-host Langfuse + Grafana** | Data residency + no per-seat cost for a regulated bank |
| **Agent orchestration** | **Build on LangGraph + Temporal** | Frameworks are commodities; your agent graphs + evals are the IP |

---

## 7. Phased adoption (AI-first MVP → scale)

| Phase | Timeline | Stack focus | AI-first proof point |
|---|---|---|---|
| **P0 — Foundation** | Weeks 0–4 | Postgres + pgvector, FastAPI, Keycloak, (guardrails, LLM router, eval harness) | Eval-gated CI; offline mock → real LLM |
| **P1 — AI core** | Weeks 4–10 | LangGraph + Temporal + MCP, Qdrant + Neo4j + LlamaIndex, vLLM, Langfuse + Ragas | One agentic flow (AML or support) end-to-end, traced and eval-gated |
| **P2 — Bank MVP** | Weeks 10–20 | Core (buy), Redpanda + Flink, mobile (RN) + web (Next.js), real-time fraud (GNN) | Sub-150ms fraud + agentic assistant in prod |
| **P3 — Scale** | Months 5–9 | Iceberg + ClickHouse, Feast, BentoML/KServe, OPA + Vault, multi-region K8s | 10k+ TPS, champion/challenger, drift monitors |
| **P4 — Moat** | Months 9+ | Fine-tuned models (Unsloth), GraphRAG regulatory, super-app, embedded finance/BaaS | Self-improving evals; proprietary fine-tunes; agentic everything |

---

## 8. Key risks & mitigations (stack-specific)

| Risk | Mitigation |
|---|---|
| **LLM vendor lock-in** | LiteLLM gateway + `BaseLLM` abstraction; self-host open weights via vLLM |
| **Eval debt** | Eval gate in CI from day one; tagged regression suites; online eval on prod traces |
| **Data residency / regulation** | Self-host Langfuse, Qdrant, Neo4j, vLLM in-VPC; OPA policy; data catalog |
| **Core-banking complexity** | Buy the core; event-driven integration; don't rebuild a ledger unless it's the thesis |
| **Cost blow-up (agent loops)** | LiteLLM budgets, model routing, retrieval budgeting, Temporal checkpointing |
| **Security (MCP injection)** | NeMo tool-result rails, least-privilege task-scoped creds, approval gates, signed principals |
| **Latency (fraud path)** | GNN/GBM hot path with precomputed embeddings; LLM only on warm path; async budgets |
| **Accessibility (EAA)** | WCAG 2.2 AA in CI (axe-core) + manual screen-reader testing from P1 |

---

## 9. One-line stack summary

**Buy the core** (Thought Machine/Mambu), **stream everything** (Redpanda + Flink), **make AI a first-class layer** (LangGraph + Temporal + MCP + LlamaIndex + Qdrant + Neo4j + vLLM), **gate everything with evals** (Langfuse + Ragas + Braintrust), **secure by construction** (NeMo + Llama Guard + OPA + Vault), **run on K8s + Terraform + ArgoCD**, and **build the team around eval-driven, human-accountable, AI-first delivery**.

---

*End of AI-first tech stack recommendation.*
