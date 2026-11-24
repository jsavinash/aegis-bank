# Memory Management and Resource Pools

## Python

- **Bounded queues** to cap memory under load.
- **Streaming** large results (don't load everything).
- **Generators** for large datasets.
- **Object pooling** for expensive objects (DB connections, HTTP clients).
- **GC tuning** for long-running workers.

## Java

- **Object pooling** (caches/pools) for temporary buffers.
- **Bounded virtual threads** to avoid explosion.
- **Preallocate** buffers where size is known.
- **Avoid leaks** — always close resources; use try-with-resources.

## Resource pools

| Resource | Pool |
|---|---|
| **DB connections** | SQLAlchemy pool / `pgxpool` |
| **HTTP clients** | httpx / `http.Client` |
| **GPU / model** | Inference server (vLLM) with bounded concurrency |
| **Thread/worker** | Fixed-size worker pools |

## Memory safety

- **No unbounded caches** — TTL + max size (LRU).
- **Bounded context windows** — retrieval budgets.
- **Chunking** for large documents.

## Monitoring

- Track memory, thread/virtual-thread counts, queue depths.
- Alert on leaks (monotonic growth).