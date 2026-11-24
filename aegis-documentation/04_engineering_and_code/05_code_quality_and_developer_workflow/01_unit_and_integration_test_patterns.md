# Unit and Integration Test Patterns

## Test pyramid

| Level | Scope | Tooling |
|---|---|---|
| **Unit** | Pure functions, models, guardrails | pytest, Vitest |
| **Integration** | DB, APIs, services | pytest + Testcontainers, Supertest |
| **Contract** | API/event schemas | Schemathesis, Pact |
| **E2E** | Critical journeys | Playwright, Detox |

## Patterns

- **Arrange-Act-Assert.**
- **Fixtures** for test data (synthetic, no PII).
- **Fake LLMs** (deterministic mock) for agent tests.
- **Property-based tests** (Hypothesis) for models/guardrails.
- **Table-driven tests** (Go).

## AI test patterns

- **Eval-gated CI** — regression suite blocks merges.
- **Golden datasets** for AML/fraud/credit.
- **LLM-as-judge** (calibrated) + Ragas.
- **Determinism** — pin model + prompt version + seed.

## Integration

- **Testcontainers** for Postgres/Qdrant/Neo4j.
- **Contract tests** against schemas.
- **Idempotency tests** (replay).

## Non-negotiables

- No real PII in tests.
- Every bug fix gets a regression test.
- Coverage gates on Tier 0 code.