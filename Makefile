.PHONY: install dev test lint format serve seed migrate up down \
        java-build java-test java-run java-quality java-docker

install:
	pip install -e ".[dev]"

dev:
	pip install -e ".[dev]" && pre-commit install || true

test:
	pytest -q

test-cov:
	pytest --cov=aegis --cov-report=term-missing

lint:
	ruff check aegis tests

format:
	ruff format aegis tests
	ruff check --fix aegis tests

serve:
	uvicorn aegis.api:app --reload --host 0.0.0.0 --port 8000

seed:
	aegis seed

up:
	docker compose up -d postgres redis

down:
	docker compose down

migrate:
	alembic upgrade head

# --- Java microservices (Gradle, in polygot-starter-kit) ---
java-build:
	cd polygot-starter-kit && ./gradlew clean build

java-test:
	cd polygot-starter-kit && ./gradlew test

java-run:
	cd polygot-starter-kit && ./gradlew -p apps/micro-services payments:bootRun

java-quality:
	cd polygot-starter-kit && ./gradlew spotlessApply check

java-docker:
	cd polygot-starter-kit && ./gradlew -p apps/micro-services payments:jibDockerBuild