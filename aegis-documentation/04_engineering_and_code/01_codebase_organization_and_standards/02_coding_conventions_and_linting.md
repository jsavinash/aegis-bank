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

## General

- **No secrets** in code.
- **No direct DB access** across service boundaries.
- **Contract-first** — update `api/` before implementation.
- **Review:** 1+ approver; 2 for Tier 0 / AI.

## Pre-commit

- ruff, mypy, eslint, tsc, secret scan, contract lint.