.PHONY: install dev test lint format serve seed migrate up down

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