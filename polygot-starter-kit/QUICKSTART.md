# Quick Start — Polygot Starter Kit

One repo, one control plane (**`make`**), four stacks: **TypeScript (pnpm + Turborepo)**, **Python/uv**, **Java/Gradle**, **MCP**.

## 1. Prerequisites
- **pnpm** ≥ 9 (TS) — https://pnpm.io
- **uv** (Python) — https://docs.astral.sh/uv
- **Java 21** (Gradle) + **Node ≥ 20**
- **Docker** (optional, for redis/compose)

## 2. Bootstrap (one command)
```bash
git clone <repo> && cd polygot-starter-kit
make bootstrap          # install deps + git hooks
```
`make install` = `pnpm install` + `uv sync --all-packages` (TS tasks run via **Turborepo**).

## 3. Run everything
```bash
make dev                # all dev servers (web, api, app)
```
Run individually:
```bash
make run-web            # Astro marketing (apps/web)
make run-api            # tRPC + Hono API (apps/api)
make run-app            # React app (apps/app)
make run-aegis          # Python AI service (apps/aegis)
make run-mcp            # MCP server, stdio (apps/aegis-mcp)
make run-data           # data-platform
make run-java           # Java payments (or: ./java.sh run)
```

### Java microservices (Gradle)
```bash
./java.sh build         # ./gradlew clean build
./java.sh test          # ./gradlew test
./java.sh run           # ./gradlew -p apps/micro-services payments:bootRun
./java.sh quality       # spotlessApply + check
./java.sh docker        # jibDockerBuild (payments)
# direct Gradle equivalents:
./gradlew -p apps/micro-services payments:bootRun
./gradlew -p apps/micro-services payments:test
```

## 4. Build / test / quality
```bash
make build              # nx build + gradle build
make test               # nx test + pytest
make lint format typecheck
make ci                 # local CI mirror (affected build/test/lint + ruff/mypy/pytest + gradle)
```

## 5. Containers & docs
```bash
make docker-up          # redis + services
make docker-down
make docs               # mkdocs build
make security           # pip-audit
make sbom               # syft SBOM (if installed)
```

## 6. Clean
```bash
make clean              # remove build/cache artifacts
make deep-clean         # also node_modules + .venv
```

## 7. Use Nx directly (optional)
```bash
npx nx graph                 # visualize the polyglot graph
npx nx affected -t build     # only what changed
npx nx run-many -t test
```

## 8. Layout
```
apps/            app, web, api, email, ops-console, mobile (TS) · aegis, aegis-mcp, data-platform (Py) · micro-services (Java)
packages/        core, ui, ws-protocol (TS)
libs/            contracts, shared (Py), mcp-core
shared/          Java shared
build-logic/     Gradle convention plugins
platforms/       Gradle BOMs
db/              Drizzle schema/migrations
config/          checkstyle/detekt/pmd
infra/ docs/     Terraform, MkDocs
```

## 9. Troubleshooting
- **`./gradlew` fails:** ensure Java 21 (`java -version`); wrapper is committed.
- **`uv sync --locked` fails:** run `uv lock` to refresh `uv.lock`.
- **`pnpm install --frozen-lockfile` fails:** run `pnpm install` to refresh `pnpm-lock.yaml`.
- **Port busy:** `make docker-down` or change the port in the app config.
- **Reset everything:** `make deep-clean && make bootstrap`.

## 10. CI
`.github/workflows/ci.yml` mirrors `make ci` (Nx affected + uv ruff/mypy/pytest + Gradle + gitleaks).
