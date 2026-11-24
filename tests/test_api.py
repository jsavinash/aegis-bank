from fastapi.testclient import TestClient

from aegis.api import app

client = TestClient(app)


def test_root():
    r = client.get("/")
    assert r.status_code == 200
    assert "capabilities" in r.json()


def test_health():
    r = client.get("/health")
    assert r.status_code == 200


def test_case_flow():
    r = client.post("/api/v1/cases?alert_type=structuring")
    assert r.status_code == 201
    case_id = r.json()["case_id"]

    r = client.get(f"/api/v1/cases/{case_id}")
    assert r.status_code == 200

    r = client.post(f"/api/v1/cases/{case_id}/investigate")
    assert r.status_code == 200
    assert r.json()["evidence"]


def test_risk_score():
    r = client.post("/api/v1/risk/score", json={"amount": 1840, "velocity_1h": 3, "device_shared_count": 4})
    assert r.status_code == 200
    body = r.json()
    assert "score" in body and "decision" in body


def test_compliance():
    r = client.get("/api/v1/compliance/obligations")
    assert r.status_code == 200
    assert len(r.json()) >= 1


def test_eval_run():
    r = client.post("/api/v1/eval/run", json={"suite": "smoke", "min_accuracy": 0.0})
    assert r.status_code == 200
    assert "result" in r.json()