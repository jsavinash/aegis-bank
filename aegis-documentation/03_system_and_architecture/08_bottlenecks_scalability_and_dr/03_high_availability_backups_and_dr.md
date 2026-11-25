# High Availability, Backups and Disaster Recovery

## Availability targets

| Tier | Target | RTO | RPO |
|---|---|---|---|
| **Tier 0** (ledger, payments, auth) | 99.95%+ | <1h | <5min |
| **Tier 1** (AI, compliance) | 99.9% | <4h | <1h |
| **Tier 2** (insights, super-app) | 99.5% | <24h | <24h |

## High availability

- **Multi-AZ** deployment (K8s across ≥3 AZs); **redundant** everything for Tier 0.
- **Health checks** + automatic failover; **graceful degradation** (see product doc).

## Backups

| Data | Backup | Frequency | Retention |
|---|---|---|---|
| **Postgres** | PITR + snapshots | Continuous + daily | 35 days |
| **Qdrant/Neo4j** | Snapshots | Daily | 30 days |
| **Iceberg** | S3 versioning + snapshots | Continuous | Per policy |
| **Audit** | WORM storage (object lock) | Continuous | 7 years |

- **Encrypted** backups; **immutable** (object lock); **restore tests** monthly.

## Disaster recovery

- **Active-passive** warm standby (second region); **active-active** for Tier 0 where feasible.
- **Runbooks** (operations folder) for failover; **failback** procedure.
- **Dependency mapping** (which services/regions fail together).
- **Quarterly DR drills**; annual full failover test.

## Recovery

- **Automated failover** for Tier 0; **manual runbook** for complex recovery.
- **Data reconciliation** after recovery (idempotency prevents double-post).
- **Stakeholder comms** + status page updates.

## Secrets & config

- **Secrets** backed up/escrowed (HSM/KMS); **IaC** (Terraform) is the source of truth for rebuild.
