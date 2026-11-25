# Acceptance Criteria and Cases

> **Coverage:** acceptance is derived from the use-case catalog ([`../03_system_and_architecture/01_requirements_and_capacity_estimation/07_use_case_catalog_all.md`](../03_system_and_architecture/01_requirements_and_capacity_estimation/07_use_case_catalog_all.md)) and the full feature catalog ([`../01-business-and-strategy/14_feature_catalog.md`](../01-business-and-strategy/14_feature_catalog.md)).

## Definition of done (per feature)

- Meets functional acceptance criteria.
- Passes eval gate (AI features).
- WCAG 2.2 AA conformant.
- Security review passed.
- Observability in place (traces, metrics, audit).
- Documented (ADR if architectural).

## Acceptance criteria (examples)

| ID | Given | When | Then |
|---|---|---|---|
| AC-1 | A verified customer | initiates a payment within limits | it is scored, posted, and confirmed <2s |
| AC-2 | A fraud signal fires | a card txn is flagged | card can be frozen in one tap and confirmed |
| AC-3 | An AML alert exists | an analyst opens a case | evidence is gathered and a grounded narrative is drafted |
| AC-4 | QA confidence < 0.85 | a narrative is reviewed | the case is routed to a human (HITL) |
| AC-5 | A high-value action | is requested | step-up auth is required |
| AC-6 | A user with a screen reader | completes onboarding | all steps are announced and operable |

## Acceptance test cases (representative)

- **Payment happy path** — clear → posted → receipt.
- **Payment decline** — insufficient funds → clear reason + recovery.
- **Card freeze** — instant, idempotent, reversible.
- **AML investigation** — evidence → narrative → QA → HITL → file.
- **Assistant escalation** — complex query → human handoff with summary.
- **Accessibility** — keyboard-only + screen-reader journey.

## Non-acceptance

- No eval pass → not accepted (AI).
- Any critical security finding → not accepted.
- WCAG failure on a critical journey → not accepted.
