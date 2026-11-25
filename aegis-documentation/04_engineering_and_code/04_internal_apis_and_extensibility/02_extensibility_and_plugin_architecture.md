# Extensibility and Plugin Architecture

## Extension points

| Extension | Mechanism |
|---|---|
| **New agent** | Register a LangGraph node + tool set |
| **New tool** | Register in `ToolRegistry` (MCP) |
| **New model** | Register with LiteLLM router |
| **New guardrail** | Add a rail to the chain |
| **New product** | Configure in the product engine (no code) |
| **New payment rail** | Add a payments adapter |

## Plugin architecture

- **Registry pattern** — components register capabilities.
- **Interfaces** — plugins implement a stable interface.
- **Composition** — plugins composed per deployment.
- **Isolation** — plugins sandboxed; least privilege.

## Agent plugins

`python
registry.register(Tool(
 name="get_customer_360",
 handler=get_customer_360,
 roles={"risk"},
 is_write=False,
))
`

## Safety

- Plugins cannot exceed their declared roles/scopes.
- All tool calls pass through guardrails.
- Plugin code is reviewed and signed.

## Versioning

- Plugins declare compatibility (`api_version`).
- Breaking changes gated in CI.
