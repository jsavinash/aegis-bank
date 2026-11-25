from fastapi.testclient import TestClient

from aegis.app import create_app

c = TestClient(create_app())


def test_health():
    assert c.get("/health").json()["ok"] is True


def test_fraud_score_and_decide():
    r = c.post("/fraud/score", json={"signals": {"velocity_1h": 0}})
    assert r.status_code == 200 and r.json()["level"] == "low"

    r = c.post("/fraud/decide", json={"signals": {"high_risk_jurisdiction": True}})
    assert r.json()["verdict"] in {"approve", "step_up", "decline"}


def test_aml_triage_escalates_on_structuring():
    r = c.post(
        "/aml/triage",
        json={
            "signals": {
                "structuring": True,
                "high_risk_jurisdiction": True,
                "cash_intensity": 0.9,
                "amount": 9000,
                "currency": "USD",
            }
        },
    )
    body = r.json()
    assert r.status_code == 200 and body["verdict"] == "escalate"


def test_case_lifecycle():
    created = c.post("/cases", json={"alert_id": "00000000-0000-0000-0000-000000000001"})
    case_id = created.json()["id"]
    assert created.status_code == 201

    r = c.post(f"/cases/{case_id}/transition", json={"target": "investigating"})
    assert r.json()["status"] == "investigating"

    r = c.post(f"/cases/{case_id}/transition", json={"target": "filed"})
    assert r.json()["status"] == "filed"

    # illegal transition closed -> investigating
    c.post(f"/cases/{case_id}/transition", json={"target": "closed"})
    r = c.post(f"/cases/{case_id}/transition", json={"target": "investigating"})
    assert r.status_code == 409


def test_case_not_found():
    r = c.get("/cases/00000000-0000-0000-0000-00000000dead")
    assert r.status_code == 404


def test_kyc_workflow():
    created = c.post("/kyc", json={"customer_id": "cust-1"})
    case_id = created.json()["id"]
    assert created.json()["status"] == "pending"

    c.post(f"/kyc/{case_id}/documents", json={"document": "passport.pdf"})
    assert c.post(f"/kyc/{case_id}/review").json()["status"] == "in_review"
    assert c.post(f"/kyc/{case_id}/verify").json()["status"] == "verified"
    # verified is terminal
    assert c.post(f"/kyc/{case_id}/reject").status_code == 409