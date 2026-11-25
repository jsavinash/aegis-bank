# Language & Framework Mapping

**Version:** 1.1 · **Date:** 24 Nov 2026 · **Companion to:** [../../01-business-and-strategy/14_feature_catalog.md](../../01-business-and-strategy/14_feature_catalog.md), [05_module_to_stack_mapping.md](./05_module_to_stack_mapping.md)

Maps the **full project** — all **~485 features** (37 sections, 15 feature types) **and** the **~46 AI use cases** (7 domains, 4 agent archetypes) — to the **languages and frameworks** that should implement each. The strategy is **polyglot**: one primary language per concern, chosen by the physics of the problem (latency, AI ecosystem, UI, correctness), not by preference.

**Coverage map:** §2–3 cover the feature catalog (by type + all 37 sections); §4–4b cover the AI plane and AI use cases; §5–6 give the framework shortlist and decision rules.

---

## 1. Language strategy (the rules)

| Rule | Consequence |
|---|---|
| **AI is Python.** | Everything touching models, agents, RAG, evals, data pipelines → **Python**. Non-negotiable (PyTorch/HF/LangGraph/LlamaIndex/vLLM/NeMo/Ragas are Python-first). |
| **Money moves fast in Java.** | Latency-critical transactional paths (ledger, payments, cards, fraud hot-path, gateways) → **Java** (Spring Boot / Quarkus). Low tail latency, virtual threads, strong type system, industry-standard for core banking, massive JVM ecosystem. |
| **Screens are TypeScript.** | All customer/employee interfaces → **TypeScript** (React Native/Flutter for mobile, Next.js for web). |
| **Buy the core, don't code it.** | The ledger/product engine is a **bought** system (Thought Machine/Mambu/Temenos) — its internals are Java; you integrate, you don't reimplement. |
| **Java only at the edges.** | **Java/Kotlin** only if you adopt a JVM core or need heavy enterprise integration (SWIFT, mainframe). |
| **Config over code.** | Products, fees, pricing are **configuration** in the core, not hard-coded modules — 485 features must not mean 485 codebases. |
| **Contract-first.** | Every service speaks **OpenAPI/AsyncAPI/GraphQL** — language is an implementation detail behind a contract. |

**Default split:** **Python** (AI + most services) · **Java** (transactional hot path + gateways, Spring Boot/Quarkus) · **TypeScript** (channels + BFF) · **SQL** (data).

---

## 2. Master mapping — feature type → language → framework

| Type | Features | Language | Primary frameworks | Notes |
|---|---|---|---|---|
| **CORE** | 32 | Java (ledger) · config in core | Spring Boot / Quarkus, JPA/Hibernate + Flyway · or bought core (Temenos/Mambu) | Double-entry, idempotent, ACID; correctness > velocity |
| **PAY** | 42 | Java | Spring Boot / Quarkus, Kafka, Flink (Java/Python) | Event-driven, exactly-once, ISO 20022 |
| **CARD** | 29 | Java | card-auth service (gRPC), Redis | <150ms authorization; tokenisation via vault |
| **LEND** | 32 | Python (decisioning) + Java (servicing) | Python: `scikit-learn`/`xgboost`, `fastapi` · Java: loan servicing (Spring Boot) | ML underwriting + explainability |
| **WEALTH** | 43 | Python | `pandas`/`polars`, `numpy`, `pyportfolioopt`, `fastapi` | Portfolio math, robo, analytics |
| **BIZ** | 57 | TypeScript (back-office UI) + Python (automation) | Next.js + `tRPC`, Python `fastapi`/Celery | AP/AR automation, invoicing, expense |
| **SEC** | 32 | Python (fraud/AML) + Java (authN/Z) | Python: GNN (PyG), guardrails · Java: session/keys (Spring Security) | Real-time fraud + identity |
| **AI** | 44 | Python | LangGraph, LlamaIndex, PyTorch, vLLM, LiteLLM, Ragas | The AI plane — see §4 |
| **DATA** | 45 | Python + SQL | `dbt`, Dagster, Polars, ClickHouse client | Pipelines, analytics, feature store |
| **OPEN** | 7 | Java + TypeScript | Spring Boot (APIs), Next.js (portal), OIDC | PSD2/open-banking APIs, consent |
| **LIFE** | 39 | TypeScript + Python | React Native/Flutter, Next.js, Python services | Super-app, lifestyle, mini-apps |
| **ESG** | 16 | Python | `pandas`, carbon-factor APIs, `fastapi` | Carbon tracking, ESG scoring |
| **COMP** | 25 | Python | compliance, Docling, Neo4j/Graphiti, NeMo | KYC/AML/regulatory — see |
| **ACC** | 12 | TypeScript | React Native/Next.js + `axe-core`, WCAG 2.2 AA | Accessibility is a UI concern |
| **PLAT** | 25 | Java + Python | K8s, Terraform, OTel, Kafka, Feast, vLLM | Infra, observability, events |

---

## 3. Per-section mapping (all 37 catalog sections)

| § | Section | Language(s) | Framework(s) / stack |
|---|---|---|---|
| 1 | Accounts & Deposits | Java (core) · config | Spring Boot / Quarkus, JPA/Hibernate + Flyway; or Temenos/Mambu |
| 2 | Payments & Transfers | Java | Spring Boot / Quarkus, Kafka, Flink; ISO 20022 |
| 3 | Cards | Java | gRPC card-auth, Redis, tokenisation vault |
| 4 | Bill Pay | Java + TypeScript | Spring Boot + Next.js scheduler UI |
| 5 | Lending & Credit | Python + Java | `xgboost`/`scikit-learn`, `fastapi`; Java servicing (Spring Boot) |
| 6 | Savings, Wealth & Investing | Python | `polars`/`pandas`, `pyportfolioopt`, `fastapi` |
| 7 | Money Management & Insights | Python + TypeScript | `polars`, `fastapi`; Next.js dashboards |
| 8 | Credit & Financial Health | Python | credit-bureau APIs, `scikit-learn`, `fastapi` |
| 9 | Security & Fraud | Python + Java | PyG/GNN, , ; Java authN (Spring Security) |
| 10 | Customer Service & AI | Python | LangGraph, LlamaIndex, MCP, vLLM () |
| 11 | Business & Commercial Banking | TypeScript + Python | Next.js + `tRPC`; `fastapi`/Celery |
| 12 | Treasury & Cash Management | Java + Python | Spring Boot; Python forecasting (`pandas`, Prophet) |
| 13 | Private Banking & Wealth | Python | `pyportfolioopt`, `fastapi`, RAG over mandates |
| 14 | Kids, Teens & Family | TypeScript + Java | React Native, Spring Boot (limits/controls) |
| 15 | Open Banking & Ecosystem | Java + TypeScript | Spring Boot (PSD2 APIs), Next.js portal, OIDC |
| 16 | Super-App & Lifestyle | TypeScript + Python | React Native/Flutter, Next.js, Python services |
| 17 | Sustainability / Green | Python | `pandas`, carbon-factor APIs, `fastapi` |
| 18 | Digital Assets & Stablecoins | Python + Java | `web3.py`/`ethers`, custody KYT; Java ledger (Spring Boot) |
| 19 | Rewards & Loyalty | TypeScript + Java | Next.js, Spring Boot (points ledger) |
| 20 | Insurance & Protection | Python | underwriting models, `fastapi` |
| 21 | Accessibility & Inclusion | TypeScript | React Native/Next.js, `axe-core`, WCAG 2.2 AA |
| 22 | Compliance, Risk & Regulatory | Python | (NeMo, LlamaIndex, Neo4j, Docling) |
| 23 | Platform, Data & AI Infra | Java + Python | K8s, Terraform, OTel, Kafka, Feast, vLLM |
| 24 | Notifications & UX | TypeScript + Python | React Native push, `fastapi` prefs service |
| 25 | International / Cross-border | Java | FX services, Spring Boot, corridor integrations |
| 26 | Payments Acceptance (merchant) | Java | acquiring/POS, Spring Boot, webhooks |
| 27 | Specialty / Niche | Python + Java | domain-specific (Islamic banking, agri) |
| 28 | Credit Cards — advanced tooling | Java + TypeScript | virtual-card service (Spring Boot), Next.js controls UI |
| 29 | Super-App / Embedded / Wallets | TypeScript + Python | React Native, wallet SDKs, Python services |
| 30 | Business Banking — depth | TypeScript + Python | Next.js + `tRPC`, AP/AR automation (Python) |
| 31 | Financial Inclusion / Rural / Agri | Python + TypeScript | alternative-data scoring, USSD/mobile-money |
| 32 | Advanced AI & Agentic Banking | Python | LangGraph + Temporal + MCP () |
| 33 | Financial Wellness & Personalisation | Python | `pandas`, `scikit-learn`, `fastapi` |
| 34 | Notifications / Alerts / UX | TypeScript + Python | push/SMS/email, preference service |
| 35 | Statements / Documents / Self-Service | TypeScript + Python | Next.js, Docling (PDF), `fastapi` |
| 36 | Rewards / Travel / Perks | TypeScript + Java | Next.js, offers engine (Spring Boot) |
| 37 | New / Emerging niche | Python + Java | varies (tokenised deposits, CBDC, etc.) |

---

## 4. The AI plane — framework detail (Python)

The 44 `AI` + 25 `COMP` + 32 `SEC` features share one Python AI plane (see [../../03_system_and_architecture/02_tech_choices_and_adrs/04_ai_stack_pillars.md](../../03_system_and_architecture/02_tech_choices_and_adrs/04_ai_stack_pillars.md)):

| Concern | Framework / library |
|---|---|
| Agent orchestration | **LangGraph** + **Temporal** + **MCP** |
| RAG / retrieval | **LlamaIndex** + **Qdrant** + **Neo4j/Graphiti** |
| Inference | **vLLM** (prod) / **Ollama** (dev) + **LiteLLM** gateway |
| Training / fine-tune | **PyTorch** + **HF Transformers** + **PEFT/Unsloth** |
| Fraud GNN | **PyTorch Geometric** / **DGL** + **LightGBM** |
| Guardrails | **NeMo Guardrails** + **Llama Guard** |
| Eval | **Ragas** + **Braintrust** + **Langfuse** |
| Documents | **Docling** |
| Feature store | **Feast** |
| ML serving | **BentoML** / **KServe** |

---

## 4b. AI use cases → language & framework

Maps the AI use-case catalog ([14_feature_catalog.md](../../01-business-and-strategy/14_feature_catalog.md)) to concrete languages and frameworks. All AI use cases are **Python**, but the *serving path* differs by latency class.

| Use case (from AI catalog) | Archetype | Language | Framework / stack | Latency class |
|---|---|---|---|---|
| Real-time fraud scoring | Monitoring | Python | PyTorch Geometric / DGL + LightGBM; **BentoML/KServe** | **Hot** (<150ms, no LLM) |
| Fraud alert triage | Task | Python | LangGraph + vLLM (structured output) | Warm (seconds) |
| AML investigation (Aegis-IR) | Multi-agent | Python | LangGraph + Temporal + LlamaIndex + Neo4j + NeMo | Warm (minutes) |
| SAR/STR narrative drafting | Task | Python | vLLM + two-pass structured output + NeMo | Warm |
| KYC/KYB & CDD automation | Workflow | Python | LangGraph + Docling + Qdrant + NeMo | Warm |
| Sanctions/PEP screening | Monitoring | Python | fuzzy-match + ML scoring + Qdrant | Warm |
| Customer service / contact centre | Workflow | Python (+TS UI) | LangGraph + LlamaIndex + MCP + vLLM | Warm (streaming) |
| Fraud alert confirmation (cardholder) | Task | Python | LangGraph + Twilio/voice + NeMo | Warm |
| Credit underwriting & memo | Task | Python | XGBoost + LangGraph + vLLM + doc extraction | Warm |
| Alternative-data credit scoring | Monitoring | Python | XGBoost/LightGBM + Feast | Batch/online |
| Wealth RM copilot / planning | Multi-agent | Python | LlamaIndex + LangGraph + vLLM | Warm |
| Regulatory change monitoring | Multi-agent | Python | LlamaIndex GraphRAG + Neo4j + Docling | Batch |
| Document/vision extraction | Task | Python | Docling + vision model (vLLM) | Batch |
| Developer augmentation | Workflow | Python/TS | coding agent (LLM) + CI | Interactive |
| Cybersecurity triage | Monitoring | Python | LLM + rules + SIEM integration | Warm |
| Agentic BI (NL→SQL) | Multi-agent | Python | text-to-SQL + governed semantic layer | Interactive |

**Pattern:** the **AI brain is always Python** (LangGraph/LlamaIndex/vLLM/NeMo). Only the *fraud hot path* needs a non-LLM, ultra-low-latency serving layer (still Python model, served via BentoML/KServe/Triton). Every AI use case is wrapped in the same **guardrails → eval → trace** plane (, , Langfuse).

---

## 5. Framework shortlist by concern

| Concern | Primary pick | Alternative |
|---|---|---|
| Backend API (AI/services) | **FastAPI** (Python) | Litestar, Flask |
| Backend API (transactional) | **Java (Spring Boot / Quarkus)** | gRPC, Quarkus / Vert.x |
| BFF / GraphQL | **Apollo GraphQL** (TypeScript) | tRPC |
| Mobile | **React Native** (Expo) / **Flutter** | Native Swift/Kotlin |
| Web | **Next.js** (TypeScript) | Remix, SvelteKit |
| Auth / identity | **Keycloak** / **Ory** (passkeys) | Auth0, Cognito |
| Event bus | **Redpanda/Kafka** + **Flink** | NATS, Pulsar |
| Ledger (build) | Java + Postgres / event-sourcing | **Buy**: Temenos/Mambu |
| Feature store | **Feast** | Tecton |
| Data transform | **dbt** | — |
| Orchestration (data) | **Dagster** | Airflow, Prefect |
| IaC / CD | **Terraform** + **ArgoCD** | Pulumi, Flux |
| Observability | **OTel + Grafana** | Datadog |
| LLM tracing/eval | **Langfuse** + **Ragas** + **Braintrust** | LangSmith, Arize |

---

## 6. Golden rules (decision cheat-sheet)

1. **Touching a model → Python.** No exceptions (features + every AI use case).
2. **Touching money at speed → Java.** Ledger, payments, cards, fraud hot path, gateway.
3. **Touching a screen → TypeScript.** Mobile, web, BFF.
4. **Touching the ledger product logic → buy, don't build.** Integrate via events/APIs.
5. **Touching many products → configure, don't hardcode.** Product engine, not 485 modules.
6. **Touching a contract → define it first.** OpenAPI/AsyncAPI/GraphQL; language is an implementation detail.
7. **Touching compliance → (Python) + human sign-off.** Never autonomous for regulated actions.
8. **Every AI use case → same guardrails/eval/trace plane.** Fraud hot path = non-LLM low-latency serving (BentoML/KServe); all else = LangGraph/LlamaIndex/vLLM + NeMo + Langfuse.

---

## 7. Full-project coverage checklist

| Scope | Covered in | Status |
|---|---|---|
| All 15 feature types | §2 master mapping | ✅ |
| All 37 feature-catalog sections | §3 per-section mapping | ✅ |
| AI plane frameworks | §4 | ✅ |
| All ~46 AI use cases (7 domains) | §4b | ✅ |
| Framework shortlist by concern | §5 | ✅ |
| Decision rules | §6 | ✅ |
| Code-level mapping () | [05_module_to_stack_mapping.md](./05_module_to_stack_mapping.md) | ✅ |
| Tech-stack layers (full bank) | [../../03_system_and_architecture/02_tech_choices_and_adrs/03_ai_first_tech_stack_recommendation.md](../../03_system_and_architecture/02_tech_choices_and_adrs/03_ai_first_tech_stack_recommendation.md) | ✅ |

---

*End of language & framework mapping.*