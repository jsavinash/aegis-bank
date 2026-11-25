# Support Escalation Matrix

## Tiers

| Tier | Owner | Scope | Response |
|---|---|---|---|
| **T0** | Agentic assistant (AI) | General queries, status | Instant |
| **T1** | Contact centre agent | Account/card/service issues | <5 min |
| **T2** | Specialist (fraud, lending, business) | Complex cases | <30 min |
| **T3** | Engineering / Incident | Outages, bugs | Per SLO |

## Escalation triggers

| Trigger | Route |
|---|---|
| **Fraud/suspected scam** | T2 fraud (priority) |
| **High-value / vulnerable customer** | T2 + human |
| **Accessibility need** | Trained agent + adjustments |
| **Complaint** | Complaints team (regulatory clock) |
| **Outage** | Incident commander (T3) |

## Escalation matrix

| Issue | L1 | L2 | L3 |
|---|---|---|---|
| Card declined | Agent | Fraud | Engineering |
| Payment stuck | Agent | Payments | Engineering |
| Suspected fraud | Agent (freeze) | Fraud | Compliance |
| Loan decision query | Agent | Lending | Credit |
| Accessibility | Agent | Accessibility lead | Product |

## SLAs

- **P1 (outage):** 15 min response, 1h mitigation target.
- **P2 (degraded):** 30 min response.
- **P3 (single customer):** 4h response.

## Handoff

Every escalation carries full context (conversation summary, account, actions taken) so the customer never repeats themselves.
