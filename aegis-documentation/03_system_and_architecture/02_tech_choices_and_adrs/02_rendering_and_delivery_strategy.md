# Rendering and Delivery Strategy

## Channels

| Channel | Technology | Strategy |
|---|---|---|
| **Mobile** | React Native (Expo) / Flutter | One codebase, native modules for biometrics/passkeys |
| **Web** | Next.js (TypeScript) | SSR/SSG, accessible components |
| **Voice** | Agentic assistant (telephony) | ASR → agent → TTS |
| **Ops console** | Next.js | Internal, RBAC |

## Delivery

- **BFF pattern:** a GraphQL/REST BFF per client (mobile, web) aggregates backend calls.
- **Edge caching:** CDN for static assets; no caching of sensitive API responses.
- **Offline:** cached read-only data (balances, statements); never queue money writes.
- **Progressive enhancement:** core journeys work on low-end devices and slow networks.

## Rendering strategy

- **SSR/SSG** for web (SEO, first paint).
- **Native rendering** on mobile (performance).
- **Streaming** for AI responses (assistant tokens).
- **Accessibility:** semantic HTML, ARIA, WCAG 2.2 AA.

## Feature delivery

- Feature flags decouple deploy from release.
- Canary → staged → full rollout.
- Kill switch for every AI capability.
