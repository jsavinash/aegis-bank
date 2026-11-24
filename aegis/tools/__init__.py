"""Tool registry and MCP-style tool contracts.

Tools are grouped around intent, not one-to-one with API endpoints. Each tool
declares the roles allowed to call it and whether it is a side-effecting write.
"""

from __future__ import annotations

import json
from collections.abc import Callable
from dataclasses import dataclass, field
from typing import Any

from aegis.guardrails import get_guardrails
from aegis.telemetry import get_logger

log = get_logger("tools")


@dataclass
class Tool:
    name: str
    description: str
    handler: Callable[..., dict]
    roles: set[str] = field(default_factory=set)
    is_write: bool = False
    parameters: dict[str, Any] = field(default_factory=dict)

    def schema(self) -> dict:
        return {
            "name": self.name,
            "description": self.description,
            "input_schema": self.parameters or {"type": "object", "properties": {}},
            "roles": sorted(self.roles),
            "write": self.is_write,
        }


class ToolRegistry:
    def __init__(self):
        self._tools: dict[str, Tool] = {}

    def register(self, tool: Tool) -> None:
        self._tools[tool.name] = tool
        log.info("tool.registered", name=tool.name, roles=sorted(tool.roles))

    def get(self, name: str) -> Tool | None:
        return self._tools.get(name)

    def list(self, role: str | None = None) -> list[Tool]:
        if role is None:
            return list(self._tools.values())
        return [t for t in self._tools.values() if role in t.roles or not t.roles]

    def schemas(self, role: str) -> list[dict]:
        return [t.schema() for t in self.list(role)]

    async def call(self, role: str, name: str, args: dict, approved: bool = False) -> dict:
        tool = self.get(name)
        if tool is None:
            raise KeyError(f"unknown tool: {name}")
        call_args = dict(args)
        if approved:
            call_args["_approved"] = True
        res = get_guardrails().check_tool_call(role, name, call_args)
        get_guardrails().enforce(res)
        log.info("tool.call", tool=name, role=role)
        out = tool.handler(**call_args)
        # every tool result is untrusted: screen for injected instructions
        blob = json.dumps(out, default=str).lower()
        inj = get_guardrails().check_input(blob)
        if not inj.passed:
            log.warning("tool_result_flagged", tool=name, rule=inj.rule)
            out = {"_flagged": inj.rule, "result": out}
        return out


registry = ToolRegistry()


# ---------------------------------------------------------------------------
# Built-in tool handlers (deterministic stubs backed by fixtures / DB later).
# ---------------------------------------------------------------------------

_TRANSACTIONS = [
    {"id": "t1", "amount": 9800, "direction": "credit", "channel": "cash", "at": "2026-11-01T10:00:00Z"},
    {"id": "t2", "amount": 9600, "direction": "credit", "channel": "cash", "at": "2026-11-01T12:00:00Z"},
    {"id": "t3", "amount": 9950, "direction": "credit", "channel": "cash", "at": "2026-11-02T09:30:00Z"},
    {"id": "t4", "amount": 28500, "direction": "debit", "channel": "wire", "at": "2026-11-03T15:00:00Z"},
]


def _get_transactions(entity_id: str = "", window_days: int = 30, **_) -> dict:
    return {"entity_id": entity_id, "window_days": window_days, "transactions": _TRANSACTIONS}


def _get_kyc(entity_id: str = "", **_) -> dict:
    return {
        "entity_id": entity_id,
        "kyc_status": "verified",
        "pep": False,
        "risk_rating": "medium",
        "last_reviewed": "2025-06-01",
    }


def _screen_watchlist(name: str = "", **_) -> dict:
    return {"query": name, "sanctions_matches": [], "near_misses": [{"name": name, "score": 0.71}]}


def _get_prior_sars(entity_id: str = "", **_) -> dict:
    return {"entity_id": entity_id, "prior_sars": []}


def _search_policy(query: str = "", **_) -> dict:
    return {
        "query": query,
        "results": [
            {"ref": "AML-POL-4.2", "text": "Cash transactions aggregating > $10,000 in 24h require CTR."},
            {"ref": "AML-POL-6.1", "text": "Structuring indicators: multiple deposits just below threshold."},
        ],
    }


def _query_graph_neighbourhood(entity_id: str = "", depth: int = 2, **_) -> dict:
    return {
        "entity_id": entity_id,
        "depth": depth,
        "neighbours": [
            {"id": "acct-991", "relation": "shared_device", "chargeback": True},
            {"id": "acct-992", "relation": "shared_contact", "chargeback": False},
        ],
    }


def _get_device_history(device_id: str = "", **_) -> dict:
    return {"device_id": device_id, "cards": ["c1", "c2", "c3", "c4"], "chargebacked": 2}


def _get_entity(entity_id: str = "", **_) -> dict:
    return {"id": entity_id, "legal_name": "Acme Holdings LLC", "jurisdiction": "US"}


def _get_customer_360(entity_id: str = "", **_) -> dict:
    return {"entity_id": entity_id, "tenure_days": 900, "prior_chargebacks": 0, "avg_ticket": 120}


def _check_chargeback_history(entity_id: str = "", **_) -> dict:
    return {"entity_id": entity_id, "chargebacks_90d": 0}


def _search_obligations(query: str = "", **_) -> dict:
    return {
        "query": query,
        "obligations": [
            {"ref": "FATF-REC-10", "title": "Customer Due Diligence", "text": "Periodic review required."}
        ],
    }


def _get_control_library(**_) -> dict:
    return {"controls": [{"name": "KYC Periodic Review", "code_ref": "policy/kyc.md#review", "cadence": "12mo"}]}


def _diff_policy_version(path: str = "", **_) -> dict:
    return {"path": path, "diff": "cadence changed 12mo -> 6mo for high-risk"}


def _open_source_repo(path: str = "", **_) -> dict:
    return {"path": path, "modules": ["kyc.refresh", "risk.scoring"]}


def _file_sar(case_id: str = "", narrative: str = "", **_) -> dict:
    return {"case_id": case_id, "filed": True, "receipt": "SAR-2026-000123"}


def _freeze_account(entity_id: str = "", **_) -> dict:
    return {"entity_id": entity_id, "frozen": True}


def _register_builtin_tools() -> None:
    defs = [
        ("get_transactions", _get_transactions, {"evidence", "risk"}, False),
        ("get_kyc", _get_kyc, {"evidence"}, False),
        ("screen_watchlist", _screen_watchlist, {"evidence"}, False),
        ("get_prior_sars", _get_prior_sars, {"evidence"}, False),
        ("search_policy", _search_policy, {"evidence", "compliance"}, False),
        ("query_graph_neighbourhood", _query_graph_neighbourhood, {"network"}, False),
        ("get_device_history", _get_device_history, {"network", "risk"}, False),
        ("get_entity", _get_entity, {"network"}, False),
        ("get_customer_360", _get_customer_360, {"risk"}, False),
        ("check_chargeback_history", _check_chargeback_history, {"risk"}, False),
        ("search_obligations", _search_obligations, {"compliance"}, False),
        ("get_control_library", _get_control_library, {"compliance"}, False),
        ("diff_policy_version", _diff_policy_version, {"compliance"}, False),
        ("open_source_repo", _open_source_repo, {"compliance"}, False),
        ("file_sar", _file_sar, {"narrative"}, True),
        ("freeze_account", _freeze_account, {"risk"}, True),
    ]
    for name, handler, roles, is_write in defs:
        registry.register(Tool(name, name.replace("_", " "), handler, roles=set(roles), is_write=is_write))


_register_builtin_tools()