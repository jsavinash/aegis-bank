# Full-Stack and Infrastructure Tech Choices

**Policy: open-source first.** Prefer Apache-2.0 / MPL-2.0 / MIT / AGPL components; commercial products are the fallback (build-vs-buy decision D-001). Every layer below has an open-source primary.

## Summary

| Layer | Open-source choice | License | Commercial fallback |
|---|---|---|---|
| **Core banking** | Apache Fineract / Mifos X (or TigerBeetle) | Apache-2.0 / MPL-2.0 | Thought Machine / Mambu |
| **Transactional services** | Java (Spring Boot / Quarkus; JPA/Hibernate, gRPC, virtual threads) | Apache-2.0 | — |
| **AI / services** | Python (FastAPI, LangGraph, LlamaIndex) | MIT / Apache-2.0 | — |
| **Channels** | TypeScript (React Native/Flutter, Next.js) | MIT | — |
| **BFF** | GraphQL (Apollo Server) + REST | MIT | — |
| **Identity** | Keycloak / Ory (passkeys) | Apache-2.0 | Auth0 / Okta |
| **Edge** | APISIX + WAF | Apache-2.0 | Kong (Enterprise) |
| **Events** | Kafka (Apache) + Flink | Apache-2.0 | Redpanda (BSL) |
| **Vector / Graph** | Qdrant + Neo4j (or Memgraph) | Apache-2.0 / GPLv3 | Pinecone / Weaviate Cloud |
| **Inference** | vLLM (prod) / Ollama (dev) + LiteLLM | Apache-2.0 / MIT | OpenAI / Anthropic |
| **Data** | Iceberg on S3 + ClickHouse + dbt + Dagster | Apache-2.0 | Snowflake / Databricks |
| **Feature store** | Feast | Apache-2.0 | Tecton |
| **Observability** | OTel + Grafana; Langfuse; Ragas | Apache-2.0 / AGPL / MIT | Datadog / LangSmith |
| **Guardrails** | NeMo + Llama Guard | Apache-2.0 | Guardrails AI (Enterprise) |
| **Infra** | Kubernetes + Terraform + ArgoCD | Apache-2.0 | EKS/GKE (managed) |

See ADRs for binding decisions. Open-source components are self-hosted (data residency); commercial fallbacks require a build-vs-buy review.