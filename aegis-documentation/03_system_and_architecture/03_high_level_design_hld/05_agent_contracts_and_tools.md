# Agent Contracts and Tools
> **Scope:** all 37 feature-catalog sections + AI use cases (as features) are in scope per [`in_scope_and_out_of_scope.md`](../../01-business-and-strategy/05_scope_and_boundaries/in_scope_and_out_of_scope.md); out of scope: physical branches, proprietary payment scheme, own core-ledger build.

## Agent contracts

Each agent is a typed unit with a defined input/output schema (Pydantic) and a role. Agents run as LangGraph nodes with a Postgres checkpointer; each has a **version, timeout, retry policy, and token budget**.

| Agent | Role | Input | Output |
|---|---|---|---|
| **Evidence Gatherer** | Collect evidence | Alert | Evidence bundle |
| **Typology Analyst** | Identify ML typologies | Evidence | Hypotheses + confidence |
| **Network Analyst** | Map entity graph | Entities | Neighbourhood |
| **Narrative Writer** | Draft SAR | Evidence + hypotheses | Grounded narrative + citations |
| **QA Reviewer** | Score defensibility | Narrative | Approved / needs-review |
| **Risk Analyst** | Warm-path fraud verdict | Risk object + context | Decision + rationale |
| **Gap Analyst** | Obligation→control mapping | Obligation + controls | Gap register |

## Tool contracts (MCP-style)

| Tool | Roles | Write? |
|---|---|---|
| `get_transactions` | evidence, risk | No |
| `get_kyc` | evidence | No |
| `screen_watchlist` | evidence | No |
| `query_graph_neighbourhood` | network | No |
| `get_customer_360` | risk | No |
| `search_obligations` | compliance | No |
| `create_case` | evidence, network | **Yes** (approval required) |
| `add_case_note` | narrative, qa | No |
| `file_sar` | narrative | **Yes** (approval required) |
| `freeze_account` | risk | **Yes** (approval required) |
| `escalate_to_human` | all | No |

## Tool governance

- **Least privilege:** each agent can call only its allowed tools.
- **Guardrail gate:** every tool call passes through NeMo tool-call rail (role + write-approval).
- **Tool-result screening:** every result treated as untrusted (injection defence).
- **Idempotency:** write tools keyed by run ID.
- **Schema versioning + timeouts + rate limits;** every call audited.

## Orchestration & resilience

- **LangGraph** for agent graphs; **Temporal** for durable, crash-proof runs (waits days for HITL).
- **Retries** with backoff; **timeouts** per node; **dead-letter** for failed runs.
- **Two-pass structured output** where an agent must act and emit strict JSON.

## Human escalation

When QA confidence < threshold, or a guardrail fires, the run pauses for human review (Temporal `interrupt()`), resuming after approval.

## Observability & audit

- Every model/tool/retrieval/guardrail step is an OTel span (Langfuse).
- Immutable audit of inputs, model/prompt/tool versions, output, and human approver.
