# UML Modeling and System Use Cases

## Use case diagram (text)

```mermaid
flowchart LR
    Customer((Customer)) --> UC1[Onboard]
    Customer --> UC2[Pay/Transfer]
    Customer --> UC3[Freeze Card]
    Customer --> UC4[Ask Assistant]
    Customer --> UC5[Apply for Loan]
    Analyst((Analyst)) --> UC6[Investigate Alert]
    Analyst --> UC7[Draft SAR]
    Analyst --> UC8[Approve Case]
    Admin((Admin)) --> UC9[Manage Users]
    System((System)) --> UC10[Score Fraud]
    System --> UC11[Monitor AML]
```

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

## System boundaries

- **Customer-facing** use cases are real-time.
- **Analyst-facing** use cases are async, HITL-gated.
- **System** use cases are event-driven.