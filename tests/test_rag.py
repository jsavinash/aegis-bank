from aegis.rag import GraphStore, HybridRetriever, Router, get_graph


def test_bm25_retrieves_relevant():
    docs = [
        {"ref": "a", "content": "cash deposits just below ten thousand dollars structuring"},
        {"ref": "b", "content": "customer onboarding kyc verification process"},
    ]
    r = HybridRetriever().retrieve("structuring cash deposits", docs)
    assert r and r[0].ref == "a"


def test_graph_respects_temporal_validity():
    g = GraphStore()
    g.add_node("a")
    g.add_node("b")
    g.add_edge("a", "b", "counterparty", valid_to="2026-01-01")
    assert g.neighbourhood("a", as_of="2026-11-24") == []  # expired edge hidden


def test_router_graph_hint():
    assert Router().should_use_graph("which controls are affected across all product lines")
    assert not Router().should_use_graph("what is the kyc status of entity 1")


def test_seeded_graph():
    n = get_graph().neighbourhood("entity-1", depth=1)
    assert any(x["id"] == "acct-991" for x in n)