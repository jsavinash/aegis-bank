"""Model routing (cheap vs reasoning tiers)."""

from dataclasses import dataclass


@dataclass
class Route:
    tier: str
    model: str


ROUTES = {
    "fast": Route("fast", "gemini-4-hydrogen"),
    "balanced": Route("balanced", "gemini-4-neon"),
    "reasoning": Route("reasoning", "claude-opus-5.5"),
}

_STEP_TABLE = {
    "extract": "fast",
    "classify": "fast",
    "score": "fast",
    "reason": "reasoning",
    "narrate": "reasoning",
}


class ModelRouter:
    def route(self, complexity: str) -> Route:
        return ROUTES.get(complexity, ROUTES["balanced"])

    def for_step(self, step: str) -> Route:
        return self.route(_STEP_TABLE.get(step, "balanced"))