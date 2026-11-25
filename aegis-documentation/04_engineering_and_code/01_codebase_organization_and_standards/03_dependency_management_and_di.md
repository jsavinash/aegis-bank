# Dependency Management and Dependency Injection

## Dependency management

| Ecosystem | Tool |
|---|---|
| **Python** | `pyproject.toml` + uv/poetry; lockfile committed |
| **Java** | `pom.xml` / `build.gradle`; minimal deps |
| **TypeScript** | pnpm/npm; lockfile committed |
| **Terraform** | pinned provider versions |

## Policies

- **Pinned versions** (lockfiles) for reproducibility.
- **SCA scanning** (CVEs) in CI; no critical vulns to prod; **SBOM** generated.
- **License checks** (no copyleft without approval).
- **Minimal deps** — justify every new dependency.

## Tooling & automation

- **Renovate/Dependabot** for update PRs; **private registry** (Artifactory/GH Packages).
- **Transitive deps** reviewed; **update cadence** monthly (patch) / quarterly (minor).

## Dependency injection

- **Constructor injection** (not service locators).
- **Interfaces** for external boundaries (DB, LLM, tools) → testability.
- **Composition root** per process wires dependencies.
- **No global state** — explicit dependencies; **no circular deps**.

## DI lifecycle & patterns

- **Scoped lifetimes** (singleton for stateless, scoped per request); **factory/provider** for expensive clients.
- **Config injected** (not read from globals).

## Example (Python)

`python
class CaseService:
 def __init__(self, llm: BaseLLM, tools: ToolRegistry, guardrails: GuardrailEngine):
 self.llm = llm
 self.tools = tools
 self.guardrails = guardrails
`

## Testing

- **Fakes/mocks** at interfaces; **real** composition root only in integration tests.

## Versioning

- **Semantic versioning** for internal libraries; **deprecation** with migration path.
