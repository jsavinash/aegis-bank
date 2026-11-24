# Repository and Folder Structure

> **Coverage:** features → components in [`../../03_system_and_architecture/01_requirements_and_capacity_estimation/06_feature_traceability.md`](../../03_system_and_architecture/01_requirements_and_capacity_estimation/06_feature_traceability.md); languages/frameworks in [`04_language_and_framework_mapping.md`](./04_language_and_framework_mapping.md); use cases in [`01_uml_modeling_and_system_use_cases.md`](../02_low_level_design_and_patterns/01_uml_modeling_and_system_use_cases.md).

## Monorepo layout

```
aegis-bank/
├── aegis/                          # Python AI/risk plane (implemented)
│   ├── models/ agents/ graphs/ workflows/ tools/
│   ├── rag/ llm/ guardrails/ eval/ risk/ compliance/
│   ├── db/ security/ memory/ api/
├── services/                       # Java transactional services (to build)
│   ├── payments/ cards/ accounts/ lending/
├── channels/                       # TypeScript (to build)
│   ├── mobile/ web/ ops/
├── data/                           # dbt, Dagster, IaC
├── infra/                          # Terraform, k8s, Helm
├── docs/                           # research & architecture docs
├── aegis-documentation/            # this documentation set
```

## Service layout (per service)

```
<service>/
├── cmd/            entrypoints
├── internal/       private code (domain, adapters)
├── api/            contracts (OpenAPI/AsyncAPI)
├── tests/
└── README.md
```

## Conventions

- **Contract-first:** every service has `api/` contracts.
- **Domain-driven:** `internal/domain` is pure; adapters at edges.
- **No cross-service DB access.**
- **Ownership:** each service has a CODEOWNERS entry.