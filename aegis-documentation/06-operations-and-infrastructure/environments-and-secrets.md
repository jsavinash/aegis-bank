# Environments and Secrets

## Environments

| Env | Purpose | Data |
|---|---|---|
| **dev** | Local/feature dev | Synthetic only |
| **staging** | Integration, E2E, load | Anonymised / synthetic |
| **prod** | Production | Real (protected) |

## Configuration

- **12-factor:** config via env vars / secrets manager.
- **Per-env** Terraform workspaces.
- **No secrets in code or images.**

## Secrets management

| Secret | Store | Rotation |
|---|---|---|
| **DB credentials** | Vault / AWS Secrets Manager | Automatic |
| **API keys (LLM)** | Vault (dynamic where possible) | 90 days |
| **TLS certs** | ACM / cert-manager | Automatic |
| **Signing keys** | KMS/HSM | Per policy |

## Access

- **No long-lived CI keys** — OIDC federation to cloud.
- **Just-in-time** access for prod (break-glass with approval).
- **Least privilege** IAM roles.

## Secret hygiene

- **Scan** for secrets in CI (gitleaks).
- **Rotate** on suspected leak.
- **Audit** secret access.