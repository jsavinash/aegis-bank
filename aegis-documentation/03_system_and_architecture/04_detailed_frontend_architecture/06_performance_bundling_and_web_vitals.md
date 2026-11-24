# Performance, Bundling and Web Vitals

## Web vitals targets

| Metric | Target |
|---|---|
| **LCP** | <2.5s |
| **INP** | <200ms |
| **CLS** | <0.1 |
| **TTFB** | <600ms |
| **FCP** | <1.8s |

## Bundling

- **Route-based code splitting**; lazy-load non-critical modules.
- **Tree-shaking**; modern build (Turbopack/webpack).
- **Asset optimisation:** images (WebP/AVIF), fonts (subset, `font-display: swap`).
- **CDN** for static assets; long-cache hashed filenames.

## Performance techniques

- **SSR/SSG** for first paint.
- **Prefetch** likely next routes.
- **Virtualisation** for long lists (transactions).
- **Skeleton** loading states.
- **Service worker** for repeat visits (read-only cache).

## Monitoring

- **RUM** (real user monitoring) via Grafana Faro / Datadog RUM.
- **Synthetic** checks for critical journeys.
- **Regression gates** in CI (Lighthouse CI).