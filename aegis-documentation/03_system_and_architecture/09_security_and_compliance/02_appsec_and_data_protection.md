# AppSec and Data Protection

## Application security

| Area | Control |
|---|---|
| **Input validation** | Schema validation everywhere; Zod/Pydantic |
| **Injection** | Parameterised queries; output encoding; CSP |
| **Authn/Z** | Centralised (see identity doc) |
| **Secrets** | Vault; no secrets in code/config |
| **Dependencies** | SCA + **SBOM**; pinned versions |
| **SAST/DAST** | In CI/CD |
| **Containers/IaC** | Image + Terraform scanning |
| **Headers/CORS/CSRF** | Strict CSP, HSTS, CORS allowlist, CSRF tokens |
| **Pen-test** | Annual + pre-launch |

- **Threat modeling** per feature; **secure SDLC** (security champions, review gates).

## AI-specific security (OWASP LLM Top 10 / MCP Top 10)

| Risk | Mitigation |
|---|---|
| **Prompt injection** | NeMo input/output/tool-call rails; tool results untrusted |
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
| **Classification/retention** | PII classes; retention + **crypto-shredding** |
| **DSR** | Access/export/delete fulfilment |
| **Logging** | No PII/secrets in logs |

## Secrets

- **Vault** dynamic secrets + rotation; **no long-lived keys** in CI (OIDC federation).

## Vulnerability & incident response

- **SLA** by severity (critical 24h); **IR plan** + on-call; **SIEM** alerting; post-incident review.
