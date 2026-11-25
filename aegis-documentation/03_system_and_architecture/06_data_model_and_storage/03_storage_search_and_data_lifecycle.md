# Storage, Search and Data Lifecycle

## Storage tiers

| Tier | Data | Store | Retention |
|---|---|---|---|
| **Cache** | Hot state, rate limits, idempotency | Redis | TTL |
| **Hot** | Active accounts, recent txns | Postgres + Redis | Live |
| **Warm** | 7-year transaction history | Postgres / Iceberg | 7 years |
| **Cold** | Archived statements, audit | S3 (Iceberg) | Per regulation |

## Search

- **Transactional search:** Postgres indexes (B-tree, GIN).
- **Document search:** Qdrant vector + BM25 hybrid + reranker.
- **Graph search:** Neo4j traversal (temporal-aware).
- **Analytics:** ClickHouse.
- **Pipeline:** embed → hybrid (BM25 + vector) → rerank → tenant/ACL filter → guardrail.

## Data lifecycle

1. **Ingest** → validate → land in raw (Iceberg).
2. **Curate** → dbt transforms → curated zone.
3. **Serve** → ClickHouse / feature store.
4. **Reconcile** → ledger vs data (break resolution).
5. **Backfill** → replay from Iceberg.
6. **Archive** → cold storage.
7. **Delete** → honour GDPR/CCPA (crypto-shredding where needed).

## Backup & DR

- **Tier 0:** PITR + multi-AZ; RTO <1h, RPO <5min.
- **Tier 1:** daily snapshots; RTO <4h, RPO <1h.
- **Tier 2:** best-effort; RTO <24h, RPO <24h.
- Restore drills each quarter.

## Residency & privacy

- In-VPC / region-locked processing where required.
- PII classification + masking; DSAR fulfilment (access/export/delete).
- Crypto-shredding for hard deletes.

## Data quality

- **Contracts** at ingestion (schema validation).
- **Tests** in dbt (uniqueness, referential integrity).
- **Lineage** via OpenMetadata/DataHub.
- **PII** classification and masking.

## Retention by class

| Class | Retention |
|---|---|
| Transactions / audit | 7 years |
| KYC / AML | 5 years |
| App logs / traces | 1 year |
| Marketing/consent | Until revoked + 1 year |