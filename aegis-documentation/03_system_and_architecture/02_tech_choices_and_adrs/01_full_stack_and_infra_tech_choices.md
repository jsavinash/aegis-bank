# Full-Stack and Infrastructure Tech Choices

## Summary

| Layer | Choice | Rationale |
|---|---|---|
| **Core banking** | Buy (Thought Machine/Mambu) | Config-driven; speed; compliance |
| **Transactional services** | Go (`chi`, `pgx`, `grpc-go`) | Low latency, concurrency |
| **AI / services** | Python (FastAPI, LangGraph, LlamaIndex) | AI ecosystem |
| **Channels** | TypeScript (React Native/Flutter, Next.js) | Cross-platform, velocity |
| **BFF** | GraphQL (Apollo) + REST | Flexible client queries |
| **Identity** | Keycloak / Ory (passkeys) | Open, self-hostable |
| **Edge** | Kong + WAF | Rate limiting, security |
| **Events** | Redpanda/Kafka + Flink | Real-time backbone |
| **Vector / Graph** | Qdrant + Neo4j | RAG + GraphRAG |
| **Inference** | vLLM (prod) / Ollama (dev) + LiteLLM | Throughput, portability |
| **Data** | Iceberg on S3 + ClickHouse + dbt + Dagster | Lakehouse + OLAP |
| **Feature store** | Feast | Online/offline parity |
| **Observability** | OTel + Grafana; Langfuse; Ragas | Traces + eval |
| **Guardrails** | NeMo + Llama Guard | Programmable safety |
| **Infra** | EKS/GKE + Terraform + ArgoCD | Elastic, reproducible |

See ADRs for binding decisions.