# Full-Stack and Infrastructure Tech Choices

## Summary

| Layer | Choice | Rationale |
|---|---|---|
| **Core banking** | Buy (Thought Machine/Mambu) | Config-driven; speed; compliance |
| **Transactional services** | Java (Spring Boot / Quarkus, `chi`/`pgx`/`grpc` equivalents) | Low latency, concurrency; industry standard for banking |
| **AI / services** | Python (FastAPI, LangGraph, LlamaIndex) | AI ecosystem |
| **Channels** | TypeScript (React Native/Flutter, Next.js) | Cross-platform, velocity |
| **BFF** | GraphQL (Apollo) + REST | Flexible client queries |
| **Identity** | Keycloak / Ory (passkeys) | Open, self-hostable |
| **Edge** | APISIX + WAF | Rate limiting, security; richer plugin set (AI proxy, rate limiting, cache, RAG); better Gateway API conformance (36 HTTP extended features); fully Apache-2.0, actively developed (3.19.0, Nov 2026). Kong remains the Enterprise alternative with consumer model & support. |
| **Events** | Redpanda/Kafka + Flink | Real-time backbone |
| **Vector / Graph** | Qdrant + Neo4j | RAG + GraphRAG |
| **Inference** | vLLM (prod) / Ollama (dev) + LiteLLM | Throughput, portability |
| **Data** | Iceberg on S3 + ClickHouse + dbt + Dagster | Lakehouse + OLAP |
| **Feature store** | Feast | Online/offline parity |
| **Observability** | OTel + Grafana; Langfuse; Ragas | Traces + eval |
| **Guardrails** | NeMo + Llama Guard | Programmable safety |
| **Infra** | EKS/GKE + Terraform + ArgoCD | Elastic, reproducible |

See ADRs for binding decisions.