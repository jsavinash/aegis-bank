# FinOps and Cost Optimization

## Cost drivers

| Driver | Mitigation |
|---|---|
| **LLM inference** | Model routing (cheap for easy steps); caching; budgets; self-host open weights |
| **GPU** | Reserved/committed capacity; spot for batch; right-size nodes |
| **Vector/graph** | Tiered storage; replica tuning |
| **Data egress** | Minimise cross-region; compress |
| **Observability** | Sample traces; tiered retention |

## FinOps practices

- **Tagging & showback** per team/product.
- **Budgets** with alerts (per-step LLM budget enforced).
- **Unit economics** — cost per payment, per KYC, per query.
- **Reserved instances / savings plans** for steady state.
- **Autoscaling** to avoid idle capacity.

## Model routing economics

| Step | Tier | Rationale |
|---|---|---|
| **Extraction/classification** | fast (cheap) | High volume, low difficulty |
| **Reasoning/analysis** | reasoning (expensive) | Low volume, high value |
| **Embedding/rerank** | self-hosted | Predictable cost |

## Optimisation loops

- **Continuous eval** ensures cheaper models still pass quality gates.
- **Right-sizing** based on utilisation.
- **Chargeback** to product teams for accountability.