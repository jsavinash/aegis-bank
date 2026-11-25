# Data Privacy and Governance

## Privacy principles

- **Data minimisation** — collect only what is needed.
- **Purpose limitation** — use data only for stated purposes.
- **Consent** — explicit, revocable, expiring (open banking).
- **Transparency** — customers see who accesses their data.
- **Right to erasure** — GDPR/CCPA DSAR automation.
- **Privacy by design & accountability** — DPIA for high-risk processing.

## Data governance

| Area | Practice |
|---|---|
| **Catalogue** | OpenMetadata/DataHub; lineage |
| **Classification** | PII/PCI/confidential tags |
| **Retention** | Per data type (7-year txn; 5-year AML) |
| **Residency** | Region-locked storage/processing |
| **Access** | Role-based; audited |
| **Quality** | Contracts + dbt tests |
| **Records** | RoPA (records of processing); data-sharing register |

## Data subject rights

- **Access, rectification, portability, restriction, objection, erasure** — automated via DSAR workflow with SLA.

## Consent management

- **Lifecycle:** grant → use → renew → revoke → delete.
- **Open banking:** consent with expiry; customer dashboards.
- **AI:** opt-in consent for model training on customer data.

## Regulatory

- **GDPR / CCPA:** DSAR, erasure, portability; **breach notification (72h)**.
- **PSD2/3:** consent, SCA; **ePrivacy**.
- **EU AI Act:** transparency, human oversight, model documentation.
- **Cross-border:** SCCs/adequacy for transfers; **data residency** in-VPC.

## Roles

- **Controller/processor** mapping; **DPO** owns privacy; **data owners** per domain.

## Crypto-shredding

- Per-customer keys; delete the key to "erase" data where physical deletion is impractical.
