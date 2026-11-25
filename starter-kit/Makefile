.PHONY: install lint format typecheck test run-stdio run-http security docs
install: ; uv sync --all-packages
lint: ; uv run ruff check .
format: ; uv run ruff format .
typecheck: ; uv run mypy .
test: ; uv run pytest
run-stdio: ; uv run --package mcp-aegis mcp-aegis
run-http: ; MCP_TRANSPORT=streamable-http uv run --package mcp-aegis mcp-aegis
security: ; uv run pip-audit
docs: ; mkdocs build
