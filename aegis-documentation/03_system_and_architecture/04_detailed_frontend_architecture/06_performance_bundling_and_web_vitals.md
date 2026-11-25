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
- **Tree-shaking**; modern build (Turbopack/webpack); **bundle analyzer** in CI.
- **Asset optimisation:** images (WebP/AVIF, responsive `srcset`), fonts (subset, `font-display: swap`).
- **Compression:** Brotli/gzip; **HTTP/2–3**; **CDN** with long-cache hashed filenames.
- **Dependency audit** (size + licences); budget limits per route.

## Performance techniques

- **SSR/SSG** for first paint; **priority hints** (preload hero, `fetchpriority`).
- **Prefetch** likely next routes.
- **Virtualisation** for long lists (transactions).
- **CLS:** reserve space for media/ads; avoid layout shifts.
- **INP:** break long tasks; offload to workers; debounce inputs.
- **Skeleton** loading states; **defer third-party** scripts.
- **Service worker** for repeat visits (read-only cache).

## Monitoring

- **RUM** (Grafana Faro / Datadog RUM) segmented by device/geo/route.
- **Synthetic** checks for critical journeys.
- **Alerts** on budget breach; dashboards per route.

## Budgets & regression gates

- **Lighthouse CI** budgets (LCP/INP/CLS/TBT) block merges.
- **Bundle-size** limits per route; fail on regression.
