# Information Architecture and Mental Model

## Top-level IA (mobile app)

```
Home (balance, quick actions, insights)
├── Accounts (list, detail, pots)
├── Payments (send, request, pay a bill, scheduled)
├── Cards (manage, freeze, controls, PIN)
├── Save & Invest (goals, robo, self-directed)
├── Borrow (loans, BNPL, overdraft)
├── Business (account, invoices, expenses)
├── Insights (spending, budgets, credit score)
├── Assistant (agentic chat/voice)
└── Profile & Settings (security, accessibility, privacy)
```

## Mental model

Customers think in **"my money"** (balance, spending, goals) and **"my actions"** (pay, save, borrow), not in banking products. The IA should mirror this:

- **Money view first** — balance and cash flow up front.
- **Actions next** — send, pay, save are primary.
- **Products secondary** — lending/investing discovered contextually, not as a menu of SKUs.

## Navigation principles

- **Bottom tab bar** for the 5 primary destinations (Home, Payments, Cards, Save, Profile).
- **Everything else** reachable in ≤2 taps.
- **Global search** (assistant-powered) for "where is X".
- **Progressive disclosure** — advanced features (controls, limits) revealed on demand.

## Cross-channel consistency

Mobile, web, and voice share the same IA and terminology. The agentic assistant is a **parallel navigation path** (natural language → same destinations).