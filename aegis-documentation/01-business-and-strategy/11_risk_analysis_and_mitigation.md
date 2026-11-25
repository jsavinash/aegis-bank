# 11 — Risk Analysis and Mitigation

> **Scope:** all features + AI use cases are in scope per [`in_scope_and_out_of_scope.md`](05_scope_and_boundaries/in_scope_and_out_of_scope.md); out of scope: physical branches, proprietary payment scheme, own core-ledger build.

## Business & delivery risks

| # | Risk | Likelihood | Impact | Mitigation |
|---|---|---|---|---|
| R1 | Banking licence / BaaS partner delayed | Medium | High | Start legal early; fallback BaaS |
| R2 | Core-banking integration complexity | High | High | Buy proven core; event-driven integration; phased |
| R3 | AI pilots never reach production | High | High | Pick 2–3 deep wins; eval-gated CI; business-metric tracking |
| R4 | Regulatory rejection of AI decisions | Medium | High | Explainability, HITL, audit trails, model risk governance |
| R5 | Vendor lock-in (LLM/cloud/core) | Medium | Medium | LiteLLM routing; open weights; IaC portability |
| R6 | Cost blow-up (agent loops, GPU) | Medium | Medium | Budgets, model routing, reserved capacity |
| R7 | Talent scarcity (AI + banking) | High | Medium | Partner, upskill, managed services |
| R8 | Data quality from core | High | High | Event backbone + feature store; data contracts |
| R9 | Eval debt (regression suite not maintained) | Medium | High | Eval gate in CI from day one; tagged suites; online eval on prod traces |
| R10 | LLM latency compounding on hot paths | Medium | High | LLM never on fraud hot path; precomputed graph embeddings; async warm path with hard budget; model routing |
| R11 | Context-window bloat / graph-prompt inflation | Medium | Medium | Scoped graph over obligations/controls; retrieval budget; summarise-then-reason; router keeps lookups on cheap hybrid |
| R12 | Constraint-tax tool suppression (open-weight models) | Medium | High | Two-pass structured output (tools-on then schema-on) wherever agent must both act and emit JSON |
| R13 | Temporal correctness (citing superseded rules) | Medium | High | Temporal KG with explicit validity intervals; router sends temporal questions to graph; regression suite tagged `temporal` |
| R14 | Accessibility non-compliance (EAA/WCAG) | Medium | High | WCAG 2.2 AA in CI (axe-core) + manual screen-reader testing from Phase 1 |

## Security & compliance risks

| # | Risk | Mitigation |
|---|---|---|
| S1 | Prompt injection via tool results | NeMo tool-result rails, least-privilege MCP, approval gates |
| S2 | Hallucinated SAR/credit narrative | Citation-required generation, groundedness verifier, deterministic numbers, HITL |
| S3 | Data leakage / residency | Self-host in-VPC; encryption; DLP; consent |
| S4 | Model bias (fair lending) | Bias testing, explainability, human review |
| S5 | Fraud-model drift | Shadow scoring, champion/challenger, drift monitors |

## Risk governance

Reviewed by the Risk & Compliance Committee; security risks by CISO delegate. See `07-security-compliance-and-users/security-and-compliance/`.