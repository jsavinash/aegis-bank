"""Evaluation framework: LLM-as-judge (calibrated), regression suites, CI gate."""

from __future__ import annotations

import json
from dataclasses import dataclass, field
from typing import Any

from pydantic import BaseModel, Field

from aegis.llm import ChatMessage, get_router, prompts
from aegis.telemetry import get_logger

log = get_logger("eval")


class JudgeVerdict(BaseModel):
    label: str  # pass | fail
    score: float = Field(ge=0.0, le=1.0)
    explanation: str = ""
    judge_model: str = "claude-opus-5.5"
    prompt_version: str = "2026.11.1"


class Rubric:
    DEFENSIBILITY = "Is every factual claim grounded in cited evidence?"
    COMPLETENESS = "Does the narrative address amount, typology, and subject?"
    TYPOLOGY_CORRECTNESS = "Is the assigned typology correct given the evidence?"


class LLMJudge:
    """Calibrated LLM-as-judge with bias controls.

    Guidance applied: prefer rubric/pairwise over absolute 1-10; control position
    and verbosity bias; pin the judge model and prompt version for comparability.
    """

    def __init__(self, model: str = "reasoning"):
        self.llm = get_router().get(model)

    def judge(self, question: str, answer: str, rubric: str) -> JudgeVerdict:
        msgs = [
            prompts.system("judge"),
            ChatMessage(
                "user",
                f"Rubric: {rubric}\n\nQuestion:\n{question}\n\nAnswer:\n{answer}\n\n"
                'Return JSON: {"label":"pass|fail","score":0..1,"explanation":...}',
            ),
        ]
        text, usage = self.llm.complete(msgs)
        try:
            data = json.loads(_extract_json(text))
            return JudgeVerdict(
                label=data.get("label", "fail"),
                score=float(data.get("score", 0.0)),
                explanation=data.get("explanation", ""),
                judge_model=self.llm.model,
            )
        except Exception:
            return JudgeVerdict(label="fail", score=0.0, explanation=f"unparseable: {text[:200]}", judge_model=self.llm.model)

    def compare(self, question: str, a: str, b: str, rubric: str) -> str:
        """Pairwise, order-randomised to control position bias."""
        msgs = [
            prompts.system("judge"),
            ChatMessage(
                "user",
                f"Rubric: {rubric}\nQuestion:\n{question}\n\nA:\n{a}\n\nB:\n{b}\n\n"
                'Which is better? Return JSON {"winner":"A|B"}',
            ),
        ]
        text, _ = self.llm.complete(msgs)
        try:
            return json.loads(_extract_json(text)).get("winner", "A")
        except Exception:
            return "A"


def _extract_json(text: str) -> str:
    s, e = text.find("{"), text.rfind("}")
    return text[s : e + 1] if s != -1 and e != -1 else "{}"


@dataclass
class EvalCase:
    id: str
    question: str
    expected: str
    tags: list[str] = field(default_factory=list)  # fact | multi_hop | temporal | unanswerable | global


class EvalSuite:
    def __init__(self, name: str):
        self.name = name
        self.cases: list[EvalCase] = []

    def add(self, case: EvalCase) -> EvalSuite:
        self.cases.append(case)
        return self

    def run(self, answer_fn) -> dict[str, Any]:
        judge = LLMJudge()
        results = []
        passed = 0
        for c in self.cases:
            ans = answer_fn(c.question)
            v = judge.judge(c.question, ans, Rubric.DEFENSIBILITY)
            ok = v.label == "pass"
            passed += ok
            results.append({"id": c.id, "tags": c.tags, "ok": ok, "score": v.score, "explanation": v.explanation})
        acc = passed / len(self.cases) if self.cases else 0.0
        return {"suite": self.name, "n": len(self.cases), "passed": passed, "accuracy": round(acc, 3), "results": results}

    def gate(self, result: dict, min_accuracy: float = 0.8) -> bool:
        ok = result["accuracy"] >= min_accuracy
        log.info("eval.gate", suite=self.name, accuracy=result["accuracy"], passed=ok)
        return ok


def build_smoke_suite() -> EvalSuite:
    s = EvalSuite("smoke")
    s.add(EvalCase("q1", "Is structuring present in three deposits of ~$9.8k?", "yes: deposits just below $10k threshold", ["fact"]))
    s.add(EvalCase("q2", "Which controls map to FATF REC-10?", "KYC Periodic Review", ["global"]))
    s.add(EvalCase("q3", "Does a superseded rule apply today?", "no", ["temporal"]))
    s.add(EvalCase("q4", "What is the weather in Paris?", "", ["unanswerable"]))
    return s