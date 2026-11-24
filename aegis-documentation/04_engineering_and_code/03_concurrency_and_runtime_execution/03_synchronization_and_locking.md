# Synchronization and Locking

## Principles

- **Prefer message passing** (channels) over shared locks.
- **Keep critical sections small.**
- **Always use timeouts** on locks.
- **Avoid deadlocks** — consistent lock ordering.

## Go

- **`sync.Mutex` / `sync.RWMutex`** for short critical sections.
- **`context`** for cancellable waits.
- **Atomic operations** (`sync/atomic`) for counters.
- **Avoid** copying mutexes.

## Python

- **`asyncio.Lock`** for async critical sections.
- **`asyncio.Semaphore`** to bound concurrency.
- **Avoid** blocking locks in async code.

## Distributed locking

| Need | Solution |
|---|---|
| **Cross-service lock** | Redis/etcd with TTL + fencing token |
| **Idempotency** | Idempotency keys (no lock needed) |
| **Optimistic concurrency** | Version/ETag on records |

## Deadlock avoidance

- **Lock ordering** documented.
- **Try-lock with timeout** where possible.
- **Detect** via tracing / deadlock detector in tests.