"""In-memory feature store with online/offline parity.

A single source of truth for feature values keyed by (entity, feature). The same
store backs both the online (low-latency lookup) and offline (training) paths,
which avoids train/serve skew.
"""

from dataclasses import dataclass, field


@dataclass
class FeatureStore:
    _online: dict[tuple[str, str], float] = field(default_factory=dict)

    def set(self, entity: str, feature: str, value: float) -> None:
        self._online[(entity, feature)] = float(value)

    def get(self, entity: str, feature: str, default: float = 0.0) -> float:
        return self._online.get((entity, feature), default)

    def get_many(self, entity: str, features: list[str]) -> dict[str, float]:
        return {f: self.get(entity, f) for f in features}

    def entity_features(self, entity: str) -> dict[str, float]:
        return {f: v for (e, f), v in self._online.items() if e == entity}

    def all(self) -> dict[tuple[str, str], float]:
        return dict(self._online)