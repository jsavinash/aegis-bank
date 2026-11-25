# CI/CD and Release Process

## Pipeline stages

1. **Lint & type** (ruff, mypy, eslint, tsc).
2. **Unit + integration** tests.
3. **Contract** validation (OpenAPI/AsyncAPI).
4. **AI eval gate** (blocking).
5. **Security** (SAST, SCA, secret scan).
6. **Accessibility** (axe-core).
7. **Build** signed images (cosign, SBOM).
8. **Deploy** to staging (ArgoCD).
9. **E2E + load** on staging.
10. **Progressive release** to prod (canary → full).

## Release strategy

- **Trunk-based** with feature flags.
- **Canary** (5% → 25% → 100%) with automated rollback on SLO burn.
- **Blue-green** for Tier 0.
- **Feature flags** decouple deploy from release; kill switch for AI.

## Approvals

- **CAB** approves prod releases.
- **Risk/Security** sign-off for changes touching AML/KYC/fraud.

## Rollback

- **Automated** on SLO breach.
- **Manual** runbook for complex cases.
- **Idempotency** ensures safe replay.

## Release notes

- Auto-generated from conventional commits; user-facing notes in [`../07-security-compliance-and-users/end-user-and-support/release-notes-changelog.md`](../07-security-compliance-and-users/end-user-and-support/release-notes-changelog.md).
