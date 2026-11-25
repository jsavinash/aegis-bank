"""Typed domain exceptions for the Aegis AI plane."""


class AegisError(Exception):
    """Base error for the Aegis domain."""


class CaseError(AegisError):
    """Raised when a case operation is invalid."""


class InvalidTransition(CaseError):
    """Raised on an illegal case-state transition."""


class CaseNotFound(CaseError):
    """Raised when a case does not exist."""


class GuardrailError(AegisError):
    """Raised when a guardrail blocks an action."""