# Client, Normalised and Offline Storage
> **Scope:** all 37 feature-catalog sections + AI use cases (as features) are in scope per [`in_scope_and_out_of_scope.md`](../../01-business-and-strategy/05_scope_and_boundaries/in_scope_and_out_of_scope.md); out of scope: physical branches, proprietary payment scheme, own core-ledger build.

## Client storage

| Data | Storage | Notes |
|---|---|---|
| **Auth tokens** | Secure enclave / Keychain / Keystore | Never localStorage; biometric-gated |
| **Balances (cached)** | IndexedDB / MMKV | Read-only, with timestamp + TTL |
| **Statements (cached)** | IndexedDB | Encrypted; TTL + purge on logout |
| **Drafts / offline action queue** | Local storage / IndexedDB | Non-sensitive; replayed on reconnect |
| **Preferences / feature flags** | Local storage | Cached with TTL; stale-while-revalidate |
| **Reference data (FX, billers)** | IndexedDB | TTL + background refresh |

## Normalisation

- **Server state normalised** by entity id (account, transaction).
- **Cache keys** structured (`account:{id}`, `txn:{id}`).
- **Optimistic UI updates** reconcile to server truth; rollback on failure.
- **Cache invalidation** driven by server events (ledger/txn emitted).
- **No duplicated money truth** on the client — server is authoritative.

## Offline behaviour

- **Read-only** offline (cached balances, statements).
- **No queued money writes** — explicit retry with confirmation.
- **Conflict policy:** server-wins for money; client drafts never auto-submit.
- **Sync indicator** shows "last updated".
- **Draft payments** saved but not submitted until online + confirmed.
- **Background sync** on reconnect; **schema versioning/migrations** for cached data.

## Security

- **Encryption** at rest for cached sensitive data.
- **App-lock / biometric gating** for cached sensitive data.
- **Clear on logout** / remote logout; **cache TTL + purge**.
- **No card numbers** cached; tokens only.
- **Root/jailbreak detection** as defence-in-depth.