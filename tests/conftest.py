import asyncio

import pytest


@pytest.fixture
def anyio_backend():
    return "asyncio"


@pytest.fixture(autouse=True)
def _offline_llm(monkeypatch):
    monkeypatch.setenv("LLM_PROVIDER", "mock")
    from aegis.config import get_settings

    get_settings.cache_clear()
    yield
    get_settings.cache_clear()