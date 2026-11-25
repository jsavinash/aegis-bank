# Coding Conventions and Linting

## Python (AI plane)

- **Style:** ruff (format + lint), line length 100.
- **Typing:** mypy (strict where practical); Pydantic for models.
- **Async:** async/await for I/O; no blocking calls in async paths.
- **Errors:** typed exceptions; no bare except.
- **Docstrings:** public APIs only.

## Java (transactional)

- **Style:** Checkstyle / Spotless, 4-space indent, 120 line length.
- **Errors:** checked exceptions; avoid swallowing.
- **Concurrency:** prefer `java.util.concurrent` / `CompletableFuture`; avoid shared mutable state.
- **Frameworks:** Spring Boot / Quarkus conventions; dependency injection.

## TypeScript (channels)

- **Style:** eslint + prettier.
- **Typing:** strict TypeScript.
- **Framework:** React/Next.js conventions.
- **Accessibility:** lint rules for a11y.

## Naming

- `snake_case` (Python), `camelCase`/`PascalCase` (TS/Java); **no abbreviations** in public APIs.
- Modules `<domain>.<capability>`; tests mirror source path.

## Logging & errors

- **Structured logs** (JSON) with correlation/run IDs; **no PII/secrets**.
- **Typed errors**; map to RFC 7807 at the edge; **no stack traces to clients**.

## Testing

- **Unit + integration** per module; **coverage gate** on Tier 0; **deterministic** tests (seeded).

## Security

- **Validate input** at boundaries; **parameterised queries**; **no secrets**; **least privilege**.

## Dependencies

- **Pin versions**; **SCA** in CI; **no new dep** without review; prefer stdlib.

## General

- **No secrets** in code; **no cross-service DB access**; **contract-first** (update `api/` first).
- **Review:** 1+ approver; 2 for Tier 0 / AI; **review checklist** (tests, docs, security, a11y).

## Pre-commit

- ruff, mypy, eslint, tsc, secret scan, contract lint.
