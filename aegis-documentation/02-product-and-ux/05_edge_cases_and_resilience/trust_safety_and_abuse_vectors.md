# Trust, Safety and Abuse Vectors

## Trust & safety principles

- **Protect the customer's money first.** When in doubt, block and verify.
- **Explain, don't just reject.** Every decline/fraud hold has a reason and a path.
- **Human escalation is always available** for sensitive or high-value cases.

## Abuse vectors & mitigations

| Vector | Mitigation |
|---|---|
| **Account takeover (ATO)** | Passkeys, device binding, step-up auth, anomaly detection |
| **Card testing** | Velocity checks, device-farm detection, merchant risk |
| **Mule accounts** | Graph analysis (shared devices/contacts), behavioural monitoring |
| **Synthetic identity** | Multi-source KYC, document + biometric verification |
| **Authorised push payment (APP) fraud** | Scam-warning, confirmation of payee, cooling-off |
| **Money laundering** | AML monitoring + agentic investigation + SAR |
| **Social engineering** | In-app scam-call detection, education, transaction holds |
| **Prompt injection via docs** | NeMo tool-result rails; treat all retrieved content as untrusted |

## Scam-payment interception

When a payment pattern suggests the customer is being scammed (new payee + unusual amount + urgency), the app shows a **scam warning**, offers a **cooling-off period**, and can **hold** the payment for human review.

## Responsible design

- No dark patterns; transparent fees; clear consent.
- Vulnerability detection → route to a trained human.
- Accessibility for vulnerable customers (extra time, plain language).