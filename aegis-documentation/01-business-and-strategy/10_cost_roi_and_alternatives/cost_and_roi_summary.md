# Cost and ROI Summary

## Cost model (order-of-magnitude)

| Bucket | Notes | Relative cost |
|---|---|---|
| **Core banking licence** | Per-account / per-transaction pricing | High (fixed) |
| **Cloud infra** | EKS, GPU (vLLM), storage, streaming | Medium |
| **Data licences** | KYC/AML/fraud feeds | Medium |
| **AI inference** | Routed; self-host for residency | Medium (controllable) |
| **Engineering** | Platform, AI, channels, security | High (people) |
| **Compliance & audit** | Pen-test, model validation | Low–medium |

## ROI worked example (KYC/reconciliation workflow)

- 3,000 cases/month × 18 min analyst time = 900 hrs/month.
- With AI first-pass: ~4 min/case → 200 hrs/month.
- **Saved: 700 hrs/month** ≈ $28k–$42k/month (loaded $40–60/hr).
- Against a $35k–$80k build + low-thousands/month run → **payback 60–120 days**.

## Value pools (annual, illustrative — conservative)

> These are conservative figures for the ROI case; the aspirational targets in [`03_business_goals_and_kpis.md`](../03_business_goals_and_kpis.md) are higher (e.g., contact deflection 25–40%, AML analyst time per alert down ~70%).

| Use case | Value driver |
|---|---|
| Fraud | 5–10% fraud-loss reduction (bps of volume) |
| Servicing | 10–20% contact-deflection efficiency |
| AML | 15% compliance manual-work reduction |
| Underwriting | Faster decisions, thin-file expansion |

## Measurement discipline

Track **business metrics** (fraud-loss bps, cost per contact, alert-to-filing), compare before/after, attribute conservatively. Avoid lab accuracy claims without business outcomes.

> A detailed model is provided in [`financial_model.csv`](./financial_model.csv) (low/high annual cost, value pools, and payback).