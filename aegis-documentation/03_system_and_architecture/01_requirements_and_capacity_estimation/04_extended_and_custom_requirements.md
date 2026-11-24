# Extended and Custom Requirements

## Domain-specific requirements

| Area | Requirement |
|---|---|
| **AML** | SAR filing within regulatory deadline; 5-year record retention; explainable triage |
| **KYC** | Risk-based CDD/EDD; periodic review; PEP/sanctions screening |
| **Fraud** | Real-time scoring; chargeback handling; scam-payment interception |
| **Lending** | Adverse-action reasons; fair-lending bias testing; affordability checks |
| **Payments** | Confirmation of payee; ISO 20022; instant rails; idempotency |
| **Open banking** | Consent lifecycle (grant/revoke/expiry); PSD2 SCA |
| **Accessibility** | WCAG 2.2 AA; EAA; multi-sensory; plain language |

## Model-risk requirements (EU AI Act / SR 11-7)

- Model inventory, validation, monitoring, documentation.
- Human oversight for high-risk AI (credit, AML).
- Bias & fairness testing.
- Explainability for adverse decisions.

## Auditability

- Immutable audit log of every AI decision: inputs, model version, prompt version, policy, output, human approver.
- Reconstructable decision chains for examiners.