# Scoping and Constraints

## Scope

In: retail digital bank MVP (accounts, cards, payments, lending lite, wealth lite, security, compliance) + AI risk plane. Out: mortgage origination, crypto custody, insurance underwriting, merchant acquiring (see business scope doc).

## Constraints

| Type | Constraint |
|---|---|
| **Regulatory** | Banking licence / BaaS partner; PSD2/3; AML/KYC; GDPR/CCPA; EU AI Act; EAA/WCAG 2.2 AA |
| **Data residency** | Self-host AI stack in-VPC where required |
| **Latency** | Fraud hot path <150ms; balance <200ms |
| **Availability** | 99.95%+ |
| **Team** | Small initial team; AI-first delivery |
| **Timeline** | MVP ~3 months; scale ~9 months |

## Non-negotiables

- Buy the core; build the AI/risk plane.
- Event-driven from day one.
- Eval-gated CI; HITL for high-consequence actions.
- WCAG 2.2 AA.

## Out of scope for this phase

- Physical branches, full private banking, merchant acquiring.