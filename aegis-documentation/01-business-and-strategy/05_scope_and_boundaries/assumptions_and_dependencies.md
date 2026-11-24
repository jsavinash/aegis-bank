# Assumptions and Dependencies

## Assumptions

| # | Assumption | Impact if false |
|---|---|---|
| A1 | We can buy a core (Thought Machine/Mambu) rather than build | Timeline + cost blow-up |
| A2 | A banking licence or BaaS partner is secured | Cannot hold deposits |
| A3 | Open-weight + frontier LLMs meet quality/cost bar | Re-evaluate model routing |
| A4 | Regulators accept explainable AI + HITL | Compliance rework |
| A5 | Customers adopt passkeys & agentic assistant | Security/UX investment at risk |
| A6 | Data (KYC/AML/fraud feeds) is licensable | Build vs buy for data |

## Dependencies

| Dependency | Owner | Needed by |
|---|---|---|
| Core banking platform | Procurement + Architecture | P2 |
| Banking licence / BaaS partner | Legal + Executive | P0 |
| KYC/AML/fraud data vendors | Compliance + Procurement | P1 |
| Cloud landing zone (EKS, Terraform) | Platform | P0 |
| Identity provider (Keycloak/Ory) | Security | P1 |
| Model providers / GPU capacity | Data Science | P1 |
| Legal & compliance review | Compliance | Each phase |

## Risks to assumptions

Tracked in `11_risk_analysis_and_mitigation.md`.