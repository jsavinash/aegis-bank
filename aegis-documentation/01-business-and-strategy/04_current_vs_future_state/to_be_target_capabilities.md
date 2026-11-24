# To-Be Target Capabilities

## Target capabilities

| Capability | Target state | Enabled by |
|---|---|---|
| **Real-time ledger** | Event-driven core; every txn emits an event | Core banking + Redpanda/Kafka + Flink |
| **AI fraud engine** | Sub-150ms GNN/GBM scoring + agentic triage | Python ML + vLLM warm path |
| **Agentic AML** | Multi-agent investigation + SAR drafting + HITL | LangGraph + Temporal + GraphRAG |
| **Conversational banking** | Agentic assistant (voice/chat) with human escalation | LangGraph + LlamaIndex + MCP |
| **Digital onboarding** | <10 min, automated KYC/KYB | Docling + identity + workflow agent |
| **Credit decisioning** | ML + LLM copilot, human sign-off, explainable | XGBoost + LangGraph + vLLM |
| **Regulatory intelligence** | Continuous obligation→control mapping, gap register | GraphRAG + Graphiti temporal KG |
| **Open banking** | Aggregation + payment initiation + consent | PSD2/3 APIs + consent service |
| **Accessibility** | WCAG 2.2 AA everywhere | Design system + axe-core CI |
| **Audit-ready AI** | Every decision traceable (data/model/policy/human) | Langfuse + OTel + audit log |

## Future-state flow

```
Customer → Channel (RN/Next.js/Voice)
        → Edge (Kong/WAF, Keycloak passkeys)
        → BFF (GraphQL/REST)
        → Domain services (Go) + Core ledger (bought)
        → Event bus (Kafka) → AI plane (fraud, AML, agents) + Data (Iceberg/ClickHouse)
        → Guardrails + Audit + Langfuse
```

## Success criteria

Each capability must pass its **eval gate** (offline regression + online judge) and satisfy **security/compliance review** before production. No AI capability ships without HITL where high-consequence.