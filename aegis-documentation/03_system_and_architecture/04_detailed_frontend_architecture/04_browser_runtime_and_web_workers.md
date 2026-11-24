# Browser Runtime and Web Workers

## Browser runtime

- **Target:** modern evergreen browsers (last 2 versions); graceful degradation for older.
- **Polyfills:** minimal, conditional.
- **Security:** strict CSP, no inline scripts, SRI for third-party.
- **Storage:** IndexedDB for offline cache; no sensitive data in localStorage.

## Web workers

- **Heavy computation** (large statement parsing, client-side analysis) offloaded to Web Workers.
- **Image processing** (cheque capture, ID scan) in workers.
- **Background sync** for retry of non-money operations.

## Performance budgets

- **Main thread:** keep tasks <50ms; offload the rest.
- **Bundle size:** route-based code splitting; lazy-load heavy modules.
- **Memory:** avoid leaks; release object URLs and listeners.

## Progressive web app

- **Service worker** for offline read-only cache.
- **Installable** (manifest, icons).
- **Never** cache authenticated money data in the service worker.