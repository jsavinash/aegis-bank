# UX to System State Mapping

Each user action maps to a system state and an API call. This ensures the UX is grounded in real system behaviour and that states are observable.

| UX action | System state | API / service | Notes |
|---|---|---|---|
| View balance | Account balance | `GET /accounts/{id}/balance` (core) | <200ms target |
| Send payment | Payment pending → settled | `POST /payments` (Java) | Idempotency key |
| Freeze card | Card status frozen | `POST /cards/{id}/freeze` | Instant |
| Apply for loan | Application draft → decision | `POST /lending/applications` | ML + LLM copilot |
| Create savings goal | Goal created | `POST /goals` | Rules engine |
| Chat with assistant | Conversation state | Agent (LangGraph) | MCP tools |
| Upload KYC doc | Document → verified | Docling + KYC workflow | Async |
| View credit score | Score snapshot | `GET /credit/score` | Bureau API |

## State-to-UX contract

- **Every state must be representable in the UI** — no silent background states.
- **Pending states must show progress** (payment status, KYC review).
- **Failure states must offer recovery** (retry, contact, escalate).
- **Optimistic UI** only where safe (freeze, goal update), with rollback on failure.

## Observability

Every action emits an OTel span and an audit event. Support can reconstruct "what happened" from the trace.
