# UI and Micro-Frontend Topology
> **Scope:** all 37 feature-catalog sections + AI use cases (as features) are in scope per [`in_scope_and_out_of_scope.md`](../../01-business-and-strategy/05_scope_and_boundaries/in_scope_and_out_of_scope.md); out of scope: physical branches, proprietary payment scheme, own core-ledger build.

## Frontend topology

| App | Tech | Owner |
|---|---|---|
| **Mobile** | React Native (Expo) / Flutter | Mobile team |
| **Web** | Next.js (TypeScript) | Web team |
| **Ops console** | Next.js | Platform |
| **Agentic assistant** | Embedded in mobile/web | AI team |

## Micro-frontend strategy

- **Modular monolith frontend** initially (Next.js app router + feature modules), split into micro-frontends only when team size demands.
- **Module federation** (or Next.js remote components) for independent deploys of large domains (lending, wealth, business).
- **Shared design system** (accessible components, WCAG 2.2 AA).
- **Versioned shared contracts**; independent releases with contract tests.

## Routing & navigation

- **Route-per-domain** (app router); deep links for every journey.
- **Auth guard** + step-up for high-value actions.
- **Global shell** (AppShell: app bar, tab bar, overlays).

## State & data

- **Server state:** React Query / SWR (cache, optimistic updates, invalidation).
- **Client state:** Zustand/Redux Toolkit for local UI.
- **Forms:** React Hook Form + Zod.
- **API:** GraphQL BFF for flexible queries.
- **Offline:** read-only cache + draft queue (no auto money writes).

## Cross-cutting

- **Theming:** light/dark/high-contrast; design tokens.
- **i18n/l10n:** locale currency/date; plain language.
- **Security:** CSP, no secrets in client, token storage in secure enclave.
- **Observability:** RUM + OTel; error budgets.

## Performance budgets

- App cold start (p95) <2s; TTFB <600ms; route-level code splitting; bundle budgets in CI.

## Testing & release

- Unit + integration + E2E (Playwright/Detox); visual regression; **axe-core** in CI.
- Feature flags per module; canary/progressive rollout; rollback plan.

## Delivery

- **BFF per client** (mobile, web).
- **Feature flags** per module.
- **Accessibility CI** (axe-core) gates every PR.
