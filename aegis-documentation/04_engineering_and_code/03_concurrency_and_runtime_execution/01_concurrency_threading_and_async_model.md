# Concurrency, Threading and Async Model

## Python (AI plane)

- **Async/await** for I/O (LLM, DB, tools).
- **No blocking calls** in async paths (run CPU work in executors).
- **Structured concurrency** (task groups) where possible.
- **Bounded concurrency** — semaphores around expensive resources (GPU, DB).
- **Backpressure** — queue-based load levelling.

## Java (transactional)

- **Virtual threads** + `CompletableFuture` for fan-out/fan-in.
- **ExecutorService / structured concurrency** for coordination; prefer over shared mutable state.
- **Context propagation** (thread-local / virtual-thread local) for cancellation/deadlines.
- **Worker pools** with bounded size.

## Patterns

| Pattern | Use |
|---|---|
| **Fan-out / fan-in** | Parallel evidence gathering |
| **Pipeline** | Streaming transformations |
| **Worker pool** | Bounded task execution |
| **Bulkhead** | Isolate failures |
| **Circuit breaker** | Protect dependencies |

## Cancellation & deadlines

- **Deadlines** propagated across async hops; **cancellation** on timeout.
- **No unbounded waits**; every await has a timeout.

## Pools & backpressure

- **Pool sizing** by Little's Law; **bounded queues**; **load shedding** when saturated.

## Race & deadlock avoidance

- **Immutable state**; **no shared mutable** across threads; **consistent lock ordering**; **timeouts** on locks.

## Consistency

- **Idempotency** for retries; **transactional outbox** for events; **optimistic concurrency** (version numbers).

## Observability

- **Trace context** across async tasks; spans for fan-out; **pool/queue metrics**.

## Testing concurrency

- Deterministic tests with controlled scheduling; **stress/race tests** (JUnit 5 + Testcontainers).
