# Event Taxonomy and Tracking Plan

## Principles

- **Event-based analytics** with a stable, versioned schema.
- **One event = one user action or system fact**; no overloaded events.
- **PII-minimised** — no raw card/account numbers in events.

## Event taxonomy (examples)

| Event | Properties |
|---|---|
| `onboarding_started` | channel, referrer |
| `kyc_submitted` | doc_type, attempt |
| `account_funded` | amount_band, source |
| `payment_initiated` | type, amount_band, channel |
| `payment_completed` | latency_ms, status |
| `card_frozen` | reason, channel |
| `fraud_alert_shown` | score_band, action |
| `assistant_query` | intent, resolved_by (ai/human) |
| `goal_created` | target_amount_band, category |

## Tracking plan

- **Naming:** `snake_case`, noun_verb.
- **Versioning:** schema registry; breaking changes = new version.
- **Governance:** events owned by domain teams; reviewed at ARB.
- **Quality:** automated schema validation in CI; drop non-conforming events.

## Privacy

- Pseudonymise user IDs in analytics.
- No sensitive fields in event payloads.
- Honour consent and deletion requests.
