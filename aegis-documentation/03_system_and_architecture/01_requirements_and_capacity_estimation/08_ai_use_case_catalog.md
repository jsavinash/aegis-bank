# 02 — AI Use-Case Catalog (Aegis Bank)

**Version:** 1.0 · **Date:** 24 Nov 2026 · **Companion to:** [06_ai_native_reference_architectures.md](../03_high_level_design_hld/06_ai_native_reference_architectures.md), [../../01-business-and-strategy/14_feature_catalog.md](../../01-business-and-strategy/14_feature_catalog.md)

A catalog of AI/agentic use cases for Aegis Bank, organized by **agent archetype** and **domain**, with value signals, data readiness, regulatory exposure, and 2026 precedents. Use it to sequence the AI roadmap (what to build first) and to map each use case to the tech stack in [../02_tech_choices_and_adrs/03_ai_first_tech_stack_recommendation.md](../02_tech_choices_and_adrs/03_ai_first_tech_stack_recommendation.md).

> **Evidence base (2026):** Gartner *Agentic AI Case Examples: Banking* (~80 examples across 19 banking use cases); Evident AI Use Case Tracker Q3 2026 (111 new use cases; agentic rose 15%→39% of new functional use cases; compliance largest function, risk fastest-growing); Deloitte *Financial Services AI Dossier 2026*; Blits.ai banking use-case tracker (77 use cases, 325 public deployments); NTT Data *2026 Global AI Report — Banking playbook*; JPMorganChase LLM Suite (250k employees, ~half daily); HKMA *Discussion Paper on Agentic AI Governance* (task/workflow/monitoring archetypes); McKinsey *Agentic AI in banking: the agentic coworker*.

---

## 1. Framework: the four agent archetypes (HKMA / McKinsey)

The HKMA classifies banking agentic AI into three archetypes, plus multi-agent orchestration (McKinsey's "agentic coworker" adds the autonomy spectrum). Every use case below is tagged with its archetype.

| Archetype | Definition | Autonomy | Example |
|---|---|---|---|
| **Task agent** | Completes bounded work when triggered | Low–medium | Assemble a credit paper; draft a SAR narrative; repair a payment instruction |
| **Workflow agent** | Runs a recurring process end-to-end within rules | Medium | KYC onboarding; reconciliation; collections; loan origination |
| **Monitoring agent** | Continuously detects, escalates, contains | Medium–high | Fraud scoring; AML transaction monitoring; cybersecurity triage |
| **Multi-agent system** | Orchestrator directs specialised agents | High (supervised) | AML investigation (Aegis-IR); credit underwriting pod; wealth advice pod |

**Autonomy spectrum (controls scale with it):** Adviser (recommends, human acts) → Copilot (drafts, human approves) → Supervised executor (acts within limits) → Autonomous (acts, human notified). *Aegis defaults to Adviser/Copilot for customer-facing and high-consequence actions, escalating to Supervised executor only where mandated and monitored.*

---

## 2. Use cases by domain

Legend: **Archetype** (T=Task, W=Workflow, M=Monitoring, MA=Multi-agent) · **Value** (H/M/L) · **Data readiness** (H/M/L) · **Reg. exposure** (H/M/L) · **Precedence** (✅ at scale / 🟡 emerging).

### 2.1 Customer service & engagement

| Use case | Archetype | Value | Data | Reg | Precedent |
|---|---|---|---|---|---|
| First-line contact centre agent (voice/chat/WhatsApp, multilingual) | W | H | H | M | ✅ 45 orgs, 66% median containment (Blits) |
| Account & card servicing agent (balance, statements, freeze, PIN) | T | H | H | M | ✅ 83% median containment |
| Real-time agent assist (transcribe + next-best-action) | T | M | H | L | ✅ 26% median productivity gain |
| Voice banking agent (conversational money transfer/bill pay) | T | H | M | M | ✅ up to 60% cycle-time reduction |
| Complaints recognition, investigation & response | W | M | M | H | ✅ ~7 min saved/task (YBS) |
| Digital human guide (product Q&A, escalates complex) | T | L | M | M | ✅ 94.5% accuracy (vendor) |
| Personalised financial guidance / coaching | MA | M | M | M | 🟡 |

### 2.2 Fraud, financial crime & compliance

| Use case | Archetype | Value | Data | Reg | Precedent |
|---|---|---|---|---|---|
| Real-time fraud scoring (card/txn) | M | H | H | M | ✅ 40–60% false-positive cut |
| Fraud alert triage (analyst first pass) | T | H | M | H | ✅ ≥90% accuracy (Lloyds) |
| Fraud alert confirmation with cardholder | T | H | M | M | ✅ 76% fraud-loss reduction (CommBank) |
| Scam-payment interception | M | H | M | H | 🟡 |
| AML transaction monitoring (score + triage) | M | H | M | H | ✅ (TD Bank agents) |
| AML investigation agent (Aegis-IR) | MA | H | M | H | ✅ (Flagright/Marble/Refute) |
| SAR/STR narrative drafting copilot | T | H | M | H | ✅ 57% automation (Nexo) |
| KYC/KYB & customer due diligence automation | W | H | H | H | ✅ |
| Sanctions & PEP screening (continuous) | M | H | M | H | ✅ |
| Regulatory reporting automation | W | H | M | H | ✅ |
| Regulatory change monitoring & control mapping | MA | M | M | H | 🟡 (Lexicon pattern) |
| Disputes & chargeback workflow automation | W | M | H | M | ✅ |
| Agentic BI (natural-language governed answers) | MA | M | H | M | 🟡 |

### 2.3 Lending & credit

| Use case | Archetype | Value | Data | Reg | Precedent |
|---|---|---|---|---|---|
| Loan origination agent (collect → verify → recommend) | MA | H | M | H | 🟡 (end-to-end pilots: CommBank) |
| Credit underwriting & memo drafting | T | H | M | H | ✅ 96% cycle-time cut (KFH, vendor) |
| Income & document verification (mortgage) | T | H | H | M | ✅ 78.5% handling-time reduction |
| Alternative-data credit scoring (thin-file) | M | H | M | H | ✅ 70% automation |
| Early collections & hardship support (voice) | W | M | M | H | ✅ 63% cost reduction |
| Creditworthiness checks (SME) | T | M | M | H | ✅ 15 min → <1 min |

### 2.4 Wealth & private banking

| Use case | Archetype | Value | Data | Reg | Precedent |
|---|---|---|---|---|---|
| Relationship-manager copilot (portfolio review, next-best-action) | T | H | M | M | ✅ (JPMorgan Connect Coach) |
| Robo-advisory / auto-rebalance | W | M | H | M | ✅ |
| Ultra-personalised financial planning (multi-agent) | MA | M | M | M | 🟡 |
| Tax-loss harvesting & portfolio analytics | W | M | H | M | ✅ |
| Market-scout / scenario simulation | MA | M | M | M | 🟡 |

### 2.5 Payments, treasury & operations

| Use case | Archetype | Value | Data | Reg | Precedent |
|---|---|---|---|---|---|
| Agentic payment initiation (within mandate) | W | M | M | H | 🟡 (15 deployments) |
| Treasury / liquidity monitoring & escalation | M | M | H | M | ✅ |
| Reconciliation & break resolution | W | M | H | M | ✅ (Broadridge) |
| Payment-instruction repair (exception handling) | T | M | H | M | ✅ |
| Trade-document examination (letters of credit) | T | H | M | M | ✅ 70% cycle-time reduction |
| Cash-flow forecasting & scenario modelling | MA | M | M | M | 🟡 |

### 2.6 Technology, cyber & internal productivity

| Use case | Archetype | Value | Data | Reg | Precedent |
|---|---|---|---|---|---|
| Developer augmentation (coding agents, test generation) | W | H | H | L | ✅ |
| Cybersecurity alert triage & containment | M | H | M | M | 🟡 (fastest riser, Evident Q3) |
| Enterprise knowledge search (governed, cited) | T | H | H | M | ✅ ≥96% adoption |
| Internal audit copilot | T | M | M | M | 🟡 |
| Document processing / vision extraction (statements, IDs) | T | H | H | M | ✅ (DBS 65k forms/mo, –70% effort) |

### 2.7 Investment banking & markets (adjacent, for scale ambition)

| Use case | Archetype | Value | Data | Reg | Precedent |
|---|---|---|---|---|---|
| Research automation (summarise filings, valuation models) | T | H | H | M | ✅ JPMorgan LLM Suite (40% of tasks) |
| Pitchbook / presentation generation | T | H | M | L | ✅ (RBC, BofA) |
| Trading-operations exception handling | M | M | M | M | ✅ (Citi) |
| Enterprise risk assessment | MA | M | M | H | 🟡 (Westpac) |

---

## 3. Prioritisation (value density, not novelty)

Score every candidate on **annual value × data readiness × (inverse) regulatory exposure**. The 2026 lesson (Stratenity, Evident): *leaders pick 2–3 deep wins, not a dozen pilots.*

| Rank | Use case | Why first | Model / stack |
|---|---|---|---|
| 1 | **Fraud detection & alert triage** | Highest measurable ROI (card losses 6–8 bps; 40–60% FP cut); moderate reg exposure; builds org muscle | GNN/GBM hot path + LangGraph warm triage (`aegis/risk`, `aegis/agents`) |
| 2 | **Customer servicing (contact centre + account/card agent)** | 25–40% contact deflection; huge volume; well-scoped | RAG + agentic assistant + MCP tools |
| 3 | **AML monitoring + investigation (Aegis-IR)** | Legacy FP >90%; agentic investigation is proven (4 funded competitors) | Multi-agent LangGraph + GraphRAG + Temporal (`aegis/`) |
| 4 | **KYC/onboarding automation** | High volume, well-defined; fastest payback (60–120 days) | Workflow agent + Docling + guardrails |
| 5 | **Credit underwriting copilot** | High value but heavy fair-lending scrutiny → *after* fraud/servicing prove governance | ML + LLM copilot, human-in-loop |
| 6 | **Wealth RM copilot** | Revenue uplift; lower regulatory exposure than lending | RAG over portfolio + policy |

**Sequencing rule:** fraud + servicing first (lower fair-lending exposure, builds operating muscle) → AML as an *augmentation layer* over existing monitoring (regulatory continuity, not rip-and-replace) → underwriting only once the governance operating model exists.

---

## 4. Where bank AI programs stall (and how Aegis avoids it)

| Anti-pattern (2026 evidence) | Mitigation in Aegis |
|---|---|
| Spreading thin across 10 pilots | Focus on 2–3 deep wins (fraud, servicing, AML) with production commitment |
| Leading with underwriting (most regulated) | Sequence after governance model exists |
| Measuring model accuracy, not business metric | Track fraud-loss bps, contact-deflection %, AML alert-to-filing rate |
| Vendor model assumes clean features the core can't supply | Event-driven core + Feast feature store for online/offline parity |
| Black-box agents in regulated decisions | Grounding-required generation, audit trails, HITL sign-off, explainability |

---

## 5. Governance for agentic AI (HKMA ART + McKinsey)

Every use case above must satisfy these controls, scaled to autonomy and sensitivity:

1. **Accountability** — a named human owner per agent; ultimate responsibility cannot be outsourced to the model.
2. **Risk-tiered controls** — adviser agents (public data) need lighter controls than executor agents writing to payments/AML; classify each agent by *what it may do and what it may touch*, pull controls from a standard library.
3. **Evidence before action** — tests run, policies checked, outputs validated against authoritative sources before any side effect.
4. **Containment** — rollback paths, blast-radius limits, idempotency keys.
5. **Audit-ready traceability** — reconstruct who decided what, on what basis, with what authority (feeds `aegis/db` audit events).
6. **Fail-closed guardrails** — NeMo + Llama Guard; tool-result screening (OWASP MCP Top-10).

---

## 6. Mapping to the codebase & stack

| Use case | Code | Stack layer |
|---|---|---|
| Fraud triage / AML investigation | `aegis/risk/`, `aegis/agents/`, `aegis/graphs/`, `aegis/workflows/` | AI/ML platform (LangGraph + Temporal + MCP) |
| RAG / KYC / policy Q&A | `aegis/rag/` | LlamaIndex + Qdrant + Neo4j |
| Guardrails on every agent | `aegis/guardrails/` | NeMo + Llama Guard |
| Eval of every use case | `aegis/eval/` | Ragas + Braintrust + Langfuse |
| Document extraction | `aegis/compliance/` | Docling |
| Regulatory change | `aegis/compliance/` | GraphRAG + Graphiti |

See [../../04_engineering_and_code/01_codebase_organization_and_standards/05_module_to_stack_mapping.md](../../04_engineering_and_code/01_codebase_organization_and_standards/05_module_to_stack_mapping.md) for the full module → stack map.

---

## 7. Summary: the AI-first bet

Aegis does not chase novelty; it picks a **narrow set of high-value-density use cases** (fraud, servicing, AML, KYC, underwriting, wealth) and builds each to production with **eval-gated CI, human accountability, and fail-closed guardrails** — the operating model that separates 2026's AI leaders from its pilot factories.

---

*End of AI use-case catalog.*