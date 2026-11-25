# Browser Runtime and Web Workers

## Browser runtime

- **Target:** modern evergreen browsers (last 2 versions); graceful degradation for older.
- **Feature detection** over UA sniffing; **minimal conditional polyfills**; documented support matrix.
- **Security:** strict CSP, no inline scripts, SRI for third-party.
- **Storage:** IndexedDB for offline cache; no sensitive data in localStorage.
- **Monitoring:** window error/unhandledrejection → Sentry/RUM.

## Web workers

- **Heavy computation** (large statement parsing, client-side analysis) offloaded to Web Workers.
- **Image processing** (cheque capture, ID scan) in workers.
- **Dedicated/shared workers** with a **worker pool**; **transferables** (ArrayBuffer/ImageBitmap) to avoid copies.
- **Background sync** for retry of non-money operations.

## Performance budgets

- **Main thread:** keep tasks <50ms; offload the rest.
- **Scheduling:** yield via `scheduler.postTask` / `requestIdleCallback`; protect **INP**.
- **Bundle size:** route-based code splitting; lazy-load heavy modules.
- **Memory:** avoid leaks; release object URLs and listeners; cap worker count.

## Progressive web app

- **Service worker** for offline read-only cache.
- **Installable** (manifest, icons).
- **Update strategy:** versioned SW; prompt/skipWaiting to avoid stale clients.
- **Push** notifications + **background sync** for non-money retries.
- **Never** cache authenticated money data in the service worker.