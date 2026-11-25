# Polygot Starter Kit (production-grade Nx monorepo)

Nx + **Bun/TypeScript** + **uv/Python** + **Gradle/Java**, one task graph.

Layouts follow:
- Java: https://github.com/jsavinash/java-starter-kit
- Python: https://github.com/jsavinash/python-starter-kit
- MCP: https://github.com/jsavinash/mcp-starter-kit
- TypeScript: https://github.com/jsavinash/javascript-starter-kit

## Quick start
```bash
npx nx@latest init
bun install
uv sync --all-packages
./gradlew build
bash scripts/install-hooks.sh
nx graph
```

## CI
`.github/workflows/ci.yml` runs `nx affected -t build test lint`, `uv run ruff/mypy/pytest`, and `./gradlew build`.
