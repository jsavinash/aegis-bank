# Runbook: Database Failover

## Symptoms
- Primary DB unavailable / high latency.
- Connection errors spiking.

## Steps

1. **Confirm** the failure (metrics, logs, synthetic checks).
2. **Check** replica health and lag.
3. **Promote** a read replica to primary (managed failover if available).
4. **Update** connection strings / service discovery.
5. **Restart** affected services (rolling).
6. **Verify** reads/writes; run smoke tests.
7. **Reconcile** — idempotency ensures no double-post; check for gaps.
8. **Communicate** — status page, support, regulators if material.
9. **Post-mortem** — root cause, action items.

## Rollback
- If promotion fails, restore from PITR and replay.

## Prevention
- Multi-AZ by default; automated failover for Tier 0.
- Regular failover drills.
