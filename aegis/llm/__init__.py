"""LLM abstraction: multi-provider routing, structured output, token budgets."""

from __future__ import annotations

import json
from dataclasses import dataclass, field
from typing import TypeVar

from pydantic import BaseModel

from aegis.config import get_settings
from aegis.telemetry import get_logger

log = get_logger("llm")

T = TypeVar("T", bound=BaseModel)


class LLMError(Exception):
    pass


class BudgetExceeded(LLMError):
    pass


@dataclass
class Usage:
    input_tokens: int = 0
    output_tokens: int = 0
    cost_usd: float = 0.0

    def __add__(self, other: Usage) -> Usage:
        return Usage(
            self.input_tokens + other.input_tokens,
            self.output_tokens + other.output_tokens,
            self.cost_usd + other.cost_usd,
        )


@dataclass
class Budget:
    max_requests: int = 100
    max_input_tokens: int = 1_000_000
    max_output_tokens: int = 250_000
    max_cost_usd: float = 50.0
    usage: Usage = field(default_factory=Usage)
    requests: int = 0

    def check(self) -> None:
        if self.requests >= self.max_requests:
            raise BudgetExceeded("request budget exhausted")
        if self.usage.input_tokens >= self.max_input_tokens:
            raise BudgetExceeded("input token budget exhausted")
        if self.usage.output_tokens >= self.max_output_tokens:
            raise BudgetExceeded("output token budget exhausted")
        if self.usage.cost_usd >= self.max_cost_usd:
            raise BudgetExceeded("cost budget exhausted")

    def record(self, usage: Usage) -> None:
        self.requests += 1
        self.usage = self.usage + usage


@dataclass
class ChatMessage:
    role: str  # system | user | assistant | tool
    content: str


# Rough price table ($ per 1M tokens), used for budget accounting in mock/offline mode.
_PRICES = {
    "mock": (0.0, 0.0),
    "claude-opus-5.5": (8.0, 40.0),
    "claude-sonnet-5.5": (4.0, 20.0),
    "gemini-4-hydrogen": (1.0, 3.0),
    "gemini-4-neon": (6.0, 24.0),
    "gpt-6-terra": (2.0, 8.0),
}


def _price(model: str) -> tuple[float, float]:
    return _PRICES.get(model, (5.0, 20.0))


class BaseLLM:
    def __init__(self, model: str = "mock", temperature: float = 0.0):
        self.model = model
        self.temperature = temperature

    def _cost(self, usage: Usage) -> float:
        pin, pout = _price(self.model)
        return (usage.input_tokens / 1e6) * pin + (usage.output_tokens / 1e6) * pout

    def complete(self, messages: list[ChatMessage], **kw) -> tuple[str, Usage]:
        raise NotImplementedError

    def structured(
        self,
        messages: list[ChatMessage],
        schema: type[T],
        budget: Budget | None = None,
    ) -> T:
        raise NotImplementedError


class MockLLM(BaseLLM):
    """Deterministic offline model so the platform runs with zero external deps.

    All methods are synchronous and do not touch the event loop, so they are safe
    under asyncio.run, TestClient worker threads, and plain sync tests alike.
    """

    def _generate(self, messages: list[ChatMessage]) -> str:
        prompt = " ".join(m.content for m in messages).lower()
        if "citation" in prompt or "narrative" in prompt:
            return "Behaviour is consistent with structuring. [t1][t2][t3]"
        if "structuring" in prompt:
            return "structuring typology indicated: multiple deposits just below reporting threshold."
        if "mule" in prompt or "network" in prompt:
            return "entity shares device/contact graph with 2 known mule accounts."
        if "sanction" in prompt:
            return "no direct sanctions match; 1 near-miss on transliterated name."
        return "analysis complete: behaviour consistent with low-to-medium risk."

    def complete(self, messages: list[ChatMessage], **kw) -> tuple[str, Usage]:
        out = self._generate(messages)
        prompt = " ".join(m.content for m in messages)
        usage = Usage(input_tokens=len(prompt) // 4, output_tokens=len(out) // 4, cost_usd=0.0)
        return out, usage

    async def acomplete(self, messages: list[ChatMessage], **kw) -> tuple[str, Usage]:
        return self.complete(messages, **kw)

    def structured(self, messages, schema: type[T], budget: Budget | None = None) -> T:
        text, usage = self.complete(messages)
        if budget:
            budget.record(usage)
        return _coerce_for_schema(text, schema)


class OpenAILLM(BaseLLM):
    def __init__(self, model: str = "gpt-6-terra", api_key: str | None = None, **kw):
        super().__init__(model, **kw)
        from openai import OpenAI

        self.client = OpenAI(api_key=api_key or get_settings().openai_api_key)

    def complete(self, messages: list[ChatMessage], **kw) -> tuple[str, Usage]:
        resp = self.client.chat.completions.create(
            model=self.model,
            temperature=self.temperature,
            messages=[{"role": m.role, "content": m.content} for m in messages],
        )
        text = resp.choices[0].message.content or ""
        u = resp.usage
        usage = Usage(
            input_tokens=getattr(u, "prompt_tokens", 0),
            output_tokens=getattr(u, "completion_tokens", 0),
        )
        usage.cost_usd = self._cost(usage)
        return text, usage

    def structured(self, messages, schema: type[T], budget: Budget | None = None) -> T:
        resp = self.client.chat.completions.create(
            model=self.model,
            temperature=self.temperature,
            messages=[{"role": m.role, "content": m.content} for m in messages],
            response_format={"type": "json_schema", "json_schema": _json_schema(schema)},
        )
        text = resp.choices[0].message.content or "{}"
        u = resp.usage
        usage = Usage(getattr(u, "prompt_tokens", 0), getattr(u, "completion_tokens", 0))
        usage.cost_usd = self._cost(usage)
        if budget:
            budget.record(usage)
        return schema.model_validate_json(text)


class AnthropicLLM(BaseLLM):
    def __init__(self, model: str = "claude-opus-5.5", api_key: str | None = None, **kw):
        super().__init__(model, **kw)
        from anthropic import Anthropic

        self.client = Anthropic(api_key=api_key or get_settings().anthropic_api_key)

    def complete(self, messages: list[ChatMessage], **kw) -> tuple[str, Usage]:
        system = " ".join(m.content for m in messages if m.role == "system")
        convo = [m for m in messages if m.role != "system"]
        resp = self.client.messages.create(
            model=self.model,
            max_tokens=kw.get("max_tokens", 4096),
            system=system or None,
            messages=[{"role": m.role, "content": m.content} for m in convo],
        )
        text = "".join(b.text for b in resp.content if getattr(b, "text", None))
        usage = Usage(
            input_tokens=resp.usage.input_tokens,
            output_tokens=resp.usage.output_tokens,
        )
        usage.cost_usd = self._cost(usage)
        return text, usage

    def structured(self, messages, schema: type[T], budget: Budget | None = None) -> T:
        messages = list(messages) + [
            ChatMessage(
                "user",
                f"Return ONLY JSON matching this schema:\n{json.dumps(_json_schema(schema))}",
            )
        ]
        text, usage = self.complete(messages)
        if budget:
            budget.record(usage)
        return _coerce(text, schema)


def _json_schema(schema: type[BaseModel]) -> dict:
    return {
        "name": schema.__name__,
        "schema": schema.model_json_schema(),
        "strict": True,
    }


def _coerce[T: BaseModel](text: str, schema: type[T]) -> T:
    text = text.strip()
    if text.startswith("```"):
        text = text.strip("`")
        text = text[text.find("{") :] if "{" in text else text
    try:
        return schema.model_validate_json(text)
    except Exception:
        start, end = text.find("{"), text.rfind("}")
        if start != -1 and end != -1:
            return schema.model_validate_json(text[start : end + 1])
        raise


def _coerce_for_schema[T: BaseModel](text: str, schema: type[T]) -> T:
    """Offline mock: build a schema-valid instance from the generated text."""
    from aegis.models import TypologyHypothesis, Verdict

    name = schema.__name__
    text.lower()
    try:
        if name == "Output" and "hypotheses" in schema.model_fields:
            return schema(
                hypotheses=[
                    TypologyHypothesis(
                        code="structuring",
                        label="Structuring",
                        confidence=0.82,
                        rationale="Deposits just below the $10k threshold.",
                        decisive_evidence=["t1", "t2", "t3"],
                    )
                ]
            )
        if name == "Output" and "narrative" in schema.model_fields:
            return schema(narrative=text, citations=[{"claim": "structuring", "ref": "t1"}])
        if name == "Output" and "approved" in schema.model_fields:
            return schema(approved=True, confidence=0.9, notes=["grounded and complete"])
        if name == "Output" and "decision" in schema.model_fields:
            return schema(
                decision=Verdict.STEP_UP,
                score=0.77,
                typology=["device_farm"],
                rationale="Shared device across multiple cards [t1].",
                evidence_spans=["t1"],
            )
        if name == "Output" and "compliant" in schema.model_fields:
            return schema(
                compliant=False,
                severity="high",
                rationale="Refresh cadence 12mo vs required 6mo [policy/kyc.md].",
                evidence_spans=["policy/kyc.md"],
                proposed_remediation="Update cadence to 6 months for high-risk.",
            )
    except Exception:
        pass
    # Generic fallback: try JSON, then empty-ish construction.
    try:
        return _coerce(text, schema)
    except Exception:
        return schema.model_construct()


class LLMRouter:
    """Routes a step to a model tier. 'tiered' = cheap default, strong for reasoning."""

    TIERS = {
        "fast": "gemini-4-hydrogen",
        "balanced": "gemini-4-neon",
        "reasoning": "claude-opus-5.5",
        "coding": "gpt-6-terra",
    }

    def __init__(self, provider: str | None = None):
        self.provider = provider or get_settings().llm_provider
        self._cache: dict[str, BaseLLM] = {}

    def _build(self, model: str) -> BaseLLM:
        if self.provider == "mock":
            return MockLLM(model="mock")
        if self.provider == "openai":
            return OpenAILLM(model=model)
        if self.provider == "anthropic":
            return AnthropicLLM(model=model)
        raise LLMError(f"unknown provider: {self.provider}")

    def get(self, tier: str = "balanced") -> BaseLLM:
        model = self.TIERS.get(tier, tier)
        if model not in self._cache:
            self._cache[model] = self._build(model)
        return self._cache[model]


_router: LLMRouter | None = None


def get_router() -> LLMRouter:
    global _router
    if _router is None:
        _router = LLMRouter()
    return _router


async def two_pass[T: BaseModel](
    llm: BaseLLM,
    gather_messages: list[ChatMessage],
    emit_messages: list[ChatMessage],
    schema: type[T],
    budget: Budget | None = None,
) -> T:
    """Pass 1: tools-on style reasoning (no schema). Pass 2: schema-constrained emit.

    Mitigates the 2026 constraint-tax tool-suppression failure by decoupling
    action selection from schema-constrained generation.
    """
    log.info("two_pass.start", schema=schema.__name__)
    _, usage = await _acomplete(llm, gather_messages)
    if budget:
        budget.record(usage)
    messages = gather_messages + [
        ChatMessage("assistant", "Evidence gathered."),
        *emit_messages,
    ]
    result = llm.structured(messages, schema, budget)
    log.info("two_pass.done", schema=schema.__name__)
    return result


async def _acomplete(llm: BaseLLM, messages: list[ChatMessage]) -> tuple[str, Usage]:
    if hasattr(llm, "acomplete"):
        return await llm.acomplete(messages)
    return llm.complete(messages)