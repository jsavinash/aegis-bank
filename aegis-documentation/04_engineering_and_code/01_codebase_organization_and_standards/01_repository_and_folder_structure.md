# Repository and Folder Structure (Nx Polyglot Monorepo)

> **Coverage:** features → components in [`../../03_system_and_architecture/01_requirements_and_capacity_estimation/06_feature_traceability.md`](../../03_system_and_architecture/01_requirements_and_capacity_estimation/06_feature_traceability.md); languages/frameworks in [`04_language_and_framework_mapping.md`](./04_language_and_framework_mapping.md); use cases in [`01_uml_modeling_and_system_use_cases.md`](../02_low_level_design_and_patterns/01_uml_modeling_and_system_use_cases.md).
>
> **Python layout follows [`jsavinash/python-starter-kit`](https://github.com/jsavinash/python-starter-kit)** (uv workspace): `apps/` + `libs/shared`, `config/dependencies.toml`, `tests/`, `.githooks/`, `scripts/`, `.python-version`, `.pre-commit-config.yaml`, `Makefile`, `main.py`, `docker-compose.yml`, `mkdocs.yml`, `noxfile.py`.
> **MCP servers follow [`jsavinash/mcp-starter-kit`](https://github.com/jsavinash/mcp-starter-kit)**: `apps/<server>/src/<pkg>/{server.py,cli.py,http_app.py,features/}` + reusable `libs/mcp-core`.

Aegis is a **polyglot Nx monorepo**: one workspace, one task graph, one cache. Nx infers projects/targets from each toolchain's own manifest (`build.gradle`, `pyproject.toml`, `package.json`), so Java (Spring Boot/Quarkus), Python (AI/data, uv), and TypeScript (channels/BFF) coexist with `nx affected`, caching, and distributed CI.

## Layout

```
aegis-bank/
├── nx.json                    # plugins, targetDefaults, module boundaries
├── package.json               # workspace root (pnpm) + nx
├── pnpm-workspace.yaml        # apps/*, libs/*
├── tsconfig.base.json
├── pyproject.toml             # uv workspace root (members = apps/*, libs/shared)
├── uv.lock                    # single, cross-Python lockfile
├── .python-version            # 3.12
├── .pre-commit-config.yaml
├── Makefile                   # install / lint / test / quality / docker
├── main.py                    # root CLI entry
├── docker-compose.yml         # Redis + local services
├── mkdocs.yml                 # docs site
├── noxfile.py                 # multi-env test sessions
├── apps/                      # deployable apps
│   ├── web/                   # Next.js customer web (TS)
│   ├── ops-console/           # Next.js agent/ops console (TS)
│   ├── mobile/                # React Native (Expo, TS)
│   ├── bff/                   # GraphQL/REST BFF (Node/TS)
│   ├── aegis/                 # Python AI risk & compliance (src/, tests/, Dockerfile, pyproject.toml)
│   ├── aegis-mcp/             # MCP server (src/mcp_aegis/{server,cli,http_app}.py, features/)
│   └── data-platform/         # Python dbt/Dagster
├── libs/                      # shared libraries
│   ├── ts/                    # ui, contracts, config, sdk
│   ├── java/                  # shared domain
│   ├── mcp-core/              # reusable MCP config/auth/lifecycle/logging/transport security
│   └── shared/                # Python shared (Pydantic DTOs, contracts, utils)
├── services/                  # Java transactional (Gradle)
│   ├── payments/  cards/  accounts/  lending/  ledger-adapter/
├── config/dependencies.toml   # centralized Python dependency version catalog
├── tests/                     # root-level tests
├── infra/                     # Terraform, k8s, Helm
├── docs/                      # MkDocs + ADRs
├── aegis-documentation/       # this documentation set
├── .githooks/                 # pre-commit / commit-msg / pre-push
└── scripts/                   # install-hooks.sh, etc.
```

## Nx plugins (nx.json)

```jsonc
{
  "plugins": [
    "@nx/js", "@nx/next", "@nx/react-native", "@nx/node",
    "@nx/gradle",        // Java (Spring Boot/Quarkus), Java 17+
    "@nxlv/python"       // Python (uv) — community
  ]
}
```

- **Java:** `@nx/gradle` (or `@nx/maven`, experimental Nx 22+) infers `build`/`test` from `build.gradle`.
- **Python:** `@nxlv/python` infers targets from each `pyproject.toml`; `uv` workspace sources create cross-language edges.
- **TS:** `@nx/js`/`next`/`react-native`/`node` infer from `package.json`/`vite`/`jest`.
- **2026:** lazy graph hydration means a JS dev need not install Java/Python toolchains to compute the graph.

## Python (uv workspace — starter-kit layout)

```toml
# pyproject.toml (root)
[tool.uv.workspace]
members = ["apps/*", "libs/shared"]

[dependency-groups]
dev = ["pytest", "ruff", "mypy"]
```

```toml
# apps/aegis/pyproject.toml
[project]
name = "aegis"
dependencies = ["shared"]

[tool.uv.sources]
shared = { workspace = true }
```

- Each app: `src/`, `tests/`, `Dockerfile`, `pyproject.toml`, `.env`.
- `config/dependencies.toml` is the **single source of truth** for Python versions.
- `uv sync` installs the workspace; `uv add <dep>`; `uv run pytest`; `make install`.
- Nx: `@nxlv/python` infers targets (or a `project.json` target `uv run pytest`, `cache: true`).
- Quality: `make lint|format|typecheck|test|quality-check` (ruff + mypy strict + pytest).

## MCP servers (mcp-starter-kit layout)

Each deployable MCP server lives under `apps/`; cross-server concerns live in `libs/mcp-core`:

```
apps/aegis-mcp/src/mcp_aegis/
├── server.py      # composition root (MCPServer)
├── cli.py         # stdio + HTTP runner
├── http_app.py    # uvicorn/ASGI entry
└── features/      # tools, resources, prompts
libs/mcp-core/src/mcp_core/   # config, auth, lifecycle, logging, transport security
```

- **Transports:** stdio (local host) + **Streamable HTTP** (`/mcp`); SSE is legacy.
- **Auth:** OAuth resource server (RFC 7662 introspection) + host allowlist; **fail-closed** remote bind.
- **Registration/transport at the app edge;** business logic in feature/service modules.
- Run: `uv sync --all-packages`, `make run-stdio`, `make run-http`, `make security`.

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

- `nx run-many -t build`, `nx affected -t test`, `nx build payments`, `nx run web:dev`.
- **Nx Cloud** remote cache + **distributed task execution**; **affected** skips untouched projects.

## Module boundaries (tags)

- `scope:web|app|service|lib`, `type:feature|ui|util|data`, `platform:ts|java|python`.
- Enforce: `app` → `lib`; `service` → `lib`; no `service` → `service` (events only); `lib` no app deps.

## Service layout (per project)

```
<project>/
├── build.gradle / pyproject.toml / package.json   # manifest Nx reads
├── src/ (main/java | python | ts)   ├── tests/
├── api/                # OpenAPI/AsyncAPI contracts
├── Dockerfile
├── project.json        # only if a target needs explicit config
└── README.md
```

## Conventions

- **Contract-first:** every service has `api/` contracts; `libs/ts/contracts` is the source of truth.
- **Domain-driven:** domain layer pure; adapters at edges; **no cross-service DB access**.
- **Ownership:** CODEOWNERS per project; **ADRs** in `docs/adr/`.
- **Config/secrets:** env/Terraform; Vault (never in repo); `.env.example` only.

## Setup

```bash
npx nx@latest init                 # add Nx
nx add @nx/gradle                  # Java
nx add @nxlv/python                # Python
uv sync                            # install Python workspace (starter-kit layout)
bash scripts/install-hooks.sh      # git hooks
nx add @nx/next @nx/react-native   # channels
nx graph                           # visualize the polyglot graph
```