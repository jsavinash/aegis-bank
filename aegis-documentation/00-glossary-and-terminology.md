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
| **Passkey / FIDO2** | Cryptographic device-bound passwordless authentication (WebAuthn/FIDO2); with risk-adaptive MFA as step-up for sensitive actions. |
| **MFA** | Multi-factor authentication — layered authentication with risk-adaptive / step-up escalation for high-risk actions. |
| **P2P** | Peer-to-peer payments between customers (Zelle-style). |
| **ACH / SEPA / FedNow / RTP** | Automated Clearing House / Single Euro Payments Area / FedNow / Real-Time Payments — domestic and cross-border payment rails. |
| **MCC** | Merchant Category Code — merchant classification used for risk controls and routing. |
| **SME** | Small / medium-sized enterprise — business-banking segment. |
| **HNW** | High-net-worth — private banking / wealth segment. |
| **FATF / FinCEN** | Financial Action Task Force / Financial Crimes Enforcement Network — key AML regulatory bodies. |
| **GDPR / CCPA** | General Data Protection Regulation / California Consumer Privacy Act — privacy frameworks. |
| **EAA** | European Accessibility Act — legal accessibility standard built on WCAG 2.2 AA. |
| **PCI-DSS / SOC 2 / ISO 27001** | Payment Card Industry Data Security Standard / SOC 2 Type II / ISO 27001 — security and trust standards. |
| **KYT** | Know Your Transaction — verification of crypto / stablecoin transactions. |
| **CBDC** | Central Bank Digital Currency — state-issued digital currency wallet. |
| **ESG** | Environmental, Social & Governance — sustainability / impact-investing product domain. |
| **CD** | Certificate of Deposit — fixed-term deposit product. |
| **HELOC** | Home Equity Line of Credit — secured credit product. |
| **AP / AR** | Accounts Payable / Accounts Receivable — B2B financial flows. |
| **APY / AER** | Annual Percentage Yield / Annual Equivalent Rate — savings yield measures. |
| **FICO** | Fair Isaac Credit Score — consumer credit score. |
| **MTD** | Making Tax Digital — tax-compliance reporting. |
| **ISO 20022** | ISO 20022 — international financial messaging standard (transaction events). |
| **SWIFT** | Society for Worldwide Interbank Financial Telecommunication — cross-border payment messaging. |
| **DSAR** | Data Subject Access Request — privacy right to access/export/delete data. |

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
| **LLM** | Large Language Model — foundation model for language understanding / generation (e.g., Claude, GPT, Gemini). |
| **BM25** | Best-matching 25 — sparse keyword retrieval ranking. |
| **HNSW** | Hierarchical Navigable Small World — approximate nearest-neighbor vector index. |
| **pgvector** | PostgreSQL extension for vector embeddings and similarity search. |
| **RBAC** | Role-Based Access Control — permission model scoped to roles. |
| **JWT** | JSON Web Token — signed token for authentication / authorization. |
| **OWASP** | Open Worldwide Application Security Project — source of MCP Top-10 security guidance. |
| **SIEM** | Security Information & Event Management. |
| **mTLS** | Mutual TLS — two-way TLS authentication. |
| **ACID** | Atomicity / Consistency / Isolation / Durability — database transaction guarantees. |
| **LoRA / QLoRA** | Low-Rank Adaptation / Quantized LoRA — parameter-efficient model fine-tuning. |
| **3DS** | 3-D Secure — cardholder authentication protocol for online payments. |
| **TPS** | Transactions Per Second — transaction throughput measure. |
| **RUM** | Real User Monitoring — front-end performance and engagement data. |
| **APM** | Application Performance Monitoring. |
| **SAST / DAST** | Static / Dynamic Application Security Testing. |
| **HSM** | Hardware Security Module — key storage and cryptographic operations. |
| **CIAM** | Customer Identity & Access Management. |
| **PII** | Personally Identifiable Information. |
| **Agent archetype** | Task / Workflow / Monitoring / Multi-agent classification of AI agent scope. |
| **Autonomy spectrum** | Adviser → Copilot → Supervised executor → Autonomous (human accountability scales with autonomy). |

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
| **ACH** | Automated Clearing House |
| **AP / AR** | Accounts Payable / Accounts Receivable |
| **APY / AER** | Annual Percentage Yield / Annual Equivalent Rate |
| **CD** | Certificate of Deposit |
| **CIAM** | Customer Identity & Access Management |
| **CBDC** | Central Bank Digital Currency |
| **DSAR** | Data Subject Access Request |
| **EAA** | European Accessibility Act |
| **ESG** | Environmental, Social & Governance |
| **FATF** | Financial Action Task Force |
| **FinCEN** | Financial Crimes Enforcement Network |
| **FICO** | Fair Isaac Credit Score |
| **GDPR** | General Data Protection Regulation |
| **HELOC** | Home Equity Line of Credit |
| **HNW** | High Net Worth |
| **HSM** | Hardware Security Module |
| **ISO 20022** | ISO 20022 financial messaging standard |
| **ISO 27001** | International Organization for Standardization 27001 |
| **JWT** | JSON Web Token |
| **KYT** | Know Your Transaction |
| **MCC** | Merchant Category Code |
| **MFA** | Multi-Factor Authentication |
| **MTD** | Making Tax Digital |
| **P2P** | Peer-to-Peer |
| **PCI-DSS** | Payment Card Industry Data Security Standard |
| **RBAC** | Role-Based Access Control |
| **RUM** | Real User Monitoring |
| **SAST** | Static Application Security Testing |
| **DAST** | Dynamic Application Security Testing |
| **SEPA** | Single Euro Payments Area |
| **SOC 2** | Service Organization Control 2 |
| **SWIFT** | Society for Worldwide Interbank Financial Telecommunication |
| **TPS** | Transactions Per Second |
| **3DS** | 3-D Secure |