# High Availability, Backups and Disaster Recovery

## Availability targets

| Tier | Target |
|---|---|
| **Tier 0** (ledger, payments, auth) | 99.95%+ |
| **Tier 1** (AI, compliance) | 99.9% |
| **Tier 2** (insights, super-app) | 99.5% |

## High availability

- **Multi-AZ** deployment (K8s across ≥3 AZs).
- **Redundant** everything for Tier 0.
- **Health checks** + automatic failover.
- **Graceful degradation** (see product doc).

## Backups

| Data | Backup | Frequency | Retention |
|---|---|---|---|
| **Postgres** | PITR + snapshots | Continuous + daily | 35 days |
| **Qdrant/Neo4j** | Snapshots | Daily | 30 days |
| **Iceberg** | S3 versioning + snapshots | Continuous | Per policy |
| **Audit** | WORM storage | Continuous | 7 years |

## Disaster recovery

- **RTO** < 1 hour (Tier 0), **RPO** < 5 minutes.
- **Active-passive** warm standby in a second region.
- **Runbooks** (see operations folder) for failover.
- **Quarterly DR drills**; annual full failover test.

## Recovery

- **Automated failover** for Tier 0.
- **Manual runbook** for complex recovery.
- **Data reconciliation** after recovery (idempotency ensures no double-post).