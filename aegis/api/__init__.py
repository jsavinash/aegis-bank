"""FastAPI application factory + middleware."""

from __future__ import annotations

from contextlib import asynccontextmanager

from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

from aegis.api.routers import alerts, cases, compliance, entities, eval_, risk
from aegis.config import get_settings
from aegis.db import close_db, health_check, init_db
from aegis.telemetry import configure_logging, configure_tracing, get_logger

log = get_logger("api")


@asynccontextmanager
async def lifespan(app: FastAPI):
    settings = get_settings()
    configure_logging("DEBUG" if settings.debug else "INFO")
    configure_tracing(settings.otel_enabled, settings.otel_exporter_otlp_endpoint)
    try:
        await init_db()
    except Exception as e:
        log.warning("db_init_failed", error=str(e))
    log.info("api.start", env=settings.env)
    yield
    await close_db()
    log.info("api.stop")


def create_app() -> FastAPI:
    settings = get_settings()
    app = FastAPI(
        title="Aegis — AI-native financial crime compliance platform",
        version="0.1.0",
        debug=settings.debug,
        lifespan=lifespan,
        docs_url="/docs",
        redoc_url="/redoc",
    )
    app.add_middleware(
        CORSMiddleware,
        allow_origins=["*"] if settings.debug else [],
        allow_credentials=True,
        allow_methods=["*"],
        allow_headers=["*"],
    )

    app.include_router(cases.router, prefix="/api/v1", tags=["cases"])
    app.include_router(alerts.router, prefix="/api/v1", tags=["alerts"])
    app.include_router(entities.router, prefix="/api/v1", tags=["entities"])
    app.include_router(risk.router, prefix="/api/v1", tags=["risk"])
    app.include_router(compliance.router, prefix="/api/v1", tags=["compliance"])
    app.include_router(eval_.router, prefix="/api/v1", tags=["eval"])

    @app.get("/health", tags=["system"])
    async def health():
        db = await health_check()
        return {"status": "ok" if db else "degraded", "db": db, "env": settings.env}

    @app.get("/", tags=["system"])
    async def root():
        return {
            "name": "Aegis",
            "description": "AI-native financial crime compliance platform",
            "version": "0.1.0",
            "docs": "/docs",
            "capabilities": [
                "aml_investigation",
                "real_time_risk",
                "regulatory_intelligence",
                "agentic_orchestration",
                "hybrid_rag",
                "graphrag",
                "guardrails",
                "evaluation",
            ],
        }

    return app


app = create_app()