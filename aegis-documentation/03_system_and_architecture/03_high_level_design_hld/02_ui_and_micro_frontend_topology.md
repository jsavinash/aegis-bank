# UI and Micro-Frontend Topology

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

## State & data

- **Server state:** React Query / SWR.
- **Client state:** Zustand/Redux Toolkit for local UI.
- **Forms:** React Hook Form + Zod.
- **API:** GraphQL BFF for flexible queries.

## Delivery

- **BFF per client** (mobile, web).
- **Feature flags** per module.
- **Accessibility CI** (axe-core) gates every PR.