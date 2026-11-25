# ADR-003: Authentication Strategy

- **Status:** Accepted
- **Date:** 2026-11-24
- **Deciders:** CISO, Chief Architect

## Context
Only 5% of 2026 banking apps ship passkeys (Ostorlab). Phishing-resistant, passwordless auth is a security differentiator and regulatory expectation.

## Decision
- **Passkeys (FIDO2/WebAuthn)** as the primary factor.
- **Biometrics** (Face/Touch/Voice) unlock the passkey.
- **Risk-adaptive MFA** — step-up (OTP/push) for high-risk actions.
- **Device binding** — private key in secure enclave (Play Integrity / App Attest).
- **Identity provider:** Keycloak or Ory (self-hosted).
- **Signed agent principals** (HMAC) for machine identities.

## Alternatives
| Option | Pros | Cons |
|---|---|---|
| Password + OTP | Familiar | Phishable, poor UX |
| SMS OTP | Ubiquitous | SIM-swap risk |
| Passkeys + biometrics | Phishing-resistant | Requires device support, fallback |

## Consequences
- Fallback path (OTP) must exist for unsupported devices.
- Step-up auth integrated into high-risk journeys.

## Compliance
Strong Customer Authentication (PSD2 SCA); phishing-resistant MFA.
