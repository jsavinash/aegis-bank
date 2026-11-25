# Memory Management and Resource Pools

## Python

- **Bounded queues** to cap memory under load.
- **Streaming** large results; **generators** for large datasets.
- **Object pooling** for expensive objects (DB connections, HTTP clients).
- **GC tuning** for long-running workers.

## Java

- **Object pooling** (caches/pools) for temporary buffers.
- **Bounded virtual threads** to avoid explosion.
- **Preallocate** buffers where size is known.
- **Avoid leaks** — try-with-resources; always close resources.

## Resource pools

| Resource | Pool |
|---|---|
| **DB connections** | SQLAlchemy pool / HikariCP |
| **HTTP clients** | httpx / `java.net.http.HttpClient` |
| **GPU / model** | Inference server (vLLM) with bounded concurrency |
| **Thread/worker** | Fixed-size worker pools |

## Cache & eviction

- **No unbounded caches** — TTL + max size (**LRU/LFU**).
- **Bounded context windows** — retrieval budgets; **chunking** for large docs.

## K8s memory

- **Requests/limits** per pod; **OOMKilled** alerts; **HPA** on memory where relevant.

## Leak detection & profiling

- Track memory, thread/virtual-thread counts, queue depths; **profiling** (Pyroscope/async-profiler).
- Alert on **monotonic growth** (leak) and **pool saturation**.
