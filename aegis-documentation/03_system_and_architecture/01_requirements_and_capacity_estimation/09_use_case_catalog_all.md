# Use-Case Catalog — All Use Cases (Business, System, AI)

**Version:** 1.0 · **Date:** 24 Nov 2026 · **Companion to:** [02_functional_requirements_demand_supply.md](./02_functional_requirements_demand_supply.md), [07_full_feature_catalog.md](./07_full_feature_catalog.md), [08_ai_use_case_catalog.md](./08_ai_use_case_catalog.md)

Consolidated catalog of **all use cases** for Aegis Bank across three lenses: **business** (what the bank does), **system** (technical capabilities), and **AI** (agentic/ML use cases). The AI use cases are detailed in [08_ai_use_case_catalog.md](./08_ai_use_case_catalog.md); this document consolidates and indexes them all.

## 1. Business use cases (actors & outcomes)

| ID | Use case | Primary actor | Outcome |
|---|---|---|---|
| BU-01 | Open an account | Customer | Account opened, KYC verified (<10 min) |
| BU-02 | Fund account | Customer | Account funded (open banking / ACH) |
| BU-03 | Make a payment / transfer | Customer | Payment settled, confirmed |
| BU-04 | Pay a bill | Customer | Bill paid, scheduled if requested |
| BU-05 | Manage cards (freeze, limits) | Customer | Card controlled instantly |
| BU-06 | Apply for a loan | Customer | Decision (auto or human) with reason |
| BU-07 | Save toward a goal | Customer | Goal created, auto-save active |
| BU-08 | Invest (robo / self-directed) | Customer | Portfolio created / trade executed |
| BU-09 | Get help / ask assistant | Customer | Answer or human escalation |
| BU-10 | Dispute a transaction | Customer | Dispute raised, investigated |
| BU-11 | Run a business (invoice, expenses) | Business owner | Invoice sent, expenses reconciled |
| BU-12 | Send/receive cross-border / QR | Traveller | FX settled, QR paid |
| BU-13 | Manage consent (open banking) | Customer | Consent granted/revoked/expired |
| BU-14 | Detect & respond to fraud | Bank | Fraud stopped, customer protected |
| BU-15 | Investigate AML alert & file SAR | Analyst | Case built, SAR filed (HITL) |
| BU-16 | Assess credit risk | Underwriter | Explainable decision |
| BU-17 | Monitor regulatory change | Compliance | Obligations mapped to controls |
| BU-18 | Report to regulators | Compliance | Audit-ready reports |

## 2. System use cases (technical capabilities)

| ID | Use case | Component |
|---|---|---|
| SY-01 | Authenticate (passkey/MFA) | Identity (Keycloak/Ory) |
| SY-02 | Score transaction for fraud | Fraud (aegis/risk) |
| SY-03 | Post to ledger | Core banking |
| SY-04 | Emit & consume events | Kafka / Flink |
| SY-05 | Retrieve knowledge (RAG) | LlamaIndex + Qdrant + Neo4j |
| SY-06 | Run an agent workflow | LangGraph + Temporal |
| SY-07 | Enforce a guardrail | NeMo + Llama Guard |
| SY-08 | Record an audit event | Audit log |
| SY-09 | Evaluate a model/prompt | Ragas + Braintrust (CI) |
| SY-10 | Screen a tool result (injection) | Guardrails |
| SY-11 | Route a model tier | LiteLLM |
| SY-12 | Reconcile accounts | Temporal workflow |
| SY-13 | Generate statements | Documents service |
| SY-14 | Send notifications | Notifications service |
| SY-15 | Provision a card | Cards service |

## 3. AI use cases (subset of 46 — full detail in 08)

| ID | AI use case | Archetype | Value | Reg |
|---|---|---|---|---|
| AI-01 | First-line contact centre agent | Workflow | H | M |
| AI-02 | Account & card servicing agent | Task | H | M |
| AI-03 | Real-time agent assist | Task | M | L |
| AI-04 | Voice banking agent | Task | H | M |
| AI-05 | Complaints recognition & response | Workflow | M | H |
| AI-06 | Real-time fraud scoring | Monitoring | H | M |
| AI-07 | Fraud alert triage | Task | H | H |
| AI-08 | Fraud alert confirmation (cardholder) | Task | H | M |
| AI-09 | Scam-payment interception | Monitoring | H | H |
| AI-10 | AML transaction monitoring | Monitoring | H | H |
| AI-11 | AML investigation (Aegis-IR) | Multi-agent | H | H |
| AI-12 | SAR/STR narrative drafting | Task | H | H |
| AI-13 | KYC/KYB & CDD automation | Workflow | H | H |
| AI-14 | Sanctions & PEP screening | Monitoring | H | H |
| AI-15 | Regulatory reporting automation | Workflow | H | H |
| AI-16 | Regulatory change monitoring | Multi-agent | M | H |
| AI-17 | Disputes & chargeback automation | Workflow | M | M |
| AI-18 | Agentic BI (NL→governed answers) | Multi-agent | M | M |
| AI-19 | Loan origination agent | Multi-agent | H | H |
| AI-20 | Credit underwriting & memo drafting | Task | H | H |
| AI-21 | Income & document verification | Task | H | M |
| AI-22 | Alternative-data credit scoring | Monitoring | H | H |
| AI-23 | Early collections & hardship support | Workflow | M | H |
| AI-24 | RM copilot (portfolio review) | Task | H | M |
| AI-25 | Robo-advisory / auto-rebalance | Workflow | M | M |
| AI-26 | Ultra-personalised financial planning | Multi-agent | M | M |
| AI-27 | Agentic payment initiation (mandate) | Workflow | M | H |
| AI-28 | Treasury / liquidity monitoring | Monitoring | M | M |
| AI-29 | Reconciliation & break resolution | Workflow | M | M |
| AI-30 | Trade-document examination | Task | H | M |
| AI-31 | Developer augmentation | Workflow | H | L |
| AI-32 | Cybersecurity triage & containment | Monitoring | H | M |
| AI-33 | Enterprise knowledge search | Task | H | M |
| AI-34 | Document / vision extraction | Task | H | M |
| AI-35 | Research automation (filings) | Task | H | M |
| AI-36 | Pitchbook / presentation generation | Task | H | L |

> Full AI use-case detail (archetypes, data readiness, precedents, prioritisation) is in [08_ai_use_case_catalog.md](./08_ai_use_case_catalog.md).

## 4. Actor map

| Actor | Business | System | AI |
|---|---|---|---|
| **Customer** | BU-01–13 | SY-01, SY-02, SY-13, SY-14 | AI-01–05, AI-24–27 |
| **Analyst** | BU-15–16 | SY-08, SY-10 | AI-07, AI-11–12, AI-20 |
| **Compliance** | BU-17–18 | SY-08 | AI-10, AI-13–16 |
| **Business owner** | BU-11 | SY-03, SY-12 | AI-23 |
| **Bank (system)** | BU-14 | SY-02–06, SY-09 | AI-06, AI-09, AI-28 |

## 5. Coverage vs features

Every use case maps to features in [07_full_feature_catalog.md](./07_full_feature_catalog.md) and components in [06_feature_traceability.md](./06_feature_traceability.md). Use this catalog for **test scenarios** and **acceptance criteria** (see `../../05-quality-and-verification/acceptance-criteria-and-cases.md`).