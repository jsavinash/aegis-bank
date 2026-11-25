# CDN, Edge and Distributed Caching

## CDN

- **Static assets** served via CDN (CloudFront/Cloudflare) with long-cache hashed filenames.
- **Edge functions** for light personalisation (geo, A/B assignment).
- **Edge WAF + rate limiting**; **token verification** at the edge for APIs.
- **No sensitive API caching** at the edge; cache keys exclude PII.

## Caching layers

| Layer | Cache | Invalidation |
|---|---|---|
| **Browser** | Static assets, read-only data | Hashed assets; revalidate |
| **CDN** | Static | On deploy |
| **API gateway** | Auth tokens (short TTL) | TTL |
| **Redis** | Sessions, feature cache, rate limits | TTL / explicit |
| **Application** | Reference data (FX rates, products) | Scheduled / event |
| **Database** | Query cache, read replicas | Replication lag |

## Patterns

- **Cache-aside:** read through cache; on miss, load from DB and populate.
- **Write-through** for critical data (ledger bypasses cache); **write-behind** where safe.
- **Negative caching** for known-empty results; **bloom filter** to stop penetration.

## Cache stampede / avalanche protection

- **Request coalescing** (single-flight); **stale-while-revalidate**.
- **TTL jitter**; **locks** on rebuild; **hot-key** replication.

## Eviction & sizing

- **LRU/LFU** with `maxmemory-policy`; per-bucket TTLs; memory headroom alerts.

## Distribution

- **Multi-region** active-active for Tier 0 with **conflict resolution** (last-write-wins/CRDT).
- **Read replicas** for analytics; **cache warming** after deploy.

## Observability

- Hit ratio, eviction rate, latency per cache tier; alert on regressions.
