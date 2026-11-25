# Back-of-the-Envelope Capacity Estimation

## Assumptions

- 5M customers; 5M accounts; 20M card txns/month; 5M payments/month; 2M assistant queries/month; 100k loan applications/month.
- Peak = 3× average.
- Event volume ≈ 30M events/month (card, payment, ledger, audit, AI).

## Demand → capacity summary

| Driver | Volume | Average | Peak (3×) |
|---|---|---|---|
| Card authorization | 20M/month | ~7.7/sec | ~23/sec |
| Fraud scoring | Every card txn | ~7.7/sec | ~23/sec |
| Payments | 5M/month | ~1.9/sec | ~6/sec |
| Assistant queries | 2M/month | ~0.8/sec | ~2.3/sec |
| Loan applications | 100k/month | ~0.04/sec | ~0.12/sec |
| Events (all) | ~30M/month | ~12/sec | ~36/sec |
| Accounts/balance reads | 5M accounts | On demand | p99 <200ms |

## Capacity by domain (all 37 catalog sections)

| § | Domain | Capacity driver | Rough rate / note |
|---|---|---|---|
| 1 | Accounts & Deposits | Balance/ledger reads | 5M accounts; p99 <200ms |
| 2 | Payments & Transfers | Payment throughput | ~1.9/sec avg, ~6/sec peak |
| 3 | Cards | Authorization | ~7.7/sec avg, ~23/sec peak |
| 4 | Bill Pay & Payment Mgmt | On demand | Low TPS |
| 5 | Lending & Credit | Applications + decisioning | ~0.04/sec avg |
| 6 | Savings, Wealth & Investing | On demand | Low TPS |
| 7 | Money Management & Insights | Aggregation/reads | On demand |
| 8 | Credit & Financial Health | On demand | Low TPS |
| 9 | Security & Fraud | Scoring every txn | ~7.7/sec avg, ~23/sec peak |
| 10 | Customer Service & AI | Assistant queries | ~0.8/sec avg |
| 11 | Business & Commercial Banking | On demand | Low TPS |
| 12 | Treasury & Cash Management | On demand | Low TPS |
| 13 | Private Banking & Wealth (HNW) | Low volume | Low TPS |
| 14 | Kids, Teens & Family | On demand | Low TPS |
| 15 | Open Banking & Embedded Finance | API calls | On demand |
| 16 | Super-App & Lifestyle | On demand | Low TPS |
| 17 | Sustainability / Green Banking | On demand | Low TPS |
| 18 | Digital Assets & Stablecoins | On demand | Low TPS |
| 19 | Rewards & Loyalty | On demand | Low TPS |
| 20 | Insurance & Protection | On demand | Low TPS |
| 21 | Accessibility & Inclusion | UI (no TPS) | n/a |
| 22 | Compliance, Risk & Regulatory | KYC onboarding + ongoing; event-driven | Screening events |
| 23 | Platform, Data & AI Infrastructure | Event backbone | ~12/sec avg, ~36/sec peak |
| 24 | Notifications, Personalisation & UX | Event-driven fan-out | On demand |
| 25 | International / Cross-border | FX + payments | On demand |
| 26 | Payments Acceptance (merchant) | On demand | Low TPS |
| 27 | Specialty / Niche products | On demand | Low TPS |
| 28 | Credit Cards — advanced tooling | On demand | Low TPS |
| 29 | Super-App, Embedded Finance & Wallets | On demand | Low TPS |
| 30 | Business Banking — depth | On demand | Low TPS |
| 31 | Financial Inclusion, Rural & Agri | On demand | Low TPS |
| 32 | Advanced AI & Agentic Banking | Agent runs | On demand |
| 33 | Financial Wellness & Personalisation | On demand | Low TPS |
| 34 | Notifications, Alerts & UX | Event-driven | On demand |
| 35 | Statements, Documents & Self-Service | On demand | Low TPS |
| 36 | Rewards, Travel & Perks | On demand | Low TPS |
| 37 | New / Emerging niche domains | On demand | Low TPS |

## Card authorization (hot path)

- 20M/month ≈ 7.7/sec average; peak ~23/sec — but per-node p99 <150ms is the binding constraint, not TPS.
- Stateless Java service (Spring Boot / Quarkus); horizontal scale; Redis for state.

## Fraud scoring

- Every card txn scored → ~8/sec average, ~24/sec peak.
- GNN embedding lookup precomputed; <20ms; GBM <10ms → <150ms p99.

## AI assistant

- 2M queries/month ≈ 0.8/sec avg; LLM calls dominate cost.
- Stateless agents; shared Qdrant/Neo4j; vLLM with continuous batching.

## LLM inference cost

- ~3B tokens/month (2M queries × ~1.5k tokens); route easy steps to cheaper models, cache, and self-host open weights where residency requires; per-step budget enforced (LiteLLM).

## Data

- Event volume: ~30M events/month ≈ 12/sec avg; Kafka handles easily.
- Iceberg on S3; ClickHouse for OLAP.

## Storage

- 5M customers × ~5-year history → ~TB-scale; Postgres (core) + Iceberg (lake).

## Scaling strategy

- **Tier 0:** over-provision + multi-AZ; fail over fast.
- **Tier 1:** autoscale K8s; queue-based load levelling.
- **Tier 2:** best-effort, degrade gracefully.

> **Headroom note:** the 10k+ TPS peak target in the NFR/scale documents is a multi-region *design ceiling*, not the bottom-up demand. At 5M customers the derived load is ~24/sec card-auth peak (3× the ~7.7/sec average); capacity is provisioned against the headroom target, while the bottom-up figures size the current footprint.
