# Empty, Loading and Error States

## Principles

- **Never show a blank screen.** Every empty state explains *why* and offers a next step.
- **Loading states must feel fast** — skeletons, not spinners; optimistic UI where safe.
- **Errors must be actionable** — say what happened, what to do, and offer help.

## Empty states

| Context | Message | Action |
|---|---|---|
| No transactions | "No transactions yet" | "Make your first payment" |
| No goals | "You haven't set a savings goal" | "Create a goal" |
| No cards | "You don't have a card yet" | "Order a card" |
| No alerts | "You're all caught up" | — |

## Loading states

- **Skeleton screens** for lists/dashboards.
- **Inline progress** for payments ("Sending…").
- **Timeout + fallback** if a request exceeds budget (e.g., assistant → offer human).

## Error states

| Error | Message | Recovery |
|---|---|---|
| Insufficient funds | "Not enough money in this account" | Top up / choose another account |
| Card declined | "Your card was declined" + reason | Retry / contact / freeze |
| Network failure | "You're offline" | Retry when back online |
| Fraud hold | "We're reviewing this transaction for your safety" | Confirm it was you / contact |
| Session expired | "Please sign in again" | Re-authenticate (passkey) |

## Accessibility

- Errors announced to screen readers (`aria-live`).
- Never rely on colour alone; pair with text/icon.
- Focus moves to error summary on submit failure.
