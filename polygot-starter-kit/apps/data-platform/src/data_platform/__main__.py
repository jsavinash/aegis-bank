"""CLI entry point: run a small demo pipeline over sample transactions."""

from .features import FeatureStore
from .pipeline import aggregate_by_category, dedupe, moving_average

SAMPLE = [
    {"id": "t1", "category": "groceries", "amount": 50.0},
    {"id": "t2", "category": "dining", "amount": 25.0},
    {"id": "t1", "category": "groceries", "amount": 50.0},
    {"id": "t3", "category": "groceries", "amount": 30.0},
]


def main() -> None:
    unique = dedupe(SAMPLE, key=lambda t: t["id"])
    by_category = aggregate_by_category(unique)
    trend = moving_average([t["amount"] for t in unique], window=2)

    store = FeatureStore()
    for category, total in by_category.items():
        store.set("demo", f"spend_{category}", total)

    print("data-platform demo")
    print("unique transactions:", len(unique))
    print("by category:", by_category)
    print("moving average:", [round(x, 2) for x in trend])
    print("feature store:", store.entity_features("demo"))


if __name__ == "__main__":
    main()