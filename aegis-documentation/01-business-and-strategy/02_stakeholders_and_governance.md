# 02 — Stakeholders and Governance

## Stakeholders

| Stakeholder | Interest | Influence |
|---|---|---|
| Executive Sponsor (CDO) | Strategic outcome, funding | High |
| Head of Retail / Product | Feature scope, roadmap, UX | High |
| Chief Architect | Architecture integrity, tech choices | High |
| Head of Compliance / MLRO | AML/KYC, regulatory reporting | High (binding) |
| CISO / Security | Security, data protection | High (binding) |
| Head of Platform / SRE | Buildability, reliability, cost | High |
| Data Science / ML Eng | Models, evals, MLOps | Medium |
| Head of AI / ML | Model quality, evals, MLOps | High |
| Data Protection Officer (DPO) | GDPR/CCPA, privacy, DSAR | High (binding) |
| CFO | Funding, cost/ROI | High |
| Frontline / Contact Centre | Usability, escalation flows | Medium |
| Customers | Experience, trust, accessibility | High (voice) |
| Regulators | Compliance, auditability | High (external) |

## Governance bodies

- **Architecture Review Board (ARB):** approves ADRs, cross-cutting design. Chair: Chief Architect.
- **Risk & Compliance Committee:** approves AML/KYC, model risk, regulatory positions. Chair: Head of Compliance.
- **Security Review:** approves threat model, pen-test results, data flows. Chair: CISO delegate.
- **Change Advisory Board (CAB):** approves production releases. Chair: Head of Platform.
- **AI Governance (EU AI Act):** model inventory, risk tiering, human oversight, explainability — owned by the Risk & Compliance Committee with the Head of AI/ML.

## RACI (summary)

| Activity | Responsible | Accountable | Consulted | Informed |
|---|---|---|---|---|
| Architecture | Chief Architect | CDO | ARB, Security | Product |
| AML/KYC controls | Compliance | Head of Compliance | Risk Committee | Regulators |
| Fraud models | Data Science | Head of AI/ML | Compliance | Product |
| AI/ML models | Data Science | Head of AI/ML | Risk Committee, Compliance | Product |
| Security | Security Eng | CISO | ARB | All |
| Data privacy | DPO | DPO | Legal, Compliance | Regulators |
| Production release | Platform | Head of Platform | CAB | All |
| Incident response | Platform/SRE | CISO | CAB | All |
| Third-party / vendors | Procurement | CFO | CISO, Compliance | ARB |
| Accessibility | Frontend | Head of Product | ARB | Compliance |