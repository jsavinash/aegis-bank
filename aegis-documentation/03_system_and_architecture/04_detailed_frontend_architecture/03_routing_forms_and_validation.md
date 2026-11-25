# Routing, Forms and Validation

## Routing

- **File-based routing** (Next.js App Router); native stack navigation (React Navigation).
- **Route guards:** authentication, step-up auth, role checks; redirect with return URL.
- **Deep links:** every screen linkable (account, transaction, goal); cold-start deep link → auth → resume.
- **State preservation:** back-navigation retains context.
- **Code splitting** per route; **error/loading/404 boundaries**.

## Forms

- **Schema-first:** Zod schemas shared between client and API.
- **Validation:** inline, accessible errors; validate on blur + submit; **async + server-side** revalidation.
- **Step-up:** high-risk actions (new payee, limit change) require re-auth.
- **Drafts:** non-sensitive drafts persist locally (schema-versioned); never persist card numbers.
- **Idempotency keys** on submit; **multi-step wizards** with per-step validation; **i18n** messages.

## Validation rules (examples)

| Field | Rule |
|---|---|
| Amount | > 0, ≤ daily limit, correct currency |
| Payee | Must exist / be confirmed (confirmation of payee) |
| Card PIN | Not stored; reset via secure flow |
| Password | N/A (passkeys) — but recovery codes validated |
| Email / phone | Format + ownership (OTP) |
| IBAN / account | Checksum + country format |

## Accessibility

- Labels tied to inputs; `aria-describedby` for errors.
- Focus to first error on submit.
- Announce validation results to screen readers.

## Testing

- Zod schema unit tests; **route-guard** tests; form submission (idempotency) tests.
