# Repository and Folder Structure (Nx Polyglot Monorepo)

> **Coverage:** features → components in [`../../03_system_and_architecture/01_requirements_and_capacity_estimation/06_feature_traceability.md`](../../03_system_and_architecture/01_requirements_and_capacity_estimation/06_feature_traceability.md); languages/frameworks in [`04_language_and_framework_mapping.md`](./04_language_and_framework_mapping.md).
>
> Aegis is a **polyglot Nx monorepo**. Each language follows its own starter-kit layout (linked below); Nx unifies them into one task graph, cache, and `affected` pipeline.

## Root layout

```
aegis-bank/
├── nx.json                       # Nx workspace + plugins
├── package.json / bun.lock       # TS workspace (Bun)
├── tsconfig.json / eslint.config.ts / vitest.config.ts / vitest.workspace.ts
├── pyproject.toml / uv.lock / .python-version
├── settings.gradle.kts / build.gradle.kts / gradle.properties / gradlew
├── apps/                         # per-language apps (see sections)
├── packages/  libs/  shared/     # shared code (see sections)
├── db/  config/  build-logic/  platforms/
├── infra/  docs/  aegis-documentation/
└── .githooks/  scripts/
```

---

## Java — [`jsavinash/java-starter-kit`](https://github.com/jsavinash/java-starter-kit)

Spring Boot + Gradle, composite builds, convention plugins, BOMs.

```
apps/micro-services/<service>/     # api-gateway, payments, cards, accounts, lending, ledger-adapter
shared/{configurations,constants,entities,enums,utility}/
build-logic/{convention-plugins,springboot-app,java-app,java-lib}/
platforms/{springboot,test,web}/   # BOMs
gradle/libs.versions.toml          # version catalog
config/{checkstyle,detekt,pmd}/
aggregation/                       # JaCoCo coverage
```

- Convention plugins auto-apply Spotless/Checkstyle/Detekt/PMD/JaCoCo + git hooks.
- Quality gates: JaCoCo 80% line / 60% branch / 70% method / 80% class.
- Build: `./gradlew build` (auto-installs hooks); `./gradlew qualityCheck|qualityFix`.

---

## Python — [`jsavinash/python-starter-kit`](https://github.com/jsavinash/python-starter-kit)

uv workspace, centralized deps, MkDocs, nox.

```
apps/aegis/                        # AI risk & compliance (src/, tests/, Dockerfile, pyproject.toml)
apps/data-platform/                # dbt/Dagster
libs/shared/                       # Pydantic DTOs, contracts, utils
config/dependencies.toml           # single source of Python versions
tests/                             # root tests
pyproject.toml (uv workspace) / uv.lock / .python-version
.pre-commit-config.yaml / Makefile / main.py / noxfile.py / mkdocs.yml
```

- `[tool.uv.workspace] members = ["apps/*", "libs/shared"]`; `uv sync --all-packages`.
- Quality: `make lint|format|typecheck|test|quality-check` (ruff + mypy strict + pytest).

---

## MCP — [`jsavinash/mcp-starter-kit`](https://github.com/jsavinash/mcp-starter-kit)

Reusable MCP core + deployable servers.

```
apps/aegis-mcp/src/mcp_aegis/{server.py, cli.py, http_app.py, features/}
libs/mcp-core/src/mcp_core/        # config, auth, lifecycle, logging, transport security
```

- Transports: stdio + Streamable HTTP (`/mcp`); OAuth RFC 7662 + host allowlist (fail-closed).
- Run: `make run-stdio|run-http|security`.

---

## TypeScript — [`jsavinash/javascript-starter-kit`](https://github.com/jsavinash/javascript-starter-kit)

Bun + React 19 + tRPC + Hono + Drizzle.

```
apps/app/        # React 19 + TanStack Router (customer)
apps/web/        # Astro marketing
apps/api/        # tRPC + Hono API (BFF)
apps/email/      # React Email templates
apps/ops-console/ apps/mobile/   # ops console, React Native
packages/core/   # shared types + utils
packages/ui/     # shadcn/ui components
packages/ws-protocol/
db/              # Drizzle schema, migrations, seed
eslint.config.ts / tsconfig.json / vitest.config.ts / vitest.workspace.ts
```

- Package manager: **Bun** (`bun install`); task runner: Nx.
- Test: Vitest; lint/format: ESLint + Prettier.

---

## Nx integration

```jsonc
// nx.json
{ "plugins": ["@nx/js", "@nx/next", "@nx/react-native", "@nx/node", "@nx/gradle", "@nxlv/python"] }
```

- **Inferred tasks** from each manifest (`build.gradle.kts`, `pyproject.toml`, `package.json`).
- `targetDefaults`: `build`/`test`/`typecheck` with `dependsOn: ["^build"]`, `cache: true`.
- `nx run-many -t build`, `nx affected -t test`, `nx graph`; Nx Cloud remote cache + DTE.
- **Module boundaries** via tags: `scope:web|app|service|lib`, `platform:ts|java|python`.

## Setup

```bash
npx nx@latest init
nx add @nx/gradle @nxlv/python
bun install
uv sync --all-packages
./gradlew build
bash scripts/install-hooks.sh
nx graph
```