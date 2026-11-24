# Network Resilience and Rate Limiting

## Rate limiting

| Scope | Limit (illustrative) |
|---|---|
| **Login / auth** | Strict (brute-force protection) |
| **Payments** | Per-customer velocity + daily limits |
| **Public API** | Per-client token bucket |
| **Assistant** | Per-user quota + cost budget |

## Resilience patterns

| Pattern | Use |
|---|---|
| **Timeouts** | Every network call |
| **Retries** | Idempotent only; exponential backoff + jitter |
| **Circuit breaker** | Protect against failing dependencies |
| **Bulkhead** | Isolate resource pools |
| **Fallback** | Degrade gracefully (e.g., assistant → human) |
| **Load shedding** | Shed low-priority traffic under pressure |

## Network security

- **TLS 1.3** everywhere; mTLS for service-to-service.
- **WAF** at the edge.
- **DDoS protection** (Cloudflare/AWS Shield).
- **Network segmentation** (VPC, private subnets).

## Multi-region

- **Active-active** for Tier 0; **active-passive** for Tier 2.
- **Global load balancer** with health checks.
- **Data residency** routing per region.