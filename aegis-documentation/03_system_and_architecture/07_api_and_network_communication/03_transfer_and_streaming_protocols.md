# Transfer and Streaming Protocols

## Protocols

| Use | Protocol |
|---|---|
| **Request/response** | HTTP/2–3, gRPC |
| **Server-sent events** | SSE (assistant token streaming, balance updates) |
| **WebSocket** | Real-time chat, live updates |
| **Event streaming** | Kafka (binary) |
| **File transfer** | S3 (multipart), SFTP (legacy) |

## Streaming

- **LLM token streaming** via SSE to the assistant UI.
- **Live balance** via WebSocket/SSE.
- **Reconnect** with `Last-Event-ID`; **heartbeat**; **message ordering** per stream.
- **Backpressure:** client-driven; server honours timeouts.

## gRPC

- **Internal services** use gRPC with Protobuf for low latency.
- **Streaming RPCs** for long-lived interactions.
- **Deadline propagation** across calls; **connection pooling** + **load balancing**; **TLS**.

## File transfer

- **Multipart** upload/download (S3); **resume** + **checksums**; **size limits**; **AV scan**.

## Resilience

- **Retries** with backoff (idempotent only); **timeouts** on all calls.
- **Circuit breakers** + bulkheads; **keep-alive / heartbeat** for long connections.

## Security

- **TLS 1.3**; auth on every connection; **rate limiting**; no PII in URLs.
