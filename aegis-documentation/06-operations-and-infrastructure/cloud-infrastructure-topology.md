# Cloud Infrastructure Topology

> **Coverage:** infrastructure sized from feature volumes ([`../01-business-and-strategy/14_feature_catalog.md`](../01-business-and-strategy/14_feature_catalog.md)); operational failure modes from use cases ([`../03_system_and_architecture/01_requirements_and_capacity_estimation/07_use_case_catalog_all.md`](../03_system_and_architecture/01_requirements_and_capacity_estimation/07_use_case_catalog_all.md)).

## Topology

`mermaid
flowchart TD
 U[Users] --> CDN[CloudFront / Cloudflare]
 CDN --> LB[Global LB]
 LB --> VPC1[VPC - Region A]
 LB --> VPC2[VPC - Region B - warm standby]
 subgraph RegionA[VPC - Region A]
 EKS[EKS cluster - 3 AZs]
 RDS[(RDS/Aurora Postgres)]
 REDIS[(ElastiCache Redis)]
 MSK[(MSK / Redpanda)]
 S3[(S3 - Iceberg)]
 QDR[(Qdrant)]
 NEO[(Neo4j)]
 end
`

## Accounts (AWS/GCP)

| Account | Purpose |
|---|---|
| **prod** | Production workloads |
| **staging** | Pre-prod |
| **dev** | Development |
| **security** | Logs, audit, security tooling |
| **shared** | Networking, DNS, CI/CD |

## Networking

- **VPC** per environment; private subnets for data.
- **Transit Gateway** for connectivity.
- **PrivateLink** for managed services.
- **WAF + Shield** at the edge.

## Data services

- **Aurora Postgres** (multi-AZ) — transactional.
- **S3** — Iceberg lakehouse (versioned).
- **MSK/Redpanda** — events.
- **ElastiCache** — hot state.
- **Qdrant/Neo4j** — self-managed on EKS (GPU nodes for vLLM).

## Environments

dev → staging → prod; all defined in Terraform; prod parity with staging.
