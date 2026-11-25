# Customer Journey and Service Blueprint

## End-to-end journey: onboarding → first payment

| Stage | Customer actions | Frontstage (visible) | Backstage (systems) | Pain points |
|---|---|---|---|---|
| **Discover** | Hears about Aegis | Marketing, app store | — | Trust |
| **Sign up** | Enters details | Registration UI | Identity, KYC orchestration | Friction |
| **Verify** | Uploads ID, selfie | Guided capture | Docling + liveness + sanctions | Drop-off |
| **Fund** | Links bank / deposits | Funding flow | Open banking / ACH | Speed |
| **Activate** | Pays a bill / transfers | Payments UI | Ledger, fraud score | Confidence |
| **Aha** | Salary auto-sorts | Progress view | Rules engine, pots | Delight |
| **Retain** | Uses daily | Home dashboard | Insights, notifications | Habit |

## Service blueprint (fraud scenario)

| Layer | Steps |
|---|---|
| **Customer** | Sees a suspicious charge → taps "freeze" → confirms |
| **Frontstage** | Alert → one-tap actions → confirmation |
| **Backstage** | Fraud engine scores → alert → agent triage → card block/release |
| **Support processes** | Analyst review queue, SAR pre-fill if needed |
| **Systems** | Kafka event → GNN score → LangGraph agent → core ledger |

## Moments that matter

- **Onboarding completion** (activation).
- **First fraud resolution** (trust).
- **First goal reached** (retention).
- **Any decline** (must explain, not just reject).

## Service recovery

Every failure (declined payment, failed login, suspected fraud) must have a clear, fast, human-accessible recovery path.
