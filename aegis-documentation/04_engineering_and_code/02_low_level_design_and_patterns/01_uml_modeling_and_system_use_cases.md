# UML Modeling and System Use Cases

> Full use-case catalog (business + system + AI): [`../../03_system_and_architecture/01_requirements_and_capacity_estimation/07_use_case_catalog_all.md`](../../03_system_and_architecture/01_requirements_and_capacity_estimation/07_use_case_catalog_all.md).

## Use case diagram (text)

`mermaid
flowchart LR
 Customer((Customer)) --> UC1[Onboard]
 Customer --> UC2[Pay/Transfer]
 Customer --> UC3[Freeze Card]
 Customer --> UC4[Ask Assistant]
 Customer --> UC5[Apply for Loan]
 Customer --> UC9[Dispute Transaction]
 Customer --> UC10[Manage Consent]
 Analyst((Analyst)) --> UC6[Investigate Alert]
 Analyst --> UC7[Draft SAR]
 Analyst --> UC8[Approve Case]
 Admin((Admin)) --> UC11[Manage Users]
 System((System)) --> UC12[Score Fraud]
 System --> UC13[Monitor AML]
`

## Key use cases

| ID | Use case | Actors | Outcome |
|---|---|---|---|
| UC-1 | Onboard | Customer, System | Account opened, KYC verified |
| UC-2 | Make payment | Customer, System | Payment settled |
| UC-3 | Freeze card | Customer | Card blocked |
| UC-4 | Ask assistant | Customer, AI | Answer or escalation |
| UC-5 | Apply for loan | Customer, System | Decision (auto or human) |
| UC-6 | Investigate alert | Analyst, AI | Case built |
| UC-7 | Draft SAR | Analyst, AI | Narrative ready for filing |
| UC-8 | Approve case | Analyst | Case approved/filed |
| UC-9 | Dispute transaction | Customer, Analyst | Dispute resolved |
| UC-10 | Manage consent | Customer | Consent granted/revoked |
| UC-11 | Manage users | Admin | Users/roles managed |
| UC-12 | Score fraud | System | <150ms decision |
| UC-13 | Monitor AML | System | Alert raised |

## Sequence (payment)

`mermaid
sequenceDiagram
 Customer->>BFF: pay(recipient, amount)
 BFF->>Payments: POST /payments
 Payments->>Fraud: score()
 Fraud-->>Payments: allow
 Payments->>Core: post (idempotent)
 Core-->>Payments: posted
 Payments-->>BFF: 201
 BFF-->>Customer: receipt
`

## Class diagram (core entities)

`mermaid
classDiagram
 Customer "1" --> "*" Account
 Account "1" --> "*" Transaction
 Customer "1" --> "*" Card
 Loan "1" --> "*" Repayment
`

## System boundaries

- **Customer-facing** use cases are real-time.
- **Analyst-facing** use cases are async, HITL-gated.
- **System** use cases are event-driven.
