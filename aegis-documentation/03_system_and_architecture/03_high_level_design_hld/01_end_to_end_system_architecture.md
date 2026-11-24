# End-to-End System Architecture

## Context
Aegis is an AI-first retail bank. The architecture separates the **transactional core** (bought) from the **AI/risk plane** (built), connected by an **event backbone**.

## Diagram

```mermaid
flowchart TD
    subgraph CH[Channels]
        Mob[Mobile RN/Flutter]
        Web[Web Next.js]
        Voice[Voice/WhatsApp]
    end
    subgraph EDGE[Edge & Identity]
        GW[Kong + WAF]
        ID[Keycloak/Ory - passkeys]
        BFF[GraphQL/REST BFF]
    end
    subgraph CORE[Core Banking]
        LEDGER[Real-time Ledger]
        PRODUCTS[Product Engine]
        CIF[Customer Info File]
    end
    subgraph SVC[Domain Services - Go]
        PAY[Payments]
        CARD[Cards]
        LEND[Lending]
    end
    subgraph BUS[Event Backbone]
        KAFKA[Redpanda/Kafka + Flink]
    end
    subgraph AI[AI Platform - Python]
        AGENTS[LangGraph Agents]
        RAG[LlamaIndex RAG]
        VEC[(Qdrant)]
        GRAPH[(Neo4j)]
        VLLM[vLLM/Ollama + LiteLLM]
    end
    subgraph RISK[AI Risk - aegis/]
        FRAUD[Fraud Scorer]
        AML[AML Investigation]
        GUARD[NeMo + Llama Guard]
    end
    subgraph DATA[Data Platform]
        ICE[(Iceberg/S3)]
        OLAP[(ClickHouse)]
    end
    subgraph OBS[Observability]
        LF[Langfuse + OTel]
        AUDIT[(Audit Log)]
    end
    CH --> GW --> ID --> BFF
    BFF --> CORE
    BFF --> SVC
    BFF --> AI
    CORE --> KAFKA
    SVC --> KAFKA
    KAFKA --> RISK
    KAFKA --> DATA
    AI --> RAG --> VEC & GRAPH
    AI --> VLLM
    RISK --> GUARD
    BFF --> GUARD
    RISK --> AUDIT
    AI --> LF
```

## Data flow (payment)

1. Customer initiates payment (mobile).
2. BFF validates session (passkey) → calls Payments service (Go).
3. Fraud scorer (GNN/GBM) scores in <150ms.
4. If clear → core ledger posts; event emitted to Kafka.
5. If ambiguous → warm-path agent (LangGraph) produces verdict → HITL if needed.
6. Every step → Langfuse trace + audit event.

## Key principles
- Event-driven; AI as first-class; fail-closed guardrails; audit-ready.