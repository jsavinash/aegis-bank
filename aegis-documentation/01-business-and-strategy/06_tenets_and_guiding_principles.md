# 06 — Tenets and Guiding Principles

## Core tenets

1. **AI-first, not AI-wrapped.** AI is the product's decision engine; the UI is a view into it. If the model is removed, the product fails — that's the test.
2. **Trust is the product.** Security, explainability, and auditability are features, not afterthoughts.
3. **Humans are accountable.** High-consequence actions (file SAR, freeze, approve loan, move money) require human sign-off with a full audit trail. AI never acts autonomously on regulated actions.
4. **Deterministic-first, AI-second.** Money math, authorization, and compliance rules are code; AI handles ambiguity and language, never invents numbers.
5. **Buy the commodity, build the moat.** Buy the core banking ledger and data vendors; build the AI/risk plane and evals.
6. **Config over code.** Products/fees/pricing are configuration; 600 features ≠ 600 codebases. One ledger, many products.
7. **Eval-driven engineering.** No prompt/model/graph change merges without passing the tagged regression suite. Evals define "done," not vibes; the eval loop closes via online traces fed back into the dataset.
8. **Accessibility by design.** WCAG 2.2 AA from day one; EAA-compliant in EU; axe-core CI + manual screen-reader testing.
9. **Event-driven from day one.** Every transaction emits an event; the AI plane is impossible without a real-time event stream.
10. **Fail closed.** If a safety check can't run, block and alert. Deterministic checks run first; model checks only where rules can't decide.

## AI-first design principles

11. **AI as the primary interface.** The default customer and employee interface is conversational/agentic (voice + text), with traditional screens as a fallback for complex or authority actions.
12. **AI that escalates, not deflects.** Agents hand off to humans for fraud, disputes, and high-consequence actions — never trap users in self-service loops (J.D. Power: satisfaction drops for fraud/disputes).
13. **AI in every workflow, not just chat.** Every domain (fraud, AML, underwriting, KYC, support, marketing, ops) has an AI decision or copilot, each with a human escalation path.
14. **AI-native data layer as first-class.** Vector + graph + feature store are core infrastructure, not sidecars.
15. **Model-agnostic & self-hostable.** Abstract the LLM layer; route across providers (LiteLLM); self-host open weights for data residency; never hard-wire a single vendor.
16. **Durable execution everywhere.** Every agent run is a Temporal workflow: survives crashes, waits days for humans, idempotent side effects keyed by run ID.
17. **Least-privilege, task-scoped tool access.** Agents never hold standing write credentials; every MCP tool call is policy-checked and attributable to a signed principal; approval gates before external actions.
18. **Grounding as a hard gate.** Every factual claim carries a citation span to the evidence bundle; a verifier rejects ungrounded output before it reaches a human or a filing.
19. **Numbers computed in code, narrated by the model.** The LLM explains risk/amounts; quantitative values are computed in deterministic code.
20. **Two-pass structured output.** Where an agent must both act and emit strict JSON: pass 1 gathers context (tools on), pass 2 emits the verdict (tools off, schema on) — avoids constraint-tax tool suppression.
21. **Eval as CI gate + closed loop.** Tagged regression suites (fact/multi-hop/temporal/unanswerable) block merges; sampled production traces scored by calibrated LLM-as-judge feed the dataset.
22. **Full-fidelity OTel tracing.** Every model call, tool call, retrieval, guardrail decision, and human action is a span; a bad outcome is reconstructable end-to-end.

## Language & framework rules (implementation)

23. **Touching a model → Python.** Everything touching models, agents, RAG, evals, data pipelines is Python.
24. **Touching money at speed → Go.** Ledger, payments, cards, fraud hot path, gateway: Go for low tail latency.
25. **Touching a screen → TypeScript.** All customer/employee interfaces (mobile, web, BFF).
26. **Touching the ledger product logic → buy, don't build.** Integrate via events/APIs; don't reimplement the core.
27. **Touching a contract → define it first.** OpenAPI/AsyncAPI/GraphQL; language is an implementation detail behind the contract.
28. **Touching compliance → `aegis/` (Python) + human sign-off.** Never autonomous for regulated actions.
