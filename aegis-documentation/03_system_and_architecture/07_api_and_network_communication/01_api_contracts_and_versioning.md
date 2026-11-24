# API Contracts and Versioning

## API styles

| API | Style | Consumers |
|---|---|---|
| **Public/partner** | REST + OpenAPI 3.1 | Partners, open banking |
| **Client** | GraphQL (BFF) | Mobile, web |
| **Internal** | gRPC / REST | Service-to-service |
| **Events** | AsyncAPI + Kafka | Event consumers |

## Versioning

- **URI versioning** (`/v1/...`) for public REST.
- **GraphQL:** schema evolution (deprecate, don't break).
- **AsyncAPI:** schema registry with versioned subjects.
- **Deprecation policy:** N-2 support; sunset headers.

## Contract governance

- **OpenAPI/AsyncAPI** as source of truth.
- **Spectral** linting in CI (breaking-change gate).
- **Generated clients** from contracts.
- **Mock servers** from contracts for parallel dev.

## Example endpoints

```
POST /v1/payments            → payment
GET  /v1/accounts/{id}       → account
POST /v1/cards/{id}/freeze   → freeze
POST /v1/lending/applications→ application
POST /v1/assistant/query     → assistant (streaming)
```

## Security

- OAuth2 / OIDC; scopes per endpoint.
- Rate limiting per client.
- Idempotency keys on writes.