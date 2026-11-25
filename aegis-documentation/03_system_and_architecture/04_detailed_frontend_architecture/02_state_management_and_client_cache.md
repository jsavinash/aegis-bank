# State Management and Client Cache

## Server state

- **React Query / SWR** for server data (balances, transactions, accounts).
- **Caching:** stale-while-revalidate; background refetch; **TTL + eviction**.
- **Invalidation:** targeted after mutations (payment → invalidate balance).
- **Retry/backoff + timeouts;** error/loading/empty states; error boundaries.

## Client state

- **Zustand / Redux Toolkit** for local UI state (current screen, form drafts, preferences).
- **URL state** for shareable/deep-linkable views (account, transaction).
- **Selectors** for derived state; **partial persistence** (non-sensitive only).
- **Multi-tab sync** via BroadcastChannel/storage events.

## Forms

- **React Hook Form + Zod** — schema validation, accessible errors.

## Optimistic updates

- **Freeze card, create goal** — optimistic with rollback on failure.
- **Money movements** — never optimistic; confirm then update from server.

## Cache & offline

- **Cached read-only** (balances, statements) available offline with "last updated".
- **Never cache sensitive data** in plaintext.
- **Write queue:** not used for money; only explicit retry.
- **Schema versioning/migrations** for persisted cache; purge on logout.

## Consistency

- **Single source of truth** per entity; cache keys normalised (by id).
- **Conflict policy:** server-wins for money; client drafts never auto-submit.
- **Sync:** WebSocket/SSE for real-time balance updates where needed.

## Testing & tooling

- **MSW** request mocking; **seeded stores** for deterministic tests.
- **Redux/React Query devtools** in development only.