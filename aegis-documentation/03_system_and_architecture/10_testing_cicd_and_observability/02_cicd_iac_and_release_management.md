# CI/CD, IaC and Release Management

## CI pipeline

| Stage | Checks |
|---|---|
| **Lint** | ruff, eslint, prettier |
| **Type** | mypy, tsc |
| **Test** | unit, integration |
| **Contract** | OpenAPI/AsyncAPI lint, breaking-change gate |
| **Eval** | AI regression suite (blocking) |
| **Security** | SAST, SCA, secret scan |
| **A11y** | axe-core |
| **Build** | Docker images, signed |

## CD pipeline

- **GitOps:** ArgoCD reconciles desired state.
- **Environments:** dev → staging → prod, all IaC.
- **Progressive delivery:** canary → staged → full, with automated rollback.
- **Feature flags** decouple deploy from release.
- **Approval gates:** CAB for prod.

## IaC

- **Terraform** for cloud resources; modules per environment.
- **Helm/Kustomize** for K8s.
- **Policy as code** (OPA) for guardrails.

## Release management

- **SemVer** for services; **N-2** support for APIs.
- **Changelog** + release notes (user-facing).
- **Rollback** runbooks; kill switches for AI.

## Supply chain

- **Signed images** (cosign); SBOM; provenance (SLSA).