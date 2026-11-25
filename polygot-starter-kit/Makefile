.DEFAULT_GOAL := help
SHELL := /bin/bash

.PHONY: help tools locks install bootstrap dev build test lint format typecheck ci \
        run-web run-api run-app run-aegis run-mcp run-data run-java java-sync \
        docker-build docker-up docker-down clean deep-clean hooks docs security sbom

help: ## Show this help
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN{FS=":.*?## "}{printf "  \033[36m%-16s\033[0m %s\n",$$1,$$2}'

tools: ## Install missing toolchains (portable Node + JDK + uv)
	bash scripts/install-tools.sh
	@if [ -f .tools/env.sh ]; then . .tools/env.sh; fi

locks: ## Generate pnpm-lock.yaml + uv.lock
	pnpm install --lockfile-only || true
	uv lock || true

install: ## Install all dependencies (pnpm + uv)
	@. .tools/env.sh 2>/dev/null || true; if command -v pnpm >/dev/null; then pnpm install; else echo "pnpm not found - install: https://pnpm.io"; fi
	@. .tools/env.sh 2>/dev/null || true; if command -v uv >/dev/null; then uv sync --all-packages; else echo "uv not found - install: https://docs.astral.sh/uv"; fi

bootstrap: tools install hooks java-sync ## One-time setup (tools + deps + gradle sync + hooks)

hooks: ## Install git hooks
	bash scripts/install-hooks.sh

dev: ## Run all dev servers (web, api, app)
	npx nx run-many -t dev

java-sync: ## Gradle sync (SDKMAN + resolve dependencies)
	. .tools/env.sh 2>/dev/null || true; if [ -f "$$HOME/.sdkman/bin/sdkman-init.sh" ]; then source "$$HOME/.sdkman/bin/sdkman-init.sh"; sdk env install; fi; java -version >/dev/null 2>&1 || { echo "java not found - install JDK 21 or SDKMAN"; exit 0; }; ./gradlew dependencies

build: ## Build everything
	. .tools/env.sh 2>/dev/null || true; npx nx run-many -t build && if [ -f "$$HOME/.sdkman/bin/sdkman-init.sh" ]; then source "$$HOME/.sdkman/bin/sdkman-init.sh"; sdk env install; fi; ./gradlew build

test: ## Run all tests (TS + Python + Java)
	. .tools/env.sh 2>/dev/null || true; npx nx run-many -t test
	uv run pytest
	./gradlew test

lint: ## Lint everything
	npx nx run-many -t lint && uv run ruff check .

format: ## Format everything
	uv run ruff format . && pnpm format

typecheck: ## Type-check everything
	npx nx run-many -t typecheck && uv run mypy .

ci: ## Local CI mirror (what CI runs)
	npx nx affected -t build test lint && uv run ruff check . && uv run mypy . && uv run pytest && if [ -f "$$HOME/.sdkman/bin/sdkman-init.sh" ]; then source "$$HOME/.sdkman/bin/sdkman-init.sh"; sdk env install; fi; ./gradlew build

run-web: ## Run Astro marketing site
	pnpm --filter @aegis/web dev
run-api: ## Run tRPC/Hono API
	pnpm --filter @aegis/api dev
run-app: ## Run React app
	pnpm --filter @aegis/app dev
run-aegis: ## Run Python aegis service
	uv run --package aegis aegis
run-mcp: ## Run MCP server (stdio)
	uv run --package mcp-aegis mcp-aegis
run-data: ## Run data-platform
	uv run --package data-platform python -m data_platform
run-java: ## Run Java micro-services (SDKMAN)
	. .tools/env.sh 2>/dev/null || true; if [ -f "$$HOME/.sdkman/bin/sdkman-init.sh" ]; then source "$$HOME/.sdkman/bin/sdkman-init.sh"; sdk env install; fi; ./gradlew -p apps/micro-services payments:bootRun

docker-build: ## Build docker images
	docker compose build
docker-up: ## Start docker compose (redis + services)
	docker compose up -d
docker-down: ## Stop docker compose
	docker compose down

docs: ## Build docs
	mkdocs build
security: ## Security scans
	uv run pip-audit || true
sbom: ## Generate SBOM
	command -v syft >/dev/null && syft . -o spdx-json > sbom.spdx.json || echo "install syft"

clean: ## Clean build artifacts
	rm -rf **/dist **/build **/.pytest_cache **/__pycache__ .nx
deep-clean: clean ## Also remove deps/venv
	rm -rf node_modules .venv
