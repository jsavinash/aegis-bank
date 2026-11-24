# Storage, Search and Data Lifecycle

## Storage tiers

| Tier | Data | Store | Retention |
|---|---|---|---|
| **Hot** | Active accounts, recent txns | Postgres + Redis | Live |
| **Warm** | 7-year transaction history | Postgres / Iceberg | 7 years |
| **Cold** | Archived statements, audit | S3 (Iceberg) | Per regulation |

## Search

- **Transactional search:** Postgres indexes (B-tree, GIN).
- **Document search:** Qdrant vector + BM25 hybrid + reranker.
- **Graph search:** Neo4j traversal (temporal-aware).
- **Analytics:** ClickHouse.

## Data lifecycle

1. **Ingest** → validate → land in raw (Iceberg).
2. **Curate** → dbt transforms → curated zone.
3. **Serve** → ClickHouse / feature store.
4. **Archive** → cold storage.
5. **Delete** → honour GDPR/CCPA (crypto-shredding where needed).

## Data quality

- **Contracts** at ingestion (schema validation).
- **Tests** in dbt (uniqueness, referential integrity).
- **Lineage** via OpenMetadata/DataHub.
- **PII** classification and masking.