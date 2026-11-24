# Data Privacy and Governance

## Privacy principles

- **Data minimisation** — collect only what is needed.
- **Purpose limitation** — use data only for stated purposes.
- **Consent** — explicit, revocable, expiring (open banking).
- **Transparency** — customers see who accesses their data.
- **Right to erasure** — GDPR/CCPA DSAR automation.

## Data governance

| Area | Practice |
|---|---|
| **Catalogue** | OpenMetadata/DataHub; lineage |
| **Classification** | PII/PCI/confidential tags |
| **Retention** | Per data type (e.g., 7-year txn; 5-year AML) |
| **Residency** | Region-locked storage/processing |
| **Access** | Role-based; audited |
| **Quality** | Contracts + dbt tests |

## Consent management

- **Lifecycle:** grant → use → renew → revoke → delete.
- **Open banking:** consent with expiry; dashboards for customers.
- **AI:** consent for model training on customer data (opt-in).

## Regulatory

- **GDPR / CCPA:** DSAR, right to erasure, portability.
- **PSD2/3:** consent, SCA.
- **EU AI Act:** transparency, human oversight.
- **Data residency:** in-VPC, region-pinned.

## Crypto-shredding

- Encrypt per-customer data with per-customer keys; delete the key to "erase" data where physical deletion is impractical.