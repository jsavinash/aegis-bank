"""Typed domain exceptions."""


class AegisError(Exception):
    """Base error for the Aegis domain."""


class CaseError(AegisError):
    """Raised when a case operation is invalid."""


class InvalidTransition(CaseError):
    """Raised on an illegal case-state transition."""


class GuardrailError(AegisError):
    """Raised when a guardrail blocks an action."""