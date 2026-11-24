# Dependency Management and Dependency Injection

## Dependency management

| Ecosystem | Tool |
|---|---|
| **Python** | `pyproject.toml` + uv/poetry; lockfile committed |
| **Java** | `pom.xml` / `build.gradle`; minimal deps |
| **TypeScript** | pnpm/npm; lockfile committed |
| **Terraform** | pinned provider versions |

## Policies

- **Pinned versions** for reproducibility.
- **SCA scanning** (CVEs) in CI; no critical vulns to prod.
- **License checks** (no copyleft without approval).
- **Minimal deps** — justify every new dependency.

## Dependency injection

- **Constructor injection** (not service locators).
- **Interfaces** for external boundaries (DB, LLM, tools) → testability.
- **Composition root** per process wires dependencies.
- **No global state** — explicit dependencies.

## Example (Python)

```python
class CaseService:
    def __init__(self, llm: BaseLLM, tools: ToolRegistry, guardrails: GuardrailEngine):
        self.llm = llm
        self.tools = tools
        self.guardrails = guardrails
```

## Versioning

- **Semantic versioning** for internal libraries.
- **Deprecation** with migration path.