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

## AI-specific testing

- **Eval-gated CI:** no prompt/model/graph change merges without passing the tagged regression suite.
- **LLM-as-judge** (calibrated, bias-controlled, pinned) + **Ragas** metrics.
- **Golden datasets** for AML/fraud/credit.
- **Shadow testing** in production.

## A/B experiments

- UX/pricing only; never regulated disclosures.
- Guardrail metrics (trust, fraud, accessibility) block regressions.

## Test data

- **Synthetic** data (Faker + LLM-generated scenarios) — no real PII in tests.
- **Anonymised** production samples for evals.