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
| **FX** | Foreign exchange — currency conversion and multi-currency handling. |
| **Stablecoin** | Fiat-pegged crypto token used for payments/remittance. |
| **Tokenised deposit** | A bank deposit represented as a blockchain token. |
| **Escrow** | Funds held by a third party until conditions are met. |
| **Round-up** | Saving the spare change from everyday transactions. |
| **Pots / spaces** | Named savings buckets within an account. |
| **Salary sorting** | Automatic split of incoming salary across pots. |
| **Credit-building** | Tools that help establish or improve a credit score. |
| **Scam detection** | In-app warnings/interception of scam payments. |
| **Positive Pay** | ACH/check fraud filter matching issued items. |
| **Maker-checker** | Dual-approval control for high-value actions. |
| **Confirmation of payee** | Pre-payment check that the payee name matches. |
| **Standing order / Request-to-Pay (RTI)** | Recurring fixed payment / a request for payment. |
| **Chargeback** | Reversal of a card payment after a dispute. |
| **Agent banking** | Assisted banking via agents (offline/USSD). |
| **USSD / softPOS / eSIM** | Feature-phone menu / software point-of-sale / embedded SIM. |

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

## Feature terms

| Term | Definition |
|---|---|
| **Budget** | A spending plan by category. |
| **Cash-flow forecast** | Predicted inflows/outflows over time. |
| **Net-worth 360°** | Consolidated view of assets minus liabilities. |
| **Payroll** | Processing of employee salaries. |
| **Cash pooling** | Consolidating balances across accounts for treasury. |
| **ZBA / TBA** | Zero-Balance / Target-Balance Account — automated sweeps. |
| **Lockbox** | Bank-operated receipt processing for payments. |
| **FX hedging** | Instruments that reduce currency risk. |
| **Teen account** | Account for minors with parental oversight. |
| **Parental controls** | Limits/approvals a parent sets on a teen account. |
| **Super-app** | An app bundling many services (payments, lifestyle, retail). |
| **Cashback** | Reward returning a percentage of spend. |
| **Card-linked offers** | Discounts applied when paying with a linked card. |
| **Underwriting** | Assessing risk to approve credit/insurance. |
| **Claims** | Requests for an insurance payout. |
| **Embedded insurance** | Insurance offered at the point of sale. |
| **Alert fatigue** | Over-alerting that reduces response quality. |
| **Quiet hours** | Do-not-disturb windows for non-critical alerts. |
| **Statement** | Periodic record of account activity. |
| **Retention** | Keeping records for the required period. |
| **Shariah / Islamic banking** | Banking compliant with Islamic law (no riba/interest). |
| **VSLA / SACCO** | Village Savings & Loan Association / Savings & Credit Cooperative. |
| **Parametric insurance** | Pays out on a defined trigger (e.g., crop yield). |
| **Tokenisation** | Replacing sensitive data (e.g., card number) with a token. |
| **Wallet tokenisation** | Device-bound tokens replacing card data for wallets. |
| **Alternative credit scoring** | Scoring using non-traditional data (thin-file). |
| **Financial health score** | A metric summarising a customer's financial resilience. |
| **Nudge** | A timely prompt encouraging a financial action. |
| **Perk** | A loyalty/travel benefit. |

## Feature-domain coverage (37 sections)

| § | Domain | Key terms |
|---|---|---|
| 1 | Accounts & Deposits | core, ledger, product engine, pots, round-up, salary sorting |
| 2 | Payments & Transfers | P2P, ACH/SEPA/FedNow/RTP, SWIFT, ISO 20022, standing order, RTI, confirmation of payee |
| 3 | Cards | MCC, 3DS, PCI-DSS, tokenisation |
| 4 | Bill Pay | bill pay, autopay, RTI |
| 5 | Lending & Credit | BNPL, EWA, HELOC, FICO |
| 6 | Savings, Wealth & Investing | robo-advisor, APY/AER, ESG, tokenised deposit |
| 7 | Money Management & Insights | budget, cash-flow forecast, net-worth |
| 8 | Credit & Financial Health | FICO, credit-building |
| 9 | Security & Fraud | passkey, MFA, GNN, scam detection, Positive Pay, KYT |
| 10 | Customer Service & AI | agentic AI, RAG, MCP, HITL |
| 11 | Business & Commercial | SME, AP/AR, MTD, payroll |
| 12 | Treasury & Cash Mgmt | cash pooling, ZBA/TBA, lockbox, FX hedging |
| 13 | Private Banking (HNW) | HNW |
| 14 | Kids, Teens & Family | teen account, parental controls |
| 15 | Open Banking & Ecosystem | PSD2/PSD3, PISP/AISP, BaaS |
| 16 | Super-App & Lifestyle | super-app, eSIM, softPOS, USSD |
| 17 | Sustainability / Green | ESG |
| 18 | Digital Assets & Stablecoins | stablecoin, tokenised deposit, CBDC, KYT |
| 19 | Rewards & Loyalty | cashback, card-linked offers |
| 20 | Insurance & Protection | underwriting, claims, embedded insurance |
| 21 | Accessibility & Inclusion | WCAG 2.2 AA, EAA |
| 22 | Compliance & Regulatory | AML, KYC, SAR/STR, PEP, typology, FATF/FinCEN |
| 23 | Platform, Data & AI Infra | feature store, OTel, vector/graph store |
| 24 | Notifications & UX | push/SMS/email, alert fatigue |
| 25 | International / Cross-border | FX, SWIFT, ISO 20022 |
| 26 | Payments Acceptance (merchant) | MCC, chargeback |
| 27 | Specialty / Niche | Shariah, VSLA/SACCO, parametric insurance |
| 28 | Credit Cards — advanced | 3DS, tokenisation |
| 29 | Embedded Finance & Wallets | wallet tokenisation |
| 30 | Business Banking — depth | payroll, MTD |
| 31 | Financial Inclusion / Rural / Agri | alternative credit scoring, VSLA |
| 32 | Advanced AI & Agentic | agentic AI, MCP, multi-agent |
| 33 | Financial Wellness | financial health score, nudges |
| 34 | Notifications, Alerts & UX | alert fatigue, quiet hours |
| 35 | Statements & Documents | statement, retention |
| 36 | Rewards, Travel & Perks | cashback, perks |
| 37 | New / Emerging | CBDC, tokenised deposit |

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