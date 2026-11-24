# 11 — Risk Analysis and Mitigation

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