# Testing Strategy and A/B Experiments

## Test pyramid

| Level | Scope | Tooling |
|---|---|---|
| **Unit** | Functions, models, guardrails | pytest, Jest/Vitest |
| **Integration** | Services, DB, APIs | pytest, Testcontainers, Supertest |
| **Contract** | API/event schemas | Schemathesis, Pact |
| **E2E** | Critical journeys | Playwright, Detox |
| **Accessibility** | WCAG 2.2 AA | axe-core, manual screen-reader |
| **Performance** | Load, latency | k6, Gatling |
| **AI eval** | Prompts/models/RAG | Ragas, Braintrust (CI gate) |
| **Security** | SAST/DAST/pentest | Semgrep, OWASP ZAP |

## CI stages & environments

- **Stages:** lint → unit → contract → integration → e2e → eval → security → perf (pre-prod).
- **Ephemeral preview envs** per PR; **staging** mirrors prod; **coverage gates** on Tier 0.

## AI-specific testing

- **Eval-gated CI:** no prompt/model/graph change merges without passing the tagged regression suite.
- **LLM-as-judge** (calibrated, bias-controlled, pinned) + **Ragas** metrics.
- **Golden datasets** for AML/fraud/credit; **shadow testing** in production.

## A/B experiments

- **Hypothesis → sample size → sequential testing**; **guardrail metrics** (trust, fraud, accessibility) block regressions.
- UX/pricing only; **never** regulated disclosures; **feature flags** for rollout/rollback.

## Test data

- **Synthetic** data (Faker + LLM scenarios) — no real PII in tests.
- **Anonymised** production samples for evals.

## Flaky & maintenance

- **Quarantine** flaky tests; track and fix; **flake budget** per suite.
