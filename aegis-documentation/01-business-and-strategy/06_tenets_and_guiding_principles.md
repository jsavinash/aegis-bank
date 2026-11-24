# 06 — Tenets and Guiding Principles

1. **AI-first, not AI-wrapped.** AI is the product's decision engine; the UI is a view into it. If the model is removed, the product fails — that's the test.
2. **Trust is the product.** Security, explainability, and auditability are features, not afterthoughts.
3. **Humans are accountable.** High-consequence actions (file SAR, freeze, approve loan, move money) require human sign-off with a full audit trail.
4. **Deterministic-first, AI-second.** Money math, authorization, and compliance rules are code; AI handles ambiguity and language, never invents numbers.
5. **Buy the commodity, build the moat.** Buy the core and data; build the AI/risk plane and evals.
6. **Config over code.** Products/fees/pricing are configuration; 600 features ≠ 600 codebases.
7. **Eval-driven engineering.** No prompt/model/graph change merges without passing the regression suite.
8. **Accessibility by design.** WCAG 2.2 AA from day one.
9. **Event-driven from day one.** The AI plane is impossible without a real-time event stream.
10. **Fail closed.** If a safety check can't run, block and alert.