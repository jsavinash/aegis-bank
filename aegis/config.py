"""Application configuration loaded from environment / .env."""

from functools import lru_cache

from pydantic_settings import BaseSettings, SettingsConfigDict


class Settings(BaseSettings):
    model_config = SettingsConfigDict(env_file=".env", env_file_encoding="utf-8", extra="ignore")

    app_name: str = "aegis"
    env: str = "dev"
    debug: bool = True
    secret_key: str = "change-me"
    access_token_ttl_seconds: int = 3600

    database_url: str = "postgresql+asyncpg://aegis:aegis@localhost:5432/aegis"
    redis_url: str = "redis://localhost:6379/0"

    llm_provider: str = "mock"
    openai_api_key: str = ""
    anthropic_api_key: str = ""
    llm_routing: str = "tiered"

    neo4j_uri: str = "bolt://localhost:7687"
    neo4j_user: str = "neo4j"
    neo4j_password: str = "password"

    temporal_address: str = "localhost:7233"
    temporal_namespace: str = "default"

    otel_enabled: bool = False
    otel_exporter_otlp_endpoint: str = "http://localhost:4317"

    guardrails_fail_closed: bool = True
    guardrails_nemo_enabled: bool = True

    rag_hybrid_top_k: int = 20
    rag_rerank_top_n: int = 8
    rag_graph_enabled: bool = True

    @property
    def is_prod(self) -> bool:
        return self.env.lower() in {"prod", "production"}


@lru_cache
def get_settings() -> Settings:
    return Settings()