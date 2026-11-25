.PHONY: install lint format typecheck test ci run-stdio run-http security docs docker java
install: ; bun install --frozen-lockfile && uv sync --locked --all-packages
lint: ; nx run-many -t lint && uv run ruff check .
format: ; uv run ruff format . && bun run format
typecheck: ; nx run-many -t typecheck && uv run mypy .
test: ; nx run-many -t test && uv run pytest
ci: ; nx affected -t build test lint && uv run ruff check . && uv run mypy . && uv run pytest
java: ; ./gradlew build
run-stdio: ; uv run --package mcp-aegis mcp-aegis
run-http: ; MCP_TRANSPORT=streamable-http uv run --package mcp-aegis mcp-aegis
security: ; uv run pip-audit
docs: ; mkdocs build
docker: ; docker compose build
