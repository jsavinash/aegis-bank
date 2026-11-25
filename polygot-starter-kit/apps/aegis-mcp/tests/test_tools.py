from mcp_aegis.features.tools import aml_triage, fraud_score, kyc_transition
from mcp_aegis.server import build_server


def test_fraud_score_low():
    r = fraud_score({})
    assert r["level"] == "low" and 0.0 <= r["value"] <= 1.0


def test_fraud_score_high_jurisdiction_raises():
    base = fraud_score({})["value"]
    raised = fraud_score({"high_risk_jurisdiction": True})["value"]
    assert raised > base


def test_aml_triage_escalates():
    r = aml_triage(
        {"structuring": True, "high_risk_jurisdiction": True, "cash_intensity": 0.9}
    )
    assert r["verdict"] == "escalate"


def test_kyc_transition_valid_and_invalid():
    assert kyc_transition("pending", "in_review")["ok"] is True
    bad = kyc_transition("verified", "rejected")
    assert bad["ok"] is False


def test_server_registers_tools():
    server = build_server()
    import asyncio

    tools = asyncio.run(server.list_tools())
    names = {t.name for t in tools}
    assert {"fraud_score", "aml_triage", "kyc_transition"} <= names