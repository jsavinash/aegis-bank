"""FastAPI application factory for the Aegis AI plane."""

from fastapi import FastAPI

from .api import aml, cases, fraud, kyc
from .settings import settings


def create_app() -> FastAPI:
    app = FastAPI(title="Aegis AI Plane", version="0.1.0")
    app.include_router(fraud.router)
    app.include_router(aml.router)
    app.include_router(cases.router)
    app.include_router(kyc.router)

    @app.get("/health")
    def health() -> dict:
        return {"ok": True, "level": settings.log_level}

    return app


app = create_app()