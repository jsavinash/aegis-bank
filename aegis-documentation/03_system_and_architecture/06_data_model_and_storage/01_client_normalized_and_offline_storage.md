# Client, Normalised and Offline Storage

## Client storage

| Data | Storage | Notes |
|---|---|---|
| **Auth tokens** | Secure enclave / Keychain / Keystore | Never localStorage |
| **Balances (cached)** | IndexedDB / MMKV | Read-only, with timestamp |
| **Statements (cached)** | IndexedDB | Encrypted |
| **Drafts** | Local storage | Non-sensitive only |
| **Preferences** | Local storage | Prefs, flags |

## Normalisation

- **Server state normalised** by entity id (account, transaction).
- **Cache keys** structured (`account:{id}`, `txn:{id}`).
- **No duplicated money truth** on the client — server is authoritative.

## Offline behaviour

- **Read-only** offline (cached balances, statements).
- **No queued money writes** — explicit retry with confirmation.
- **Sync indicator** shows "last updated".
- **Draft payments** saved but not submitted until online + confirmed.

## Security

- **Encryption** at rest for cached sensitive data.
- **Clear on logout** / remote logout.
- **No card numbers** cached; tokens only.