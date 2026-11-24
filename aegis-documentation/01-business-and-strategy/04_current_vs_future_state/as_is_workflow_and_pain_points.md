# As-Is Workflow and Pain Points

## As-is (typical incumbent bank)

| Area | Current state | Pain point |
|---|---|---|
| **Onboarding** | Paper/branch + manual KYC; days | Drop-off, cost, poor experience |
| **Fraud** | Rules + static ML; high false positives | Customer friction, analyst overload |
| **AML** | Rules generate >90% false positives | Analysts buried; slow SAR filing |
| **Customer service** | Scripted IVR + human agents; business hours | Long waits, limited coverage |
| **Underwriting** | Manual document review; days | Slow decisions, thin-file exclusion |
| **Compliance** | Manual regulatory change tracking | Lag, rot, exam findings |
| **Data** | Siloed systems, batch | No real-time view; training-serving skew |

## Root causes

1. **Batch-oriented core** — no real-time event backbone for AI.
2. **Rules-only controls** — cannot adapt to adversarial drift.
3. **Siloed data** — no unified customer/transaction graph.
4. **Manual document work** — the bulk of cost in KYC, lending, compliance.

## Cost of inaction

- Continued fraud losses (bps of volume) and false-positive friction.
- Rising compliance cost and regulatory risk (EU AI Act, AML).
- Customer attrition to neobanks with superior UX and security.