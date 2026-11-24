# Aegis Bank — Project Documentation

Full enterprise-grade documentation set for **Aegis Bank**, an AI-first retail digital bank + AI risk/compliance platform. This set mirrors the structure of a standard architecture documentation template and is populated entirely with Aegis-specific content.

**Project:** Aegis Bank — AI-first retail bank + AI-native risk & compliance plane
**Status:** Architecture / design documentation (pre-implementation)
**Owner:** AI Solutions Architect
**Last updated:** 2026-11-24

---

## Overview

Aegis Bank is a full-featured retail digital bank (~600 features across 37 domains) whose **AI layer is the product**: it is the fraud engine, the AML investigator, the compliance analyst, the virtual assistant, and the credit underwriter. This documentation covers business strategy, product/UX, system architecture, engineering, quality, operations, and security/compliance.

> **Features & use cases are covered in every section.** Start at the master map: [`FEATURE_AND_USE_CASE_COVERAGE.md`](./FEATURE_AND_USE_CASE_COVERAGE.md) — it links the full ~600-feature catalog, all business/system/AI use cases, the feature→architecture traceability, and where each is addressed across all 8 sections.

## Navigation

| Folder | Contents |
|---|---|
| `00-glossary-and-terminology.md` | Single source of truth for terms and acronyms |
| `01-business-and-strategy/` | Executive summary, sign-offs, scope, stakeholders, KPIs, personas, cost/ROI, risks, solution overview |
| `02-product-and-ux/` | Personas/JTBD, value/retention, journeys, permissions, edge cases, analytics, roadmap |
| `03_system_and_architecture/` | Requirements & capacity, tech choices & ADRs, HLD, frontend, backend/async, data, API, scalability/DR, security, testing/observability |
| `04_engineering_and_code/` | Repo structure, conventions, LLD/UML, concurrency, internal APIs, quality/workflow, reference implementation |
| `05-quality-and-verification/` | Test strategy, acceptance criteria, performance reports |
| `06-operations-and-infrastructure/` | Cloud topology, environments/secrets, CI/CD, DR, incident runbooks |
| `07-security-compliance-and-users/` | Threat model, privacy, help guides, escalation, release notes |

## Source-of-truth hierarchy

When documents conflict, precedence is:

1. `03_system_and_architecture/02_tech_choices_and_adrs/decision-log.md` + ADRs (binding decisions)
2. `03_system_and_architecture/03_high_level_design_hld/01_end_to_end_system_architecture.md` (as-built design)
3. `01-business-and-strategy/01_project_overview_and_context.md` (scope, SLAs, constraints)
4. `01-business-and-strategy/13_high_level_solution_overview.md` (solution overview)
5. Everything else (supporting detail)

## Related documents

The `docs/` folder contains the research, product spec, feature catalog, AI architectures, use-case catalog, tech-stack recommendation, and language/framework mapping that this documentation set is derived from. This `aegis-documentation/` folder is the formal, numbered project documentation set.

## Status rules

- **Draft** — authored, not reviewed.
- **Review** — in architecture review board.
- **Approved** — signed off; changes require a new ADR or decision-log entry.
- **Deprecated** — superseded; see decision log.