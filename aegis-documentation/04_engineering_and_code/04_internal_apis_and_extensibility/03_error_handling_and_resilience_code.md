# Error Handling and Resilience (Code)

## Principles

- **Fail fast** at boundaries; **fail safe** for money.
- **Typed errors** with context; no swallowed exceptions.
- **Retries only for idempotent** operations.
- **Never log secrets/PII.**

## Error taxonomy

| Error | Handling |
|---|---|
| **Validation** | Return 4xx with details |
| **Auth** | 401/403, no leakage |
| **Not found** | 404 |
| **Conflict** | 409 (idempotency) |
| **Upstream timeout** | Retry (idempotent) or fail |
| **Guardrail block** | Block + audit + alert |
| **Unknown** | Log trace_id, return 500 |

## Resilience code patterns

```python
@retry(stop=stop_after_attempt(3), wait=wait_exponential())
async def call_tool(...): ...

try:
    result = await llm.structured(...)
except BudgetExceeded:
    raise FallbackToHuman(...)
```

## Circuit breaker

- Open on repeated failures; half-open probes; closed on success.

## Idempotency

- Every write takes an idempotency key; dedup on server.

## Observability

- Every error carries a `trace_id`; structured logs; metrics.

## AI-specific

- **Two-pass** to avoid tool-suppression failures.
- **Citations required**; ungrounded output rejected.
- **Fail-closed** guardrails.