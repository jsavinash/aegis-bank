"""Model routing (cheap vs reasoning)."""

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


class ModelRouter:
    def route(self, complexity: str) -> Route:
        return ROUTES.get(complexity, ROUTES["balanced"])

    def for_step(self, step: str) -> Route:
        table = {
            "extract": "fast",
            "classify": "fast",
            "reason": "reasoning",
            "narrate": "reasoning",
        }
        return self.route(table.get(step, "balanced"))