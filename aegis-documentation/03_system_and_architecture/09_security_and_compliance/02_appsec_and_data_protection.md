# AppSec and Data Protection

## Application security

| Area | Control |
|---|---|
| **Input validation** | Schema validation everywhere; Zod/Pydantic |
| **Injection** | Parameterised queries; output encoding; CSP |
| **Authn/Z** | Centralised (see identity doc) |
| **Secrets** | Vault; no secrets in code/config |
| **Dependencies** | SCA scanning; pinned versions |
| **SAST/DAST** | In CI/CD |
| **Pen-test** | Annual + pre-launch |

## AI-specific security (OWASP LLM Top 10 / MCP Top 10)

| Risk | Mitigation |
|---|---|
| **Prompt injection** | NeMo input/output/tool-call rails; treat tool results as untrusted |
| **Tool poisoning** | Least-privilege MCP; approval gates; signed principals |
| **Sensitive data leakage** | DLP; PII masking; no secrets in prompts/logs |
| **Model DoS** | Rate limits; budgets; timeouts |
| **Hallucinated actions** | Citation-required generation; groundedness verifier; HITL |

## Data protection

| Control | Detail |
|---|---|
| **Encryption** | TLS 1.3 in transit; AES-256 at rest |
| **Tokenisation** | Card numbers tokenised (PCI-DSS) |
| **Field-level** | Sensitive fields encrypted (PII) |
| **Key management** | HSM-backed (Vault/KMS); rotation |
| **DLP** | Prevent sensitive data egress |
| **Logging** | No PII/secrets in logs |

## Secrets

- **Vault** for dynamic secrets; rotation.
- **No long-lived keys** in CI (OIDC federation).