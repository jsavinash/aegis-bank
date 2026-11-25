# Feature & Use-Case Coverage Map

**Version:** 1.0 · **Date:** 24 Nov 2026

This is the master index proving that the **full feature set (~485 features, 37 domains)** and **all use cases (business + system + AI)** are covered throughout the `aegis-documentation/` set. Use it to navigate from any section to the authoritative feature and use-case sources.

## Authoritative sources

| Artifact | Location | Contents |
|---|---|---|
| **Full feature catalog (combined)** | [`01-business-and-strategy/14_feature_catalog.md`](./01-business-and-strategy/14_feature_catalog.md) | ~485 features + ~46 AI use cases (as features), 37 sections, tagged `[TYPE] [PRIORITY] [PROVEN]` |
| **All use cases** | [`03_system_and_architecture/01_requirements_and_capacity_estimation/07_use_case_catalog_all.md`](./03_system_and_architecture/01_requirements_and_capacity_estimation/07_use_case_catalog_all.md) | Business (BU), System (SY), AI (AI) use cases |
| **AI use cases** | [`01-business-and-strategy/14_feature_catalog.md`](01-business-and-strategy/14_feature_catalog.md) | ~46 AI use cases, archetypes, value/readiness/regulatory |
| **Feature → architecture traceability** | [`03_system_and_architecture/01_requirements_and_capacity_estimation/06_feature_traceability.md`](./03_system_and_architecture/01_requirements_and_capacity_estimation/06_feature_traceability.md) | 37 domains → components/services/data |
| **Language/framework per domain** | [`04_engineering_and_code/01_codebase_organization_and_standards/04_language_and_framework_mapping.md`](./04_engineering_and_code/01_codebase_organization_and_standards/04_language_and_framework_mapping.md) | All features + AI use cases → languages/frameworks |

## Coverage by section (where features & use cases are addressed)

| Section | Features covered in | Use cases covered in |
|---|---|---|
| **00 · Glossary** | Domain terms for all features | Agent archetypes (task/workflow/monitoring/multi-agent) |
| **01 · Business & Strategy** | `14_feature_catalog.md` (full), `08_high_level_functional_requirements.md`, `13_high_level_solution_overview.md` | `07_personas_and_business_scenarios.md`, `08_high_level_functional_requirements.md` |
| **02 · Product & UX** | `feature-prioritization-roadmap.md`, `04_permissions_and_plan_tiers.md` | `02-product-and-ux/01_users_and_motivations/jobs_to_be_done_and_value_prop.md`, `02-product-and-ux/03_experience_and_system_mapping/customer_journey_and_service_blueprint.md` |
| **03 · System & Architecture** | `01-business-and-strategy/14_feature_catalog.md`, `06_feature_traceability.md`, `02_functional_requirements_demand_supply.md`, `03_non_functional_requirements_matrix.md` | `07_use_case_catalog_all.md`, `06_ai_native_reference_architectures.md`, `05_agent_contracts_and_tools.md` |
| **04 · Engineering & Code** | `04_language_and_framework_mapping.md`, `05_module_to_stack_mapping.md` | `04_engineering_and_code/02_low_level_design_and_patterns/01_uml_modeling_and_system_use_cases.md`, `06_reference_implementation/` |
| **05 · Quality & Verification** | `acceptance-criteria-and-cases.md` (feature acceptance) | `acceptance-criteria-and-cases.md` (use-case → test cases), `master-test-strategy.md` |
| **06 · Operations & Infra** | Capacity from feature volumes (`05_back_of_envelope_capacity_estimation.md`) | Operational runbooks per use-case failure modes |
| **07 · Security, Compliance & Users** | Feature-level security/compliance (types SEC/COMP/ACC) | `support-escalation-matrix.md` (use-case-driven support), `user-help-center-guides.md` (per feature) |

## Feature-type coverage (15 types)

Every feature type is covered by at least one architectural component (see `06_feature_traceability.md`):

| Type | Count | Primary component | Doc |
|---|---|---|---|
| CORE | 32 | Core banking + Accounts | 07, 06 |
| PAY | 42 | Payments | 07, 06 |
| CARD | 29 | Cards | 07, 06 |
| LEND | 32 | Lending | 07, 06 |
| WEALTH | 43 | Wealth | 07, 06 |
| BIZ | 57 | Business | 07, 06 |
| SEC | 32 | Fraud + Identity () | 07, 06, 08 |
| AI | 44 | AI Platform () | 07, 06, 08 |
| DATA | 45 | Data platform | 07, 06 |
| OPEN | 7 | Open Banking | 07, 06 |
| LIFE | 39 | Lifestyle / Channels | 07, 06 |
| ESG | 16 | ESG | 07, 06 |
| COMP | 25 | Compliance () | 07, 06, 08 |
| ACC | 12 | Channels (WCAG 2.2 AA) | 07, 06 |
| PLAT | 25 | Platform / Data / Infra | 07, 06 |

> Counts are by **primary** feature type; a few features span multiple types, so the by-type total (~480) is approximate and reconciles to the ~485 catalog total in Appendix A.

## How to use this map

- **Building a feature?** → find its type in `06_feature_traceability.md`, then the language/framework in `04_language_and_framework_mapping.md`.
- **Writing a test?** → find the use case in `07_use_case_catalog_all.md`, then the acceptance criteria in `05-quality-and-verification/acceptance-criteria-and-cases.md`.
- **Designing an AI capability?** → find it in `01-business-and-strategy/14_feature_catalog.md`, then the reference architecture in `06_ai_native_reference_architectures.md`.
