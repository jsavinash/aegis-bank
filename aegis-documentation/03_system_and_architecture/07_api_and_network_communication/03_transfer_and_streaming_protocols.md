# Transfer and Streaming Protocols

## Protocols

| Use | Protocol |
|---|---|
| **Request/response** | HTTP/2, gRPC |
| **Server-sent events** | SSE (assistant token streaming, balance updates) |
| **WebSocket** | Real-time chat, live updates |
| **Event streaming** | Kafka (binary) |
| **File transfer** | S3 (multipart), SFTP (legacy) |

## Streaming

- **LLM token streaming** via SSE to the assistant UI.
- **Live balance** via WebSocket/SSE.
- **Backpressure:** client-driven; server honours timeouts.

## gRPC

- **Internal services** use gRPC with Protobuf for low latency.
- **Streaming RPCs** for long-lived interactions.
- **Deadline propagation** across calls.

## Resilience

- **Retries** with backoff (idempotent only).
- **Timeouts** on all calls.
- **Circuit breakers** + bulkheads.
- **Keep-alive / heartbeat** for long connections.