# API Contracts and Versioning

## API styles

| API | Style | Consumers |
|---|---|---|
| **Public/partner** | REST + OpenAPI 3.1 | Partners, open banking |
| **Client** | GraphQL (BFF) | Mobile, web |
| **Internal** | gRPC / REST | Service-to-service |
| **Events** | AsyncAPI + Kafka | Event consumers |

## Versioning

- **URI versioning** (`/v1/...`) for public REST; **header versioning** where needed.
- **GraphQL:** schema evolution (deprecate, don't break).
- **AsyncAPI:** schema registry with versioned subjects.
- **Deprecation policy:** N-2 support; `Sunset` headers.

## Contract governance

- **OpenAPI/AsyncAPI** as source of truth; **API catalogue**.
- **Spectral** linting in CI (breaking-change gate); **Pact** contract tests.
- **Generated clients** + **mock servers** from contracts.

## Error model

- **RFC 7807** problem+json: `{ type, title, status, detail, instance }`.
- Stable error codes; field-level validation errors.

## Pagination, filtering & idempotency

- **Cursor pagination** for lists; **filter/sort** via query params.
- **Idempotency-Key** on all writes; **rate-limit** headers (`RateLimit-*`).

## Webhooks

- **Signed** (HMAC) payloads; **retries** with backoff; **DLQ**; replay by event id.

## Example endpoints

`
POST /v1/payments → payment
GET /v1/accounts/{id} → account
POST /v1/cards/{id}/freeze → freeze
POST /v1/lending/applications→ application
POST /v1/assistant/query → assistant (streaming)
`

## Security

- OAuth2 / OIDC; scopes per endpoint; **mTLS** for internal.
- Rate limiting per client; idempotency keys on writes.
