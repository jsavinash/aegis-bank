# Experimentation and Feature Flags

## Feature flags

- **Purpose:** decouple deploy from release; enable canary and rollback.
- **Types:** release toggles, ops toggles, permission toggles, experiment toggles.
- **Storage:** centralised flag service (e.g., Unleash/LaunchDarkly) or in-house.
- **Lifecycle:** every flag has an owner and an expiry; stale flags are removed.

## Experimentation

- **A/B testing** for UX and pricing changes (not for regulated disclosures).
- **Guardrail metrics** — never ship a change that harms trust (fraud loss, complaints, accessibility).
- **Sequential testing** where appropriate; pre-register hypotheses.

## Experiment guardrails

| Guardrail | Rule |
|---|---|
| Fair lending | No experiments that create disparate impact |
| Accessibility | No regression below WCAG 2.2 AA |
| Security | No experiment weakens fraud/auth controls |
| Regulatory | Disclosures/terms not subject to silent A/B |

## Feature flag hygiene

- Flags default **off** in prod.
- Kill switch for every AI capability (revert to rules/human).
- Audit who changed which flag and when.