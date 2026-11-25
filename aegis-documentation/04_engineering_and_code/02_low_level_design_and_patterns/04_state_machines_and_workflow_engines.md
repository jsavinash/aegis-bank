# State Machines and Workflow Engines

## State machines

### Case (AML investigation)
`
OPEN → INVESTIGATING → PENDING_REVIEW → FILED / CLOSED / DISMISSED
`

### Payment
`
INITIATED → SCORED → POSTED / DECLINED / ESCALATED
`

### KYC
`
DRAFT → SUBMITTED → VERIFYING → VERIFIED / EDD / REJECTED
`

### Loan application
`
DRAFT → SUBMITTED → UNDERWRITING → APPROVED / DECLINED / REFERRED
`

### Card
`
ACTIVE → FROZEN → CLOSED
`

### Consent
`
GRANTED → ACTIVE → EXPIRED / REVOKED
`

### Dispute
`
OPEN → INVESTIGATING → RESOLVED / CHARGEBACK
`

## Workflow engines

| Concern | Tool |
|---|---|
| **Agent orchestration** | LangGraph (typed state graphs) |
| **Durable long-running** | Temporal (HITL, retries, idempotency) |
| **Data pipelines** | Dagster |
| **Stream processing** | Flink |

## HITL interrupts

LangGraph `interrupt()` pauses a run for human approval; Temporal holds it durably across days. On resume, completed steps do **not** re-run (idempotent).

## Persistence & versioning

- **State** persisted (Postgres checkpointer / Temporal); **versioned** state machines with migration.
- **Compensations** (saga) for rollback; **idempotent** transitions.

## State invariants

- **Case:** cannot file without narrative + QA approval.
- **Payment:** cannot post without fraud score.
- **KYC:** cannot verify without sanctions check.
- **Loan:** cannot auto-approve without explainable decision.

## Testing

- **Model-based** tests (invalid transitions rejected); **replay** of Temporal histories.
