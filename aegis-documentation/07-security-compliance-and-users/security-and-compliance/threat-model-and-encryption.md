# Threat Model and Encryption

## Threat model (STRIDE)

| Threat | Example | Mitigation |
|---|---|---|
| **Spoofing** | Account takeover | Passkeys, device binding, anomaly detection |
| **Tampering** | Payment tampering | Idempotency, signing, integrity checks |
| **Repudiation** | Deny a transaction | Immutable audit log |
| **Information disclosure** | PII leak | Encryption, DLP, least privilege |
| **Denial of service** | Flood API | Rate limiting, WAF, load shedding |
| **Elevation of privilege** | Agent overreach | Least-privilege MCP, approval gates |

## AI-specific threats (OWASP LLM Top 10)

| Threat | Mitigation |
|---|---|
| **Prompt injection** | NeMo input/output/tool-call rails; treat tool results as untrusted |
| **Insecure output handling** | Structured output, groundedness verifier |
| **Data poisoning** | Data contracts, validation, provenance |
| **Model theft** | Access control, rate limits, watermarking |
| **Tool misuse** | Least privilege, approval gates, signed principals |

## Encryption

| Data | At rest | In transit |
|---|---|---|
| **Database** | AES-256 (KMS) | TLS 1.3 |
| **Card data** | Tokenisation + AES-256 | TLS + PCI scope isolation |
| **PII** | Field-level encryption | TLS 1.3 |
| **Backups** | AES-256 + immutable | TLS |
| **Secrets** | Vault (HSM-backed) | mTLS |

## Key management

- **KMS/HSM** with rotation; envelope encryption.
- **Per-customer keys** for crypto-shredding.
- **Separation of duties** for key access.

## Security testing

- SAST, DAST, dependency scan, pen-test, red-team (AI included).
