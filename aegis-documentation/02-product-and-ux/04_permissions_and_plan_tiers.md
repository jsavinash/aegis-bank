# Permissions and Plan Tiers

## Plan tiers

| Tier | Price | Includes |
|---|---|---|
| **Standard** | Free | Accounts, debit card, payments, budgeting, basic support |
| **Plus** | Monthly fee | Higher limits, virtual cards, cashback, priority support |
| **Premium / Metal** | Higher fee | Metal card, travel perks, lounge access, higher FX allowance |
| **Business** | Per-seat | Multi-user, invoicing, accounting integrations, payroll |

## Feature gating

| Feature | Standard | Plus | Premium | Business |
|---|---|---|---|---|
| Virtual cards | 1 | 5 | Unlimited | Team |
| Cashback | — | ✓ | ✓✓ | — |
| FX allowance | Basic | Reduced | Zero-FX | Multi-currency |
| Team access | — | — | — | ✓ |
| Accounting integrations | — | — | — | ✓ |
| Priority support | — | ✓ | ✓ | ✓ |

## Permissions model

- **Roles:** customer, joint holder, teen (restricted), business admin, business member, analyst, compliance, auditor, agent (service principal).
- **Scopes:** `account:read`, `payment:write`, `card:control`, `case:approve`, `risk:read`, `tool:call`.
- **Step-up auth** required for high-risk actions (new payee, limit increase, large transfer).
- **Parental controls** for teen accounts (limits, freeze, alerts).
