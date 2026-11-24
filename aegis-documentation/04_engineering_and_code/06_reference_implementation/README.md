# Reference Implementation — Aegis Core (Illustrative)

A minimal, runnable reference implementation of the Aegis domain: entities, enums, value objects, a fraud-scoring strategy, a case service with concurrency control, and a model router. This mirrors the structure of the original template but with **banking** semantics.

## Layout

```
src/
  exceptions/   typed domain errors
  models/       entities, enums, value objects
  services/     case_service, fraud_service
  strategies/   risk_strategy, model_router
tests/
  unit/         service + state-transition tests
  concurrency/  locking tests
```

## Run

```bash
pip install -r requirements.txt
pytest -q
```

## What it demonstrates

- **Value objects** (`Money`, `RiskScore`) with validation.
- **State machine** for a case (`CaseStatus` transitions).
- **Concurrency control** (a per-case lock) so concurrent updates are safe.
- **Strategy pattern** for risk scoring (rules vs model).
- **Model routing** (cheap vs reasoning tiers).

> This is illustrative, not production. The production AI plane is the `aegis/` package at the repo root.