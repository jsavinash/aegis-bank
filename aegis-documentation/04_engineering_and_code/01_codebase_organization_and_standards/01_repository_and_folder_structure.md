# Repository and Folder Structure (Nx Polyglot Monorepo)

> **Coverage:** features → components in [`../../03_system_and_architecture/01_requirements_and_capacity_estimation/06_feature_traceability.md`](../../03_system_and_architecture/01_requirements_and_capacity_estimation/06_feature_traceability.md); languages/frameworks in [`04_language_and_framework_mapping.md`](./04_language_and_framework_mapping.md); use cases in [`01_uml_modeling_and_system_use_cases.md`](../02_low_level_design_and_patterns/01_uml_modeling_and_system_use_cases.md).

Aegis is a **polyglot Nx monorepo**: one workspace, one task graph, one cache. Nx infers projects/targets from each toolchain's own manifest (`build.gradle`, `pyproject.toml`, `package.json`), so Java (Spring Boot/Quarkus), Python (AI/data), and TypeScript (channels/BFF) coexist with `nx affected`, caching, and distributed CI.

## Layout

```
aegis-bank/
├── nx.json                    # plugins, targetDefaults, module boundaries
├── package.json               # workspace root (pnpm) + nx
├── pnpm-workspace.yaml        # apps/*, packages/*
├── tsconfig.base.json
├── apps/                      # deployable frontends (TS)
│   ├── web/                   # Next.js customer web
│   ├── ops-console/           # Next.js agent/ops console
│   ├── mobile/                # React Native (Expo)
│   └── bff/                   # GraphQL/REST BFF (Node/TS)
├── services/                  # Java transactional (Gradle)
│   ├── payments/  cards/  accounts/  lending/  ledger-adapter/
├── services-python/           # Python AI/data (uv)
│   ├── aegis/                 # AI risk & compliance plane
│   └── data-platform/         # dbt/Dagster
├── packages/                  # shared, versioned libs
│   ├── ts/                    # ui, contracts, config, sdk
│   ├── java/                  # shared domain/kotlin
│   └── python/                # shared aegis libs
├── infra/                     # Terraform, k8s, Helm
├── docs/                      # research & ADRs
└── aegis-documentation/       # this documentation set
```

## Nx plugins (nx.json)

```jsonc
{
  "plugins": [
    "@nx/js", "@nx/next", "@nx/react-native", "@nx/node",
    "@nx/gradle",        // Java (Spring Boot/Quarkus), Java 17+
    "@nxlv/python"       // Python (Poetry/uv) — community
  ]
}
```

- **Java:** `@nx/gradle` (or `@nx/maven`, experimental Nx 22+) infers `build`/`test` from `build.gradle`.
- **Python:** `@nxlv/python` infers targets from `pyproject.toml`; `uv` workspace sources create cross-language edges.
- **TS:** `@nx/js`/`next`/`react-native`/`node` infer from `package.json`/`vite`/`jest`.
- **2026:** lazy graph hydration means a JS dev need not install Java/Python toolchains to compute the graph.

## Task pipeline (targetDefaults)

```jsonc
{
  "targetDefaults": {
    "build":    { "dependsOn": ["^build"], "cache": true, "outputs": ["{projectRoot}/dist"] },
    "test":     { "dependsOn": ["^build"], "cache": true },
    "lint":     { "cache": true },
    "typecheck":{ "dependsOn": ["^build"], "cache": true }
  }
}
```

- `nx run-many -t build`, `nx affected -t test`, `nx build payments`, `nx run web:dev` (with `dependsOn` to boot Java).
- **Nx Cloud** remote cache + **distributed task execution** for CI; **affected** skips untouched projects.

## Module boundaries (tags)

- `scope:web|app|service|lib`, `type:feature|ui|util|data`, `platform:ts|java|python`.
- Enforce: `app` → `lib`; `service` → `lib`; no `service` → `service` (events only); `lib` no app deps.

## Service layout (per project)

```
<project>/
├── build.gradle / pyproject.toml / package.json   # manifest Nx reads
├── src/main/java/...   (Java)  |  src/... (Python)  |  src/... (TS)
├── src/test/...
├── api/                # OpenAPI/AsyncAPI contracts
├── project.json        # only if a target needs explicit config
└── README.md
```

## Conventions

- **Contract-first:** every service has `api/` contracts; **contracts** package is the source of truth.
- **Domain-driven:** domain layer pure; adapters at edges; **no cross-service DB access**.
- **Ownership:** CODEOWNERS per project; **ADRs** in `docs/adr/`.
- **Config/secrets:** env/Terraform; Vault (never in repo).

## Setup

```bash
npx nx@latest init                 # add Nx to the repo
nx add @nx/gradle                  # Java
nx add @nxlv/python                # Python
nx add @nx/next @nx/react-native   # channels
nx graph                           # visualize the polyglot graph
```