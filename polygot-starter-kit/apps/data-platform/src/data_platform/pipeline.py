"""Data transform primitives: dedupe, aggregation, and windowed metrics."""

from collections import defaultdict
from typing import Iterable


def dedupe(items: Iterable, key) -> list:
    """Return items with unique keys, preserving first-seen order."""
    seen = set()
    out = []
    for item in items:
        k = key(item)
        if k in seen:
            continue
        seen.add(k)
        out.append(item)
    return out


def aggregate_by_category(transactions: Iterable[dict]) -> dict[str, float]:
    """Sum transaction amounts grouped by category."""
    totals: dict[str, float] = defaultdict(float)
    for txn in transactions:
        totals[txn["category"]] += float(txn["amount"])
    return dict(totals)


def moving_average(values: Iterable[float], window: int) -> list[float]:
    """Simple trailing moving average; early points use what is available."""
    if window <= 0:
        raise ValueError("window must be positive")
    buf: list[float] = []
    out: list[float] = []
    for v in values:
        buf.append(float(v))
        if len(buf) > window:
            buf.pop(0)
        out.append(sum(buf) / len(buf))
    return out