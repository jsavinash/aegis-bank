# Disaster Recovery Plan

## Objectives

| Tier | RTO | RPO |
|---|---|---|
| **Tier 0** (ledger, payments, auth) | <1 hour | <5 minutes |
| **Tier 1** (AI, compliance) | <4 hours | <1 hour |
| **Tier 2** (insights, super-app) | <24 hours | <24 hours |

## Strategy

- **Multi-AZ** within region (primary).
- **Warm standby** in a second region (Tier 0).
- **Backups** (PITR + snapshots) with cross-region copies.
- **IaC** rebuilds infrastructure deterministically.

## Failure scenarios & responses

| Scenario | Response |
|---|---|
| **AZ failure** | Automatic failover within region |
| **Region failure** | Promote warm standby; DNS failover |
| **Data corruption** | Restore from PITR; reconcile via idempotency |
| **Core vendor outage** | Vendor SLA + degraded mode |
| **Model/inference outage** | Fall back to rules/human |
| **Ransomware** | Isolate, restore from immutable backups |

## Recovery steps

1. **Detect** (alerting).
2. **Declare** (incident commander).
3. **Fail over** (runbook).
4. **Verify** (smoke tests, reconciliation).
5. **Communicate** (status page, customers, regulators).

## Testing

- **Quarterly** DR drill (at least Tier 0 failover).
- **Annual** full region failover test.
- **Runbooks** kept current (see incident-runbooks).
