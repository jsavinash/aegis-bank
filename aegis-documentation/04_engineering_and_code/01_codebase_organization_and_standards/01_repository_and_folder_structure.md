# Repository and Folder Structure (Nx Polyglot Monorepo)

> **Coverage:** features → components in [`../../03_system_and_architecture/01_requirements_and_capacity_estimation/06_feature_traceability.md`](../../03_system_and_architecture/01_requirements_and_capacity_estimation/06_feature_traceability.md); languages/frameworks in [`04_language_and_framework_mapping.md`](./04_language_and_framework_mapping.md); use cases in [`01_uml_modeling_and_system_use_cases.md`](../02_low_level_design_and_patterns/01_uml_modeling_and_system_use_cases.md).
>
> **Layout references:** Python/uv → [`jsavinash/python-starter-kit`](https://github.com/jsavinash/python-starter-kit); MCP → [`jsavinash/mcp-starter-kit`](https://github.com/jsavinash/mcp-starter-kit); Java → [`jsavinash/java-starter-kit`](https://github.com/jsavinash/java-starter-kit); TypeScript → [`jsavinash/javascript-starter-kit`](https://github.com/jsavinash/javascript-starter-kit) (Bun). Nx ties them into one task graph.

Aegis is a **polyglot Nx monorepo**: one workspace, one task graph, one cache. Nx infers projects/targets from each toolchain's own manifest (`build.gradle.kts`, `pyproject.toml`, `package.json`), so Java (Spring Boot), Python (AI/data, uv), and TypeScript (channels/BFF) coexist with `nx affected`, caching, and distributed CI.

## Layout

```
aegis-bank/
├── nx.json / package.json / bun.lock / tsconfig.json
├── eslint.config.ts / vitest.config.ts / vitest.workspace.ts
├── pyproject.toml / uv.lock / .python-version         # uv workspace (Python)
├── .pre-commit-config.yaml / Makefile / main.py / docker-compose.yml / mkdocs.yml / noxfile.py
├── settings.gradle.kts / build.gradle.kts / gradle.properties / .sdkmanrc / gradlew
├── gradle/libs.versions.toml                          # Java version catalog
├── apps/
│   ├── app/ web/ api/ email/                          # TS (React app, Astro web, tRPC/Hono API, React Email)
│   ├── ops-console/ mobile/                             # TS (Next.js ops, React Native)
│   ├── aegis/ aegis-mcp/ data-platform/               # Python (AI, MCP, data)
│   └── micro-services/                                # Java Spring Boot
│       ├── api-gateway/ payments/ cards/ accounts/ lending/ ledger-adapter/
├── packages/                    # TS shared (javascript-starter-kit)
│   ├── core/ (types, utils)  ui/ (shadcn)  ws-protocol/
├── db/                          # Drizzle schema, migrations, seed
├── libs/                        # Python/MCP shared
│   ├── mcp-core/                # reusable MCP config/auth/lifecycle
│   └── shared/                  # Python shared (Pydantic DTOs/contracts)
├── shared/                      # Java shared
│   ├── configurations/ constants/ entities/ enums/ utility/
├── build-logic/                 # Gradle convention plugins (composite build)
│   ├── convention-plugins/ springboot-app/ java-app/ java-lib/
├── platforms/                   # BOMs (springboot, test, web)
├── config/                      # dependencies.toml + checkstyle/detekt/pmd
├── aggregation/                 # JaCoCo coverage aggregation
├── tests/                       # root tests
├── infra/                       # Terraform, k8s, Helm
├── docs/ (mkdocs) / aegis-documentation/
├── .githooks/ (pre-commit, commit-msg, pre-push)
└── scripts/install-hooks.sh
```

## Nx plugins (nx.json)

```jsonc
{
  "plugins": [
    "@nx/js", "@nx/next", "@nx/react-native", "@nx/node",
    "@nx/gradle",        // Java (Spring Boot), Java 17+
    "@nxlv/python"       // Python (uv)
  ]
}
```

- **Java:** `@nx/gradle` infers `build`/`test` from `build.gradle.kts`.
- **Python:** `@nxlv/python` infers from `pyproject.toml`; `uv` workspace sources create edges.
- **TS:** `@nx/js`/`next`/`react-native`/`node`.
- **2026:** lazy graph hydration — JS devs needn't install Java/Python toolchains.

## TypeScript (javascript-starter-kit layout)

- **Runtime/pm:** **Bun** (`bun install`, `bun.lock`); workspaces via `package.json` + Nx.
- **Apps:** `apps/app` (React 19 + TanStack Router), `apps/web` (Astro), `apps/api` (tRPC + Hono), `apps/email` (React Email), plus `ops-console`/`mobile`.
- **Packages:** `packages/core` (types/utils), `packages/ui` (shadcn), `packages/ws-protocol`.
- **DB:** `db/` (Drizzle schema, migrations, seed).
- **Tooling:** root `eslint.config.ts`, `tsconfig.json`, `vitest.config.ts` + `vitest.workspace.ts`.

## Python (uv — python-starter-kit layout)

```toml
# pyproject.toml (root)
[tool.uv.workspace]
members = ["apps/aegis", "apps/aegis-mcp", "apps/data-platform", "libs/shared"]
```

- Each app: `src/`, `tests/`, `Dockerfile`, `pyproject.toml`, `.env`; `config/dependencies.toml` is the version source of truth.
- `uv sync --all-packages`, `uv run pytest`, `make install|lint|test|quality-check`.

## MCP (mcp-starter-kit layout)

```
apps/aegis-mcp/src/mcp_aegis/{server.py, cli.py, http_app.py, features/}
libs/mcp-core/src/mcp_core/     # config, auth, lifecycle, logging
```

- Transports: **stdio + Streamable HTTP** (`/mcp`); **OAuth** RFC 7662 + host allowlist (fail-closed).
- `make run-stdio|run-http|security`.

## Java (java-starter-kit layout)

- **Microservices:** `apps/micro-services/<service>/` (Spring Boot, `src/main/java`, `src/test/java`).
- **Shared:** `shared/{configurations,constants,entities,enums,utility}`.
- **Build-logic:** `build-logic/` convention plugins (`springboot-app`, `java-app`, `java-lib`, `combined`) — auto-apply Spotless/Checkstyle/Detekt/PMD/JaCoCo + git hooks.
- **Platforms (BOMs):** `platforms/{springboot,test,web}`; versions in `gradle/libs.versions.toml`.
- **Quality gates:** JaCoCo 80% line / 60% branch / 70% method / 80% class; `./gradlew qualityCheck|qualityFix`.
- **Composite builds** in `settings.gradle.kts` (build-logic, micro-services, shared, platforms, aggregation).
- Hooks auto-install on build; `bash scripts/install-hooks.sh` to (re)install.

## Task pipeline (targetDefaults)

```jsonc
{
  "targetDefaults": {
    "build":    { "dependsOn": ["^build"], "cache": true, "outputs": ["{projectRoot}/dist", "{projectRoot}/build"] },
    "test":     { "dependsOn": ["^build"], "cache": true },
    "lint":     { "cache": true },
    "typecheck":{ "dependsOn": ["^build"], "cache": true }
  }
}
```

- `nx run-many -t build`, `nx affected -t test`, `nx build payments`, `nx run web:dev`.
- **Nx Cloud** remote cache + **DTE**; **affected** skips untouched projects.

## Module boundaries (tags)

- `scope:web|app|service|lib`, `type:feature|ui|util|data`, `platform:ts|java|python`.
- Enforce: `app` → `lib`; `service` → `lib`; no `service` → `service` (events only); `lib` no app deps.

## Conventions

- **Contract-first:** `packages/core` (types/contracts) + `api/` per service.
- **Domain-driven:** domain pure; adapters at edges; **no cross-service DB access**.
- **Ownership:** CODEOWNERS per project; **ADRs** in `docs/adr/`.
- **Config/secrets:** env/Terraform; Vault; `.env.example` only.

## Setup

```bash
npx nx@latest init
nx add @nx/gradle @nxlv/python
bun install                         # TS workspace (javascript-starter-kit)
uv sync --all-packages
./gradlew build                     # Java (auto-installs hooks)
bash scripts/install-hooks.sh       # git hooks
nx add @nx/next @nx/react-native
nx graph
```