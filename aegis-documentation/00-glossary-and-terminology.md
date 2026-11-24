# 00 — Glossary and Terminology

Single source of truth for terms, acronyms, and definitions used across this documentation set.

## Business & domain

| Term | Definition |
|---|---|
| **Core banking** | The system of record: real-time ledger, product engine, general ledger, customer information file (CIF). |
| **CIF** | Customer Information File — the master record of a customer's identity, relationships, and risk. |
| **Ledger** | Double-entry record of all account balances and postings. |
| **Product engine** | Configuration layer that defines accounts, loans, deposits, fees, and pricing without code changes. |
| **KYC / KYB / CDD** | Know Your Customer / Business / Customer Due Diligence — identity and risk verification. |
| **EDD** | Enhanced Due Diligence — deeper checks for high-risk customers. |
| **SAR / STR** | Suspicious Activity / Transaction Report — regulatory filing for suspected money laundering. |
| **AML** | Anti-Money Laundering — laws, controls, and monitoring to detect and prevent laundering. |
| **PEP** | Politically Exposed Person — a senior public official, higher corruption risk. |
| **Typology** | A known money-laundering pattern (structuring, mule network, trade-based, layering). |
| **Structuring** | Splitting large deposits into smaller amounts to evade reporting thresholds (e.g., <$10k). |
| **Mule network** | Accounts used to launder funds by rapidly moving money between entities. |
| **BNPL** | Buy Now Pay Later — short-term, often interest-free credit at checkout. |
| **EWA** | Earned Wage Access — early access to earned salary. |
| **Robo-advisor** | Automated, algorithm-driven investment management. |
| **Open banking** | Regulated sharing of account data and payment initiation via APIs (PSD2/PSD3). |
| **PSD2 / PSD3** | EU Payment Services Directives governing open banking and payment security. |
| **PISP / AISP** | Payment Initiation / Account Information Service Provider (open-banking roles). |
| **BaaS** | Banking-as-a-Service — embedding banking via APIs. |

## Technical

| Term | Definition |
|---|---|
| **Agentic AI** | AI that plans and executes multi-step tasks with varying autonomy (task / workflow / monitoring / multi-agent). |
| **RAG** | Retrieval-Augmented Generation — grounding LLM answers in retrieved documents. |
| **GraphRAG** | RAG over a knowledge graph; strong for multi-hop/temporal questions. |
| **MCP** | Model Context Protocol — standard for connecting agents to tools/data. |
| **HITL** | Human-in-the-loop — mandatory human sign-off at high-consequence steps. |
| **Guardrails** | Programmable safety rails (input/output/tool-call) that block unsafe agent behaviour. |
| **Two-pass structured output** | Pass 1 tools-on (gather), pass 2 schema-on (emit) — avoids tool-suppression. |
| **GNN** | Graph Neural Network — model over relationship graphs (fraud rings, mule networks). |
| **Feature store** | System providing consistent ML features for training and serving (online/offline parity). |
| **Durable execution** | Workflow execution that survives crashes and waits (Temporal). |
| **Idempotency key** | A key ensuring an operation runs once even if retried. |
| **OTel** | OpenTelemetry — the observability standard (traces/metrics/logs). |
| **WCAG 2.2 AA** | Web Content Accessibility Guidelines — legal accessibility standard (EU EAA). |
| **PSD2 SCA** | Strong Customer Authentication under PSD2. |
| **Zero-day** | A previously unknown vulnerability. |
| **Blast radius** | The scope of impact if an action fails. |

## Abbreviations

| Short | Meaning |
|---|---|
| **API** | Application Programming Interface |
| **BFF** | Backend for Frontend |
| **CI/CD** | Continuous Integration / Continuous Delivery |
| **DR** | Disaster Recovery |
| **HLD / LLD** | High-Level / Low-Level Design |
| **NFR** | Non-Functional Requirement |
| **RTO / RPO** | Recovery Time / Point Objective |
| **SLO / SLA** | Service Level Objective / Agreement |
| **WAF** | Web Application Firewall |
| **IaC** | Infrastructure as Code |