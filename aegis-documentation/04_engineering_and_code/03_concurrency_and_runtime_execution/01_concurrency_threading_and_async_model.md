# Concurrency, Threading and Async Model

## Python (AI plane)

- **Async/await** for I/O (LLM, DB, tools).
- **No blocking calls** in async paths (run CPU work in executors).
- **Structured concurrency** (task groups) where possible.
- **Bounded concurrency** — semaphores around expensive resources (GPU, DB).
- **Backpressure** — queue-based load levelling.

## Go (transactional)

- **Goroutines** + `errgroup` for fan-out/fan-in.
- **Channels** for coordination; prefer over shared memory.
- **`context.Context`** for cancellation/deadlines.
- **Worker pools** with bounded size.

## Patterns

| Pattern | Use |
|---|---|
| **Fan-out / fan-in** | Parallel evidence gathering |
| **Pipeline** | Streaming transformations |
| **Worker pool** | Bounded task execution |
| **Bulkhead** | Isolate failures |
| **Circuit breaker** | Protect dependencies |

## Consistency

- **Idempotency** for retries.
- **Transactional outbox** for events.
- **Optimistic concurrency** (version numbers) where appropriate.

## Testing concurrency

- Race detection (`go test -race`).
- Deterministic tests with controlled scheduling.