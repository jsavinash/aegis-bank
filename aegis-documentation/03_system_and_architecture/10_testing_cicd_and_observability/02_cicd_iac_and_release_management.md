# CI/CD, IaC and Release Management

## Branching & governance

- **Trunk-based** development; short-lived branches; **CODEOWNERS** + required reviews.
- **Required status checks**; signed commits; **no direct pushes** to main.

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

- Cached/parallel; required checks block merge.

## CD pipeline

- **GitOps:** ArgoCD reconciles desired state.
- **Environments:** dev → staging → prod, all IaC.
- **Progressive delivery:** canary → staged → full, with automated rollback.
- **Feature flags** decouple deploy from release; **CAB** approval for prod.

## IaC

- **Terraform** for cloud; **Helm/Kustomize** for K8s; **OPA** policy as code.
- **Drift detection** + state locking; plan review before apply.

## Release management

- **SemVer**; **N-2** API support; **changelog** + user release notes.
- **Rollback** runbooks; **kill switches** for AI; **hotfix** path + release calendar.

## Supply chain

- **Signed images** (cosign); **SBOM**; **provenance** (SLSA).

## DORA metrics

- Deployment frequency, lead time, change failure rate, MTTR — tracked and improved.