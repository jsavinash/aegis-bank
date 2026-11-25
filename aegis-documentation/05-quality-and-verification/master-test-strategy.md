# Master Test Strategy

## Objectives

Prove Aegis is **correct, secure, compliant, accessible, and reliable** before and after launch.

## Test levels

| Level | Scope | Tooling | Gate |
|---|---|---|---|
| **Unit** | Functions, models, guardrails | pytest, Vitest | Every PR |
| **Integration** | Services, DB, APIs | Testcontainers, Supertest | Every PR |
| **Contract** | API/event schemas | Schemathesis, Pact | Every PR |
| **E2E** | Critical journeys | Playwright, Detox | Pre-release |
| **Accessibility** | WCAG 2.2 AA | axe-core + manual | Every PR |
| **Performance** | Load/latency | k6, Gatling | Pre-release |
| **Security** | SAST/DAST/pentest | Semgrep, ZAP, external | Pre-release |
| **AI eval** | Prompts/models/RAG | Ragas, Braintrust | Every PR (blocking) |

## Test data

- **Synthetic** (Faker + LLM-generated) — never real PII.
- **Anonymised** production samples for evals.
- **Golden datasets** for AML/fraud/credit.

## Environments

- **dev** — fast feedback, mock dependencies.
- **staging** — production-like, integration + E2E.
- **prod** — canary + shadow + online eval.

## Entry/exit criteria

- **Enter:** code complete, contracts updated.
- **Exit:** all gates green, no critical vulns, eval thresholds met, a11y pass.

## AI-specific

- **Eval-gated CI** blocks merges.
- **Shadow testing** in prod.
- **Drift monitors** + champion/challenger.
