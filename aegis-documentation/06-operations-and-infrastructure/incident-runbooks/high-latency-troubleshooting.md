# Runbook: High Latency Troubleshooting

## Symptoms
- p99 latency above SLO (balance, payments, assistant).

## Steps

1. **Localise** — which service/endpoint/region? (OTel traces, RED metrics).
2. **Check dependencies** — DB, Redis, Kafka lag, LLM inference, Qdrant/Neo4j.
3. **Identify saturation** — CPU, memory, GPU, connection pools, queue depth.
4. **Recent changes?** — correlate with deploys/flags.
5. **Mitigate:**
   - Scale horizontally (HPA / manual).
   - Circuit-break a failing dependency.
   - Shed low-priority traffic.
   - Roll back recent deploy / flip feature flag.
6. **Verify** latency returns to SLO.
7. **Communicate** if customer-impacting.
8. **Post-mortem.**

## Common causes

| Cause | Fix |
|---|---|
| **LLM queue** | Scale vLLM; route to cheaper model; cache |
| **DB slow query** | Add index; kill long query; read replica |
| **Kafka lag** | Scale consumers; check poison messages |
| **Hot partition** | Re-partition; dedicated resources |
| **Deploy regression** | Roll back |

## Prevention

- Load tests before release; capacity headroom; autoscaling; caching.