# Synchronization and Locking

## Principles

- **Prefer message passing** (channels) over shared locks.
- **Keep critical sections small**; **always use timeouts** on locks.
- **Avoid deadlocks** — consistent lock ordering.

## Java

- **`synchronized` / `ReentrantReadWriteLock`** for short critical sections.
- **`ThreadLocal` context / virtual threads** for cancellable waits.
- **Atomic operations** (`java.util.concurrent.atomic`) for counters.
- **Avoid** lock object copying.

## Python

- **`asyncio.Lock`** for async critical sections; **`asyncio.Semaphore`** to bound concurrency.
- **Avoid** blocking locks in async code.

## Lock-free & atomics

- **CAS / atomic** for counters/flags; **immutable** data; **read-write locks** for read-heavy.

## Distributed locking

| Need | Solution |
|---|---|
| **Cross-service lock** | Redis/etcd with TTL + **fencing token** |
| **Idempotency** | Idempotency keys (no lock needed) |
| **Optimistic concurrency** | Version/ETag on records |

- **Lease renewal** + **fencing** to prevent split-brain; **owner tokens**.

## Deadlock avoidance

- **Lock ordering** documented; **try-lock with timeout**; **deadlock detector** in tests.

## Observability & testing

- **Lock wait/hold metrics**; **stress tests** for races/deadlocks.