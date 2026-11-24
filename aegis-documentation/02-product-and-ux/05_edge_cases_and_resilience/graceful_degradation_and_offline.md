# Graceful Degradation and Offline

## Principles

- **Money movement requires connectivity** — never queue a payment offline and "send later" without explicit confirmation.
- **Read-only works offline** — balances, statements, and history (cached) remain viewable.
- **Degrade, don't fail** — if the AI assistant is down, fall back to traditional UI + human support.

## Degradation matrix

| Capability | If unavailable | Fallback |
|---|---|---|
| Agentic assistant | LLM/rag down | Traditional UI + phone support |
| Fraud hot path | ML service down | Conservative rules + manual review (fail safe) |
| Open banking aggregation | Provider down | Show last-known balances |
| Real-time notifications | Push down | In-app message centre |
| Biometric login | Sensor down | Passkey / OTP fallback |

## Offline behaviour

- **Cached balances** shown with a "last updated" timestamp.
- **Cached statements** available offline.
- **Draft payments** saved locally but not submitted until online + confirmed.
- **Queue-only for low-risk reads**; never for writes.

## Consistency

- **Optimistic UI** for freeze/goal updates with rollback.
- **Idempotency keys** so retries never double-charge.
- **Conflict resolution** — last-write-wins with audit, or explicit prompt for money.