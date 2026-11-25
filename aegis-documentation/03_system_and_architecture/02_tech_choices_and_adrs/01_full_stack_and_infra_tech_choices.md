# Full-Stack and Infrastructure Tech Choices

**Policy: open-source first, industry-standard where it matters.** Prefer Apache-2.0 / MPL-2.0 / MIT / AGPL components that are the de-facto standard in their category; commercial products are the fallback (build-vs-buy D-001). Every layer has an open-source primary.

## Summary

| Layer | Open-source choice | License | Industry standard | Commercial fallback |
|---|---|---|---|---|
| **Core banking** | Apache Fineract / Mifos X (or TigerBeetle) | Apache-2.0 / MPL-2.0 | Emerging OSS standard; ledger via double-entry | Thought Machine / Mambu |
| **Transactional services** | Java (Spring Boot / Quarkus) | Apache-2.0 | ✅ de-facto enterprise standard | — |
| **AI / services** | Python (FastAPI, LangGraph, LlamaIndex) | MIT / Apache-2.0 | ✅ AI standard | — |
| **Channels** | TypeScript (React Native/Flutter, Next.js) | MIT | ✅ cross-platform standard | — |
| **BFF** | GraphQL (Apollo Server) + REST | MIT | ✅ standard | — |
| **Identity** | Keycloak / Ory (passkeys) | Apache-2.0 | ✅ OSS IAM standard | Auth0 / Okta |
| **Edge** | APISIX + WAF | Apache-2.0 | ✅ Gateway API standard | Kong (Enterprise) |
| **Events** | Kafka (Apache) + Flink | Apache-2.0 | ✅ de-facto event standard | Redpanda (BSL) |
| **Vector / Graph** | Qdrant + Neo4j (or Memgraph) | Apache-2.0 / GPLv3 | ✅ RAG/Graph standard | Pinecone / Weaviate Cloud |
| **Inference** | vLLM (prod) / Ollama (dev) + LiteLLM | Apache-2.0 / MIT | ✅ serving standard | OpenAI / Anthropic |
| **Data** | Iceberg on S3 + ClickHouse + dbt + Dagster | Apache-2.0 | ✅ lakehouse standard | Snowflake / Databricks |
| **Feature store** | Feast | Apache-2.0 | ✅ OSS standard | Tecton |
| **Observability** | OTel + Grafana; Langfuse; Ragas | Apache-2.0 / AGPL / MIT | ✅ OTel standard | Datadog / LangSmith |
| **Guardrails** | NeMo + Llama Guard | Apache-2.0 | Emerging standard | Guardrails AI (Enterprise) |
| **Infra** | Kubernetes + Terraform + ArgoCD | Apache-2.0 | ✅ de-facto standard | EKS/GKE (managed) |

See ADRs for binding decisions. Open-source components are self-hosted (data residency); commercial fallbacks require a build-vs-buy review.