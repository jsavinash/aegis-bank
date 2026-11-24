"""Security: JWT auth, RBAC, principal signing."""

from __future__ import annotations

import hashlib
import hmac
from datetime import UTC, datetime, timedelta

from aegis.config import get_settings

try:
    from jose import jwt

    _HAS_JOSE = True
except Exception:  # pragma: no cover
    _HAS_JOSE = False


class Principal:
    def __init__(self, sub: str, roles: list[str], scopes: list[str] | None = None):
        self.sub = sub
        self.roles = roles
        self.scopes = scopes or []

    def has_role(self, role: str) -> bool:
        return role in self.roles

    def can(self, scope: str) -> bool:
        return scope in self.scopes or "*" in self.scopes


ROLE_SCOPES: dict[str, set[str]] = {
    "analyst": {"case:read", "case:write", "risk:read", "compliance:read", "eval:run"},
    "senior_analyst": {"case:read", "case:write", "case:approve", "risk:read", "risk:approve", "compliance:read", "eval:run"},
    "admin": {"*"},
    "auditor": {"case:read", "audit:read", "compliance:read"},
    "agent": {"case:write", "risk:read", "compliance:read", "tool:call"},
}


def create_access_token(principal: Principal) -> str:
    settings = get_settings()
    now = datetime.now(UTC)
    payload = {
        "sub": principal.sub,
        "roles": principal.roles,
        "scopes": principal.scopes,
        "iat": int(now.timestamp()),
        "exp": int((now + timedelta(seconds=settings.access_token_ttl_seconds)).timestamp()),
    }
    if _HAS_JOSE:
        return jwt.encode(payload, settings.secret_key, algorithm="HS256")
    return _unsigned_token(payload)


def decode_access_token(token: str) -> Principal:
    settings = get_settings()
    if _HAS_JOSE:
        payload = jwt.decode(token, settings.secret_key, algorithms=["HS256"])
    else:
        payload = _verify_token(token)
    return Principal(payload["sub"], payload.get("roles", []), payload.get("scopes", []))


def sign_principal(principal: Principal) -> str:
    """Short-lived signed agent identity (attaches actions to a named principal)."""
    raw = f"{principal.sub}:{','.join(principal.roles)}"
    sig = hmac.new(get_settings().secret_key.encode(), raw.encode(), hashlib.sha256).hexdigest()
    return f"{raw}:{sig}"


def verify_principal(signed: str) -> bool:
    try:
        sub, roles, sig = signed.rsplit(":", 2)
    except ValueError:
        return False
    raw = f"{sub}:{roles}"
    expect = hmac.new(get_settings().secret_key.encode(), raw.encode(), hashlib.sha256).hexdigest()
    return hmac.compare_digest(sig, expect)


# ---- fallback token impl (dev only; never use in prod without jose) ----
def _b64(data: dict) -> str:
    import base64
    import json

    return base64.urlsafe_b64encode(json.dumps(data).encode()).decode().rstrip("=")


def _unsigned_token(payload: dict) -> str:
    return _b64(payload)


def _verify_token(token: str) -> dict:
    import base64
    import json

    padded = token + "=" * (-len(token) % 4)
    return json.loads(base64.urlsafe_b64decode(padded.encode()))