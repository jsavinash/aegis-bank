.PHONY: install dev test lint format serve seed migrate up down \
        java-build java-test java-run java-quality java-docker

install:
	uv sync --extra dev

dev:
	uv sync --extra dev && uv run pre-commit install || true

test:
	uv run pytest -q

test-cov:
	uv run pytest --cov=aegis --cov-report=term-missing

lint:
	uv run ruff check aegis tests

format:
	uv run ruff format aegis tests
	uv run ruff check --fix aegis tests

serve:
	uv run uvicorn aegis.api:app --reload --host 0.0.0.0 --port 8000

seed:
	uv run aegis seed

up:
	docker compose up -d postgres redis

down:
	docker compose down

migrate:
	uv run alembic upgrade head

# --- Java microservices (Gradle via SDKMAN, in polygot-starter-kit) ---
java-build:
	cd polygot-starter-kit && if [ -f "$$HOME/.sdkman/bin/sdkman-init.sh" ]; then source "$$HOME/.sdkman/bin/sdkman-init.sh"; sdk env install; fi; ./gradlew clean build

java-test:
	cd polygot-starter-kit && if [ -f "$$HOME/.sdkman/bin/sdkman-init.sh" ]; then source "$$HOME/.sdkman/bin/sdkman-init.sh"; sdk env install; fi; ./gradlew test

java-run:
	cd polygot-starter-kit && if [ -f "$$HOME/.sdkman/bin/sdkman-init.sh" ]; then source "$$HOME/.sdkman/bin/sdkman-init.sh"; sdk env install; fi; ./gradlew -p apps/micro-services payments:bootRun

java-quality:
	cd polygot-starter-kit && if [ -f "$$HOME/.sdkman/bin/sdkman-init.sh" ]; then source "$$HOME/.sdkman/bin/sdkman-init.sh"; sdk env install; fi; ./gradlew spotlessApply check

java-docker:
	cd polygot-starter-kit && if [ -f "$$HOME/.sdkman/bin/sdkman-init.sh" ]; then source "$$HOME/.sdkman/bin/sdkman-init.sh"; sdk env install; fi; ./gradlew -p apps/micro-services payments:jibDockerBuild