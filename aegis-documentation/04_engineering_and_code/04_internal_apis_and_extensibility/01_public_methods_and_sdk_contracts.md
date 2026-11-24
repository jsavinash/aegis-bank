# Public Methods and SDK Contracts

## Service SDKs

Every service exposes a typed SDK (generated from OpenAPI/AsyncAPI) for consumers.

| SDK | Language | Generated from |
|---|---|---|
| **payments-sdk** | Java, Python, TS | OpenAPI |
| **accounts-sdk** | Java, TS | OpenAPI |
| **events** | Python, Java | AsyncAPI |

## Internal APIs

- **Versioned** (`/v1`).
- **Contract-first** — schema is the source of truth.
- **Backward-compatible** changes only.
- **Errors** follow a standard envelope (`{code, message, details, trace_id}`).

## Agent SDK

Agents expose typed tools via MCP; the `ToolRegistry` is the contract. Each tool declares `input_schema`, `roles`, and `write` flag.

## SDK design

- **Idiomatic** per language.
- **Typed** (Pydantic / Java records / TS interfaces).
- **Retry/timeout** built in for idempotent calls.
- **Auth** handled via token provider.

## Deprecation

- **N-2** support window.
- **Sunset** headers + changelog.