from .settings import settings

def run() -> str:
    return 'aegis'

def health() -> dict:
    return {'ok': True, 'level': settings.log_level}
