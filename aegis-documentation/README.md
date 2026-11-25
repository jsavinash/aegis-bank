# Aegis Bank — Project Documentation

Full enterprise-grade documentation set for **Aegis Bank**, an AI-first retail digital bank + AI risk/compliance platform. This set mirrors the structure of a standard architecture documentation template and is populated entirely with Aegis-specific content.

**Project:** Aegis Bank — AI-first retail bank + AI-native risk & compliance plane
**Status:** Architecture / design documentation (pre-implementation)
**Owner:** AI Solutions Architect
**Last updated:** 2026-11-24

---

## Overview

Aegis Bank is a full-featured retail digital bank (~485 features across 37 domains) whose **AI layer is the product**: it is the fraud engine, the AML investigator, the compliance analyst, the virtual assistant, and the credit underwriter. This documentation covers business strategy, product/UX, system architecture, engineering, quality, operations, and security/compliance.

> **Features & use cases are covered in every section.** Start at the master map: [`FEATURE_AND_USE_CASE_COVERAGE.md`](./FEATURE_AND_USE_CASE_COVERAGE.md) — it links the full ~485-feature catalog, all business/system/AI use cases, the feature→architecture traceability, and where each is addressed across all 8 sections.

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

## Full document index

Every document in this set (grouped by section):

### Front matter

- [00-glossary-and-terminology.md](./00-glossary-and-terminology.md)
- [FEATURE_AND_USE_CASE_COVERAGE.md](./FEATURE_AND_USE_CASE_COVERAGE.md)

### 01 · Business & Strategy

- [approval_and_change_log.md](./01-business-and-strategy/00_executive_summary_and_signoffs/approval_and_change_log.md)
- [executive_summary.md](./01-business-and-strategy/00_executive_summary_and_signoffs/executive_summary.md)
- [01_project_overview_and_context.md](./01-business-and-strategy/01_project_overview_and_context.md)
- [02_stakeholders_and_governance.md](./01-business-and-strategy/02_stakeholders_and_governance.md)
- [03_business_goals_and_kpis.md](./01-business-and-strategy/03_business_goals_and_kpis.md)
- [as_is_workflow_and_pain_points.md](./01-business-and-strategy/04_current_vs_future_state/as_is_workflow_and_pain_points.md)
- [to_be_target_capabilities.md](./01-business-and-strategy/04_current_vs_future_state/to_be_target_capabilities.md)
- [assumptions_and_dependencies.md](./01-business-and-strategy/05_scope_and_boundaries/assumptions_and_dependencies.md)
- [in_scope_and_out_of_scope.md](./01-business-and-strategy/05_scope_and_boundaries/in_scope_and_out_of_scope.md)
- [06_tenets_and_guiding_principles.md](./01-business-and-strategy/06_tenets_and_guiding_principles.md)
- [07_personas_and_business_scenarios.md](./01-business-and-strategy/07_personas_and_business_scenarios.md)
- [08_high_level_functional_requirements.md](./01-business-and-strategy/08_high_level_functional_requirements.md)
- [09_non_functional_business_constraints.md](./01-business-and-strategy/09_non_functional_business_constraints.md)
- [build_vs_buy_options.md](./01-business-and-strategy/10_cost_roi_and_alternatives/build_vs_buy_options.md)
- [cost_and_roi_summary.md](./01-business-and-strategy/10_cost_roi_and_alternatives/cost_and_roi_summary.md)
- [11_risk_analysis_and_mitigation.md](./01-business-and-strategy/11_risk_analysis_and_mitigation.md)
- [12_domain_glossary.md](./01-business-and-strategy/12_domain_glossary.md)
- [13_high_level_solution_overview.md](./01-business-and-strategy/13_high_level_solution_overview.md)
- [14_feature_catalog.md](./01-business-and-strategy/14_feature_catalog.md)

### 02 · Product & UX

- [jobs_to_be_done_and_value_prop.md](./02-product-and-ux/01_users_and_motivations/jobs_to_be_done_and_value_prop.md)
- [user_personas_and_empathy_mapping.md](./02-product-and-ux/01_users_and_motivations/user_personas_and_empathy_mapping.md)
- [core_loops_and_habit_mechanics.md](./02-product-and-ux/02_core_value_and_retention/core_loops_and_habit_mechanics.md)
- [magic_moment_and_aha_activation.md](./02-product-and-ux/02_core_value_and_retention/magic_moment_and_aha_activation.md)
- [customer_journey_and_service_blueprint.md](./02-product-and-ux/03_experience_and_system_mapping/customer_journey_and_service_blueprint.md)
- [information_architecture_and_mental_model.md](./02-product-and-ux/03_experience_and_system_mapping/information_architecture_and_mental_model.md)
- [ux_to_system_state_mapping.md](./02-product-and-ux/03_experience_and_system_mapping/ux_to_system_state_mapping.md)
- [04_permissions_and_plan_tiers.md](./02-product-and-ux/04_permissions_and_plan_tiers.md)
- [empty_loading_and_error_states.md](./02-product-and-ux/05_edge_cases_and_resilience/empty_loading_and_error_states.md)
- [graceful_degradation_and_offline.md](./02-product-and-ux/05_edge_cases_and_resilience/graceful_degradation_and_offline.md)
- [trust_safety_and_abuse_vectors.md](./02-product-and-ux/05_edge_cases_and_resilience/trust_safety_and_abuse_vectors.md)
- [event_taxonomy_and_tracking_plan.md](./02-product-and-ux/06_analytics_and_experimentation/event_taxonomy_and_tracking_plan.md)
- [experimentation_and_feature_flags.md](./02-product-and-ux/06_analytics_and_experimentation/experimentation_and_feature_flags.md)
- [north_star_and_funnel_metrics.md](./02-product-and-ux/06_analytics_and_experimentation/north_star_and_funnel_metrics.md)
- [feature-prioritization-roadmap.md](./02-product-and-ux/feature-prioritization-roadmap.md)

### 03 · System & Architecture

- [01_scoping_and_constraints.md](./03_system_and_architecture/01_requirements_and_capacity_estimation/01_scoping_and_constraints.md)
- [02_functional_requirements_demand_supply.md](./03_system_and_architecture/01_requirements_and_capacity_estimation/02_functional_requirements_demand_supply.md)
- [03_non_functional_requirements_matrix.md](./03_system_and_architecture/01_requirements_and_capacity_estimation/03_non_functional_requirements_matrix.md)
- [04_extended_and_custom_requirements.md](./03_system_and_architecture/01_requirements_and_capacity_estimation/04_extended_and_custom_requirements.md)
- [05_back_of_envelope_capacity_estimation.md](./03_system_and_architecture/01_requirements_and_capacity_estimation/05_back_of_envelope_capacity_estimation.md)
- [06_feature_traceability.md](./03_system_and_architecture/01_requirements_and_capacity_estimation/06_feature_traceability.md)
- [07_use_case_catalog_all.md](./03_system_and_architecture/01_requirements_and_capacity_estimation/07_use_case_catalog_all.md)
- [01_full_stack_and_infra_tech_choices.md](./03_system_and_architecture/02_tech_choices_and_adrs/01_full_stack_and_infra_tech_choices.md)
- [02_rendering_and_delivery_strategy.md](./03_system_and_architecture/02_tech_choices_and_adrs/02_rendering_and_delivery_strategy.md)
- [03_ai_first_tech_stack_recommendation.md](./03_system_and_architecture/02_tech_choices_and_adrs/03_ai_first_tech_stack_recommendation.md)
- [04_ai_stack_pillars.md](./03_system_and_architecture/02_tech_choices_and_adrs/04_ai_stack_pillars.md)
- [000-adr-template.md](./03_system_and_architecture/02_tech_choices_and_adrs/architecture_decision_records/000-adr-template.md)
- [001-initial-architecture-baseline.md](./03_system_and_architecture/02_tech_choices_and_adrs/architecture_decision_records/001-initial-architecture-baseline.md)
- [002-database-selection.md](./03_system_and_architecture/02_tech_choices_and_adrs/architecture_decision_records/002-database-selection.md)
- [003-authentication-strategy.md](./03_system_and_architecture/02_tech_choices_and_adrs/architecture_decision_records/003-authentication-strategy.md)
- [decision-log.md](./03_system_and_architecture/02_tech_choices_and_adrs/decision-log.md)
- [01_end_to_end_system_architecture.md](./03_system_and_architecture/03_high_level_design_hld/01_end_to_end_system_architecture.md)
- [02_ui_and_micro_frontend_topology.md](./03_system_and_architecture/03_high_level_design_hld/02_ui_and_micro_frontend_topology.md)
- [03_backend_and_bff_service_boundaries.md](./03_system_and_architecture/03_high_level_design_hld/03_backend_and_bff_service_boundaries.md)
- [04_ai_and_ml_integration_pipeline.md](./03_system_and_architecture/03_high_level_design_hld/04_ai_and_ml_integration_pipeline.md)
- [05_agent_contracts_and_tools.md](./03_system_and_architecture/03_high_level_design_hld/05_agent_contracts_and_tools.md)
- [06_ai_native_reference_architectures.md](./03_system_and_architecture/03_high_level_design_hld/06_ai_native_reference_architectures.md)

- [01_component_hierarchy_and_design_system.md](./03_system_and_architecture/04_detailed_frontend_architecture/01_component_hierarchy_and_design_system.md)
- [02_state_management_and_client_cache.md](./03_system_and_architecture/04_detailed_frontend_architecture/02_state_management_and_client_cache.md)
- [03_routing_forms_and_validation.md](./03_system_and_architecture/04_detailed_frontend_architecture/03_routing_forms_and_validation.md)
- [04_browser_runtime_and_web_workers.md](./03_system_and_architecture/04_detailed_frontend_architecture/04_browser_runtime_and_web_workers.md)
- [05_accessibility_and_i18n_l10n.md](./03_system_and_architecture/04_detailed_frontend_architecture/05_accessibility_and_i18n_l10n.md)
- [06_performance_bundling_and_web_vitals.md](./03_system_and_architecture/04_detailed_frontend_architecture/06_performance_bundling_and_web_vitals.md)
- [01_core_workflows_and_distributed_algorithms.md](./03_system_and_architecture/05_detailed_backend_and_async_design/01_core_workflows_and_distributed_algorithms.md)
- [02_cdn_edge_and_distributed_caching.md](./03_system_and_architecture/05_detailed_backend_and_async_design/02_cdn_edge_and_distributed_caching.md)
- [03_event_queues_and_background_jobs.md](./03_system_and_architecture/05_detailed_backend_and_async_design/03_event_queues_and_background_jobs.md)
- [01_client_normalized_and_offline_storage.md](./03_system_and_architecture/06_data_model_and_storage/01_client_normalized_and_offline_storage.md)
- [02_database_schemas_and_erd.md](./03_system_and_architecture/06_data_model_and_storage/02_database_schemas_and_erd.md)
- [03_storage_search_and_data_lifecycle.md](./03_system_and_architecture/06_data_model_and_storage/03_storage_search_and_data_lifecycle.md)
- [04_integration_and_data_strategy.md](./03_system_and_architecture/06_data_model_and_storage/04_integration_and_data_strategy.md)
- [01_api_contracts_and_versioning.md](./03_system_and_architecture/07_api_and_network_communication/01_api_contracts_and_versioning.md)
- [02_data_exchange_formats.md](./03_system_and_architecture/07_api_and_network_communication/02_data_exchange_formats.md)
- [03_transfer_and_streaming_protocols.md](./03_system_and_architecture/07_api_and_network_communication/03_transfer_and_streaming_protocols.md)
- [04_network_resilience_and_rate_limiting.md](./03_system_and_architecture/07_api_and_network_communication/04_network_resilience_and_rate_limiting.md)
- [01_bottlenecks_spofs_and_hotspots.md](./03_system_and_architecture/08_bottlenecks_scalability_and_dr/01_bottlenecks_spofs_and_hotspots.md)
- [02_load_balancing_and_auto_scaling.md](./03_system_and_architecture/08_bottlenecks_scalability_and_dr/02_load_balancing_and_auto_scaling.md)
- [03_high_availability_backups_and_dr.md](./03_system_and_architecture/08_bottlenecks_scalability_and_dr/03_high_availability_backups_and_dr.md)
- [04_finops_and_cost_optimization.md](./03_system_and_architecture/08_bottlenecks_scalability_and_dr/04_finops_and_cost_optimization.md)
- [01_authn_authz_and_identity.md](./03_system_and_architecture/09_security_and_compliance/01_authn_authz_and_identity.md)
- [02_appsec_and_data_protection.md](./03_system_and_architecture/09_security_and_compliance/02_appsec_and_data_protection.md)
- [03_data_privacy_and_governance.md](./03_system_and_architecture/09_security_and_compliance/03_data_privacy_and_governance.md)
- [01_testing_strategy_and_ab_experiments.md](./03_system_and_architecture/10_testing_cicd_and_observability/01_testing_strategy_and_ab_experiments.md)
- [02_cicd_iac_and_release_management.md](./03_system_and_architecture/10_testing_cicd_and_observability/02_cicd_iac_and_release_management.md)
- [03_observability_rum_and_slos.md](./03_system_and_architecture/10_testing_cicd_and_observability/03_observability_rum_and_slos.md)

### 04 · Engineering & Code

- [01_repository_and_folder_structure.md](./04_engineering_and_code/01_codebase_organization_and_standards/01_repository_and_folder_structure.md)
- [02_coding_conventions_and_linting.md](./04_engineering_and_code/01_codebase_organization_and_standards/02_coding_conventions_and_linting.md)
- [03_dependency_management_and_di.md](./04_engineering_and_code/01_codebase_organization_and_standards/03_dependency_management_and_di.md)
- [04_language_and_framework_mapping.md](./04_engineering_and_code/01_codebase_organization_and_standards/04_language_and_framework_mapping.md)
- [05_module_to_stack_mapping.md](./04_engineering_and_code/01_codebase_organization_and_standards/05_module_to_stack_mapping.md)
- [01_uml_modeling_and_system_use_cases.md](./04_engineering_and_code/02_low_level_design_and_patterns/01_uml_modeling_and_system_use_cases.md)
- [02_class_interface_and_model_definitions.md](./04_engineering_and_code/02_low_level_design_and_patterns/02_class_interface_and_model_definitions.md)
- [03_design_patterns_and_principles.md](./04_engineering_and_code/02_low_level_design_and_patterns/03_design_patterns_and_principles.md)
- [04_state_machines_and_workflow_engines.md](./04_engineering_and_code/02_low_level_design_and_patterns/04_state_machines_and_workflow_engines.md)
- [01_concurrency_threading_and_async_model.md](./04_engineering_and_code/03_concurrency_and_runtime_execution/01_concurrency_threading_and_async_model.md)
- [02_memory_management_and_resource_pools.md](./04_engineering_and_code/03_concurrency_and_runtime_execution/02_memory_management_and_resource_pools.md)
- [03_synchronization_and_locking.md](./04_engineering_and_code/03_concurrency_and_runtime_execution/03_synchronization_and_locking.md)
- [01_public_methods_and_sdk_contracts.md](./04_engineering_and_code/04_internal_apis_and_extensibility/01_public_methods_and_sdk_contracts.md)
- [02_extensibility_and_plugin_architecture.md](./04_engineering_and_code/04_internal_apis_and_extensibility/02_extensibility_and_plugin_architecture.md)
- [03_error_handling_and_resilience_code.md](./04_engineering_and_code/04_internal_apis_and_extensibility/03_error_handling_and_resilience_code.md)
- [01_unit_and_integration_test_patterns.md](./04_engineering_and_code/05_code_quality_and_developer_workflow/01_unit_and_integration_test_patterns.md)
- [02_git_workflow_and_code_review_guide.md](./04_engineering_and_code/05_code_quality_and_developer_workflow/02_git_workflow_and_code_review_guide.md)
- [README.md](./04_engineering_and_code/06_reference_implementation/README.md)

### 05 · Quality & Verification

- [acceptance-criteria-and-cases.md](./05-quality-and-verification/acceptance-criteria-and-cases.md)
- [master-test-strategy.md](./05-quality-and-verification/master-test-strategy.md)
- [performance-and-load-reports.md](./05-quality-and-verification/performance-and-load-reports.md)

### 06 · Operations & Infrastructure

- [cicd-and-release-process.md](./06-operations-and-infrastructure/cicd-and-release-process.md)
- [cloud-infrastructure-topology.md](./06-operations-and-infrastructure/cloud-infrastructure-topology.md)
- [disaster-recovery-plan.md](./06-operations-and-infrastructure/disaster-recovery-plan.md)
- [environments-and-secrets.md](./06-operations-and-infrastructure/environments-and-secrets.md)
- [db-failover-runbook.md](./06-operations-and-infrastructure/incident-runbooks/db-failover-runbook.md)
- [high-latency-troubleshooting.md](./06-operations-and-infrastructure/incident-runbooks/high-latency-troubleshooting.md)

### 07 · Security, Compliance & Users

- [release-notes-changelog.md](./07-security-compliance-and-users/end-user-and-support/release-notes-changelog.md)
- [support-escalation-matrix.md](./07-security-compliance-and-users/end-user-and-support/support-escalation-matrix.md)
- [user-help-center-guides.md](./07-security-compliance-and-users/end-user-and-support/user-help-center-guides.md)
- [data-privacy-and-retention.md](./07-security-compliance-and-users/security-and-compliance/data-privacy-and-retention.md)
- [threat-model-and-encryption.md](./07-security-compliance-and-users/security-and-compliance/threat-model-and-encryption.md)

## Related documents

The `docs/` folder contains the research, product spec, feature catalog, AI architectures, use-case catalog, tech-stack recommendation, and language/framework mapping that this documentation set is derived from. This `aegis-documentation/` folder is the formal, numbered project documentation set.

## Status rules

- **Draft** — authored, not reviewed.
- **Review** — in architecture review board.
- **Approved** — signed off; changes require a new ADR or decision-log entry.
- **Deprecated** — superseded; see decision log.
