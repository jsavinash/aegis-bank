# 02 — Technology Stack

The recommended **AI-first** technology stack and how it maps onto the codebase.

| # | Document | Contents |
|---|---|---|
| 01 | [01-tech-stack-recommendation.md](./01-tech-stack-recommendation.md) | **Recommended AI-first tech stack** for the full large-scale platform — layer-by-layer picks (core banking, identity/edge, channels, AI/ML platform, data, observability/eval, security, infra) with justifications and alternatives; AI-first design principles & delivery methodology; reference-architecture diagram; build-vs-buy guide; phased P0–P4 adoption plan; stack-specific risks. |
| 02 | [02-ai-stack-pillars.md](./02-ai-stack-pillars.md) | The **7-pillar open-source AI stack** (PyTorch/HF/Unsloth, Docling/Qdrant, LlamaIndex, LangGraph/MCP, Ollama/vLLM, Langfuse/Ragas, NeMo/Llama Guard) mapped onto the `aegis/` codebase with integration notes, dependency extras, and a reconciliation table. |

**How they relate:**
- `01` is the **full production bank stack** (core + channels + data + infra + AI).
- `02` is the **AI-plane deep dive** — the 7 pillars that sit inside Layer 3 of `01`.

**Layer map (from `01`):**
| Layer | Primary picks |
|---|---|
| Core banking | Thought Machine Vault / Mambu (buy) · Redpanda/Kafka + Flink |
| Identity & edge | Keycloak/Ory (passkeys) · Kong + WAF · GraphQL/REST BFF |
| Channels | React Native/Flutter · Next.js · agentic assistant |
| **AI/ML platform** | LangGraph + Temporal + MCP · LlamaIndex + Qdrant + Neo4j · vLLM/Ollama + LiteLLM · Feast · BentoML |
| Data | Iceberg on S3 · ClickHouse · dbt · Dagster |
| Observability/eval | Langfuse + OTel/Grafana · Ragas + Braintrust |
| Security | NeMo + Llama Guard · OPA · Vault · KYC/AML vendors |
| Infra | EKS/GKE · Terraform · ArgoCD · GitHub Actions |