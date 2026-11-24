# CDN, Edge and Distributed Caching

## CDN

- **Static assets** served via CDN (CloudFront/Cloudflare) with long-cache hashed filenames.
- **Edge functions** for light personalisation (geo, A/B assignment).
- **No sensitive API caching** at the edge.

## Caching layers

| Layer | Cache | Invalidation |
|---|---|---|
| **Browser** | Static assets, read-only data | Hashed assets; revalidate |
| **CDN** | Static | On deploy |
| **API gateway** | Auth tokens (short TTL) | TTL |
| **Redis** | Sessions, feature cache, rate limits | TTL / explicit |
| **Application** | Reference data (FX rates, products) | Scheduled / event |
| **Database** | Query cache, read replicas | Replication lag |

## Cache-aside pattern

- Read through cache; on miss, load from DB and populate.
- Write-through for critical data (ledger bypasses cache).

## Cache stampede protection

- **Request coalescing** (single-flight).
- **Stale-while-revalidate**.
- **TTL jitter**.

## Distribution

- **Multi-region** active-active for Tier 0 (with conflict resolution).
- **Read replicas** for analytics.