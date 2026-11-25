# 12 — Domain Glossary

Banking-specific terms used throughout this documentation set. The general glossary is in `00-glossary-and-terminology.md`.

| Term | Definition |
|---|---|
| **CDD / EDD** | Customer / Enhanced Due Diligence — risk-based identity and business verification. |
| **CTR** | Currency Transaction Report — filing for cash transactions over a threshold (e.g., $10k US). |
| **KYC / KYB** | Know Your Customer / Business. |
| **Sanctions screening** | Checking customers/transactions against OFAC/UN/EU/national lists. |
| **Layering** | Moving funds through multiple transactions to obscure origin. |
| **Mule account** | An account used to receive and forward illicit funds. |
| **SAR narrative** | The written account in a SAR describing who/what/when/where/why/how. |
| **Adverse media** | Negative news used in risk assessment. |
| **Source of funds / wealth** | Evidence of where money/assets originated. |
| **Ongoing monitoring** | Continuous transaction/behaviour monitoring post-onboarding. |
| **Periodic review** | Scheduled KYC refresh for risk-based re-verification. |
| **Covenant** | A condition in a loan agreement. |
| **DTI / LTV** | Debt-to-Income / Loan-to-Value ratios. |
| **Settlement** | Final transfer of funds between institutions. |
| **Clearing** | The process of reconciling payment obligations. |
| **Consent** | Customer authorisation for data access (grant/revoke/expiry). |
| **FATF** | Financial Action Task Force — AML standard-setter. |
| **FinCEN** | US Financial Crimes Enforcement Network. |
| **LangGraph** | Stateful multi-agent orchestration framework (typed state graphs, `interrupt()` HITL). |
| **Temporal** | Durable execution engine for long-running, crash-proof workflows with idempotent side effects. |
| **vLLM** | High-throughput GPU inference server (PagedAttention, constrained decoding, tool-strict-level). |
| **Ollama** | Local LLM runtime for development. |
| **LiteLLM** | Multi-provider LLM gateway (routing, fallbacks, budgets, caching). |
| **LlamaIndex** | Advanced RAG framework (vector + graph + hybrid + router query engines). |
| **Qdrant** | Vector DB with rich metadata filtering (tenant isolation) and HNSW. |
| **Neo4j / Graphiti** | Graph DB + temporal knowledge graph (validity intervals, superseded-on edges). |
| **Docling** | IBM document parser → clean Markdown/JSON for statements, KYC, regulatory PDFs. |
| **Feast** | Feature store (offline: Iceberg/S3, online: Redis) for online/offline parity. |
| **BentoML / KServe** | ML model serving (GNN/GBM fraud models at low latency). |
| **Langfuse** | Self-hostable LLM tracing/evaluation platform (OTel-compatible). |
| **Ragas** | RAG evaluation (faithfulness, answer relevancy, context precision/recall). |
| **Braintrust** | Eval-as-CI platform; gates merges on regression suites. |
| **NeMo Guardrails** | Programmable conversational rails (input/output/tool-call); fail-closed. |
| **Llama Guard** | Safety classifier behind a guardrail. |
| **OPA / Cedar** | Externalized authorization policy engine. |
| **SR 11-7** | Fed model risk management guidance (validation, monitoring, documentation, governance). |