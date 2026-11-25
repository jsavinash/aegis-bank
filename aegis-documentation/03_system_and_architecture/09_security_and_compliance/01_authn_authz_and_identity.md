# Authentication, Authorization and Identity

## Authentication

| Factor | Implementation |
|---|---|
| **Primary** | Passkeys (FIDO2/WebAuthn) |
| **Biometric** | Face/Touch/Voice unlock passkey |
| **Step-up** | OTP/push for high-risk actions |
| **Fallback** | OTP for unsupported devices |
| **Device binding** | Secure enclave key (App Attest/Play Integrity) |

- **Recovery:** recovery codes + identity re-verification; **passkey sync** where platform supports.
- **Credential-stuffing / brute-force:** rate limits, breached-password checks, anomaly detection.

## Authorization

- **RBAC** roles: customer, joint, teen, business admin/member, analyst, compliance, auditor, agent.
- **ABAC** for context (device, geo, amount); **policy engine** (OPA/Cedar).
- **Scopes** per action (`account:read`, `payment:write`, `case:approve`).
- **Step-up auth** for high-risk actions; **separation of duties** (maker-checker).
- **Least privilege** for service principals (signed agent identity, HMAC).

## Identity

- **IdP:** Keycloak or Ory (self-hosted, OIDC).
- **CIAM:** self-service registration, recovery, consent.
- **Agent identity:** short-lived signed principals; credentials scoped per task.

## Session management

- **Short-lived access tokens**; refresh with rotation; **token binding** + introspection.
- **Idle + absolute timeouts**; **remote logout**; session revocation.
- **Anomaly detection** on logins (new device, geo).

## Compliance

- PSD2 SCA, GDPR, AML/KYC identity assurance; **NIST 800-63 / eIDAS** assurance levels.