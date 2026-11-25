from .app import app, create_app
from .settings import settings


def run() -> str:
    return 'aegis'


def health() -> dict:
    return {'ok': True, 'level': settings.log_level}


__all__ = ['app', 'create_app', 'run', 'health']