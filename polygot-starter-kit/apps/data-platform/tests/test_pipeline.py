from data_platform.features import FeatureStore
from data_platform.pipeline import aggregate_by_category, dedupe, moving_average


def test_dedupe_preserves_order():
    items = [{"id": "a"}, {"id": "b"}, {"id": "a"}]
    assert [i["id"] for i in dedupe(items, key=lambda x: x["id"])] == ["a", "b"]


def test_aggregate_by_category():
    txns = [
        {"category": "food", "amount": 10.0},
        {"category": "food", "amount": 5.0},
        {"category": "rent", "amount": 100.0},
    ]
    assert aggregate_by_category(txns) == {"food": 15.0, "rent": 100.0}


def test_moving_average():
    assert moving_average([1, 2, 3], window=2) == [1.0, 1.5, 2.5]


def test_moving_average_rejects_nonpositive_window():
    import pytest

    with pytest.raises(ValueError):
        moving_average([1], window=0)


def test_feature_store_parity():
    store = FeatureStore()
    store.set("c1", "velocity_1h", 3.0)
    assert store.get("c1", "velocity_1h") == 3.0
    assert store.get("c1", "missing", default=-1.0) == -1.0
    assert store.get_many("c1", ["velocity_1h", "missing"]) == {
        "velocity_1h": 3.0,
        "missing": 0.0,
    }
    assert store.entity_features("c1") == {"velocity_1h": 3.0}