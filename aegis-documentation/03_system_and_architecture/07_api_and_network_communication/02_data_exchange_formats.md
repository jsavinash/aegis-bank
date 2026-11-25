# Data Exchange Formats

## Formats

| Format | Use |
|---|---|
| **JSON** | REST APIs, internal messages |
| **Protobuf / Avro** | Event streams (Kafka), gRPC |
| **ISO 20022** | Payments messaging (interbank) |
| **OpenAPI 3.1** | API contracts |
| **AsyncAPI** | Event contracts |
| **CloudEvents** | Event envelope (id, source, type, time) |
| **CSV / Parquet** | Batch data, analytics |

## Standards

- **ISO 8601 / RFC 3339** timestamps (UTC); **ISO 4217** currencies; **ISO 3166** countries.
- **ISO 20022** for payment messages (structured, rich data).
- **UTF-8** encoding; **E.164** phone numbers; **JSON Schema** validation.

## Money & precision

- **Decimal** (never float) for amounts; store **minor units + currency** or decimal string.
- Explicit **rounding** rules; no binary floating point for money.

## Schema evolution

- **Backward-compatible** changes only (add optional fields).
- **Registry** enforces compatibility.
- **Producers own** schemas; consumers tolerate unknown fields.

## Serialisation

- **Protobuf** for high-throughput internal/event traffic; **JSON** for external APIs.
- **Parquet** for columnar analytics; **compression** (gzip/zstd) on wire/batch.
- **PII** minimised/redacted in payloads and logs.
