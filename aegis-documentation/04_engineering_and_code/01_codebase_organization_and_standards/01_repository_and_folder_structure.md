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
├── packages/                       # shared libs (ui, contracts, config)
├── data/                           # dbt, Dagster, IaC
├── infra/                          # Terraform, k8s, Helm
├── docs/                           # research & architecture docs
├── aegis-documentation/            # this documentation set
```

## Service layout (per service)

```
<service>/
├── src/main/java/      production code (domain, adapters)
├── src/test/java/      tests
├── api/                contracts (OpenAPI/AsyncAPI)
├── db/migrations/      Flyway/Liquibase
└── README.md
```

## Conventions

- **Contract-first:** every service has `api/` contracts.
- **Domain-driven:** the domain layer is pure; adapters at edges.
- **No cross-service DB access.**
- **Ownership:** each service has a CODEOWNERS entry.

## Naming & versioning

- **SemVer** for packages/services; **API N-2** support.
- Consistent module names (`<domain>.<capability>`); generated code in `gen/` (not hand-edited).

## Config & secrets

- **Config** via env/Terraform; **secrets** via Vault (never in repo); **`.env.example`** only.

## Tooling

- **Monorepo:** Nx/Turborepo (TS), Maven/Gradle (Java), uv/poetry (Python).
- **Lint/format/type** in pre-commit + CI; **ADRs** in `docs/adr/`.

## Ownership & ADRs

- **CODEOWNERS** per package; **ADRs** in `03_system_and_architecture/02_tech_choices_and_adrs/architecture_decision_records/`.