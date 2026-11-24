# Class, Interface and Model Definitions

## Core domain models (Python / Pydantic)

```python
class CaseState(BaseModel):
    case_id: UUID
    alert_id: UUID
    entity_id: UUID
    status: CaseStatus = CaseStatus.OPEN
    evidence: list[Evidence] = []
    hypotheses: list[TypologyHypothesis] = []
    narrative: str = ""
    risk_score: float = 0.0
    approvals: list[dict] = []
```

## Key interfaces

```python
class BaseLLM(Protocol):
    def complete(self, messages: list[ChatMessage]) -> tuple[str, Usage]: ...
    def structured(self, messages, schema: type[T]) -> T: ...

class ToolRegistry:
    async def call(self, role: str, name: str, args: dict, approved: bool = False) -> dict: ...

class GuardrailEngine:
    def check_input(self, text: str) -> GuardrailResult: ...
    def check_tool_call(self, role: str, tool: str, args: dict) -> GuardrailResult: ...
```

## Domain entities

| Entity | Fields (key) |
|---|---|
| **Customer** | id, legal_name, risk_rating, kyc_status |
| **Account** | id, customer_id, type, balance, currency |
| **Transaction** | id, account_id, amount, direction, occurred_at |
| **Card** | id, customer_id, status, controls |
| **Loan** | id, customer_id, principal, terms, status |
| **Case** | id, alert_id, status, narrative, risk_score |

## Value objects

`Money(amount, currency)`, `RiskScore(value, level)`, `CaseStatus`, `Verdict`.

See the reference implementation in `06_reference_implementation/`.