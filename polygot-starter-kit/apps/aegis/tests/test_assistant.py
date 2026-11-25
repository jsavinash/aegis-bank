from fastapi.testclient import TestClient
from aegis.assistant import app

c = TestClient(app)


def test_health():
    assert c.get("/health").json()["ok"] is True


def test_query_answers():
    r = c.post("/assistant/query", json={"text": "what is my balance"})
    assert r.status_code == 200 and r.json()["escalate"] is False


def test_query_escalates():
    r = c.post("/assistant/query", json={"text": "my card was lost"})
    assert r.json()["escalate"] is True
