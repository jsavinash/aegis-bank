# Routing, Forms and Validation

## Routing

- **File-based routing** (Next.js App Router); native stack navigation (React Navigation).
- **Route guards:** authentication, step-up auth, role checks.
- **Deep links:** every screen linkable (account, transaction, goal).
- **State preservation:** back-navigation retains context.

## Forms

- **Schema-first:** Zod schemas shared between client and API.
- **Validation:** inline, accessible errors; validate on blur + submit.
- **Step-up:** high-risk actions (new payee, limit change) require re-auth.
- **Drafts:** non-sensitive drafts persist locally; never persist card numbers.

## Validation rules (examples)

| Field | Rule |
|---|---|
| Amount | > 0, ≤ daily limit, correct currency |
| Payee | Must exist / be confirmed (confirmation of payee) |
| Card PIN | Not stored; reset via secure flow |
| Password | N/A (passkeys) — but recovery codes validated |

## Accessibility

- Labels tied to inputs; `aria-describedby` for errors.
- Focus to first error on submit.
- Announce validation results to screen readers.