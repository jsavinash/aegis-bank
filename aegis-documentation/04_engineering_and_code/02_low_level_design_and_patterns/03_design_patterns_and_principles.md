# Design Patterns and Principles

## Patterns used

| Pattern | Use |
|---|---|
| **Strangler fig** | Incrementally replace legacy core |
| **Saga** | Distributed transactions (payments) |
| **Transactional outbox** | Reliable event publishing |
| **CQRS** | Separate reads (queries) from writes (commands) |
| **Event sourcing** | Audit-grade history (where warranted) |
| **Repository** | Data access abstraction |
| **Adapter** | Anti-corruption layer for core/vendor |
| **Strategy** | Pluggable models/strategies (risk routing) |
| **Chain of responsibility** | Guardrail pipeline |
| **Circuit breaker / bulkhead** | Resilience |
| **Idempotency** | Safe retries |
| **HITL interrupt** | Human-in-the-loop |

## Principles

- **SOLID** — single responsibility, dependency inversion.
- **Domain-driven design** — ubiquitous language, bounded contexts.
- **Twelve-factor** — config in env, stateless processes.
- **Contract-first** — API before implementation.
- **Fail closed** — safety over availability for AI actions.
- **Least privilege** — minimal permissions everywhere.

## Anti-patterns to avoid

- **Distributed monolith** (services coupled by shared DB).
- **God objects** / long-lived global state.
- **Autonomous AI** without HITL for regulated actions.
- **Hardcoded products** instead of config.