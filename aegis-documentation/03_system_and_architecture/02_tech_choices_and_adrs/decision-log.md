# Decision Log

Binding architecture decisions. New decisions supersede old ones; the log is append-only.

| ID | Date | Decision | Status | ADR |
|---|---|---|---|---|
| D-001 | 2026-11-24 | Layered, event-driven architecture; buy core, build AI plane | Accepted | ADR-001 |
| D-002 | 2026-11-24 | Polyglot persistence (Postgres + Qdrant + Neo4j + ClickHouse + Iceberg) | Accepted | ADR-002 |
| D-003 | 2026-11-24 | Passkeys + biometrics + risk-adaptive MFA | Accepted | ADR-003 |
| D-004 | 2026-11-24 | Python for AI plane; Java (Spring Boot/Quarkus) for transactional; TypeScript for channels | Accepted | (tech choices) |
| D-005 | 2026-11-24 | Eval-gated CI blocks all AI changes | Accepted | (quality) |
| D-006 | 2026-11-24 | Fail-closed guardrails on all AI actions | Accepted | (security) |

## How to add a decision
1. Create an ADR from `000-adr-template.md`.
2. Get ARB approval (and Risk/Security where binding).
3. Add a row here with status and ADR link.
4. Supersede explicitly (mark old ADR Deprecated, link new).
