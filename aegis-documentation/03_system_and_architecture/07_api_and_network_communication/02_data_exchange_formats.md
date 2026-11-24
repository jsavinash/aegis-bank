# Data Exchange Formats

## Formats

| Format | Use |
|---|---|
| **JSON** | REST APIs, internal messages |
| **Protobuf / Avro** | Event streams (Kafka), gRPC |
| **ISO 20022** | Payments messaging (interbank) |
| **OpenAPI 3.1** | API contracts |
| **AsyncAPI** | Event contracts |
| **CSV / Parquet** | Batch data, analytics |

## Standards

- **ISO 8601** for timestamps (UTC).
- **ISO 4217** for currencies.
- **ISO 3166** for countries.
- **ISO 20022** for payment messages (structured, rich data).

## Schema evolution

- **Backward-compatible** changes only (add optional fields).
- **Registry** enforces compatibility.
- **Producers own** schemas; consumers tolerate unknown fields.

## Serialisation

- **Protobuf** for high-throughput internal/event traffic.
- **JSON** for external APIs (human-readable, tooling-friendly).
- **Parquet** for columnar analytics.