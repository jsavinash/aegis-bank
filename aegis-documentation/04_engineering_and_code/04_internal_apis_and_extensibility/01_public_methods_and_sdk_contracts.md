# Public Methods and SDK Contracts

## Service SDKs

Every service exposes a typed SDK (generated from OpenAPI/AsyncAPI) for consumers.

| SDK | Language | Generated from |
|---|---|---|
| **payments-sdk** | Java, Python, TS | OpenAPI |
| **accounts-sdk** | Java, TS | OpenAPI |
| **events** | Python, Java | AsyncAPI |

## Internal APIs

- **Versioned** (`/v1`); **contract-first** (schema is source of truth); **backward-compatible** changes only.
- **Errors** follow a standard envelope (`{code, message, details, trace_id}`) — RFC 7807.

## Agent SDK

Agents expose typed tools via MCP; the `ToolRegistry` is the contract. Each tool declares `input_schema`, `roles`, and `write` flag.

## SDK design

- **Idiomatic** per language; **typed** (Pydantic / Java records / TS interfaces).
- **Retry/timeout** built in for idempotent calls; **auth** via token provider.
- **Pagination** (cursor) + **rate-limit** surfaced to callers.

## Error codes

- Stable codes (`PAYMENT_DECLINED`, `KYC_REQUIRED`); **no stack traces**; **trace_id** for support.

## Auth & idempotency

- **OAuth2/OIDC** scopes; **Idempotency-Key** on writes; **429** with `Retry-After`.

## Testing

- **Contract tests** (Pact) + **SDK smoke tests** per release.

## Deprecation

- **N-2** support window; **Sunset** headers + changelog.
