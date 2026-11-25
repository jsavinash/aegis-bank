package com.aegis.business.domain;

import com.aegis.business.error.InvalidInvoiceTransition;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Invoice entity: owns its amount, status, and due date.
 *
 * <p>All mutations go through methods that enforce the state machine. The entity is not thread-safe
 * on its own; the service serializes access with a per-invoice lock. {@code version} supports
 * optimistic concurrency checks at the persistence boundary.
 */
public final class Invoice {

  private final UUID id;
  private final UUID accountId;
  private final Money amount;
  private final LocalDate dueDate;

  private InvoiceStatus status;
  private long version;

  public Invoice(UUID id, UUID accountId, Money amount, LocalDate dueDate) {
    this.id = Objects.requireNonNull(id, "id must not be null");
    this.accountId = Objects.requireNonNull(accountId, "accountId must not be null");
    this.amount = Objects.requireNonNull(amount, "amount must not be null");
    this.dueDate = Objects.requireNonNull(dueDate, "dueDate must not be null");
    this.status = InvoiceStatus.OPEN;
  }

  public UUID id() {
    return id;
  }

  public UUID accountId() {
    return accountId;
  }

  public Money amount() {
    return amount;
  }

  public InvoiceStatus status() {
    return status;
  }

  public LocalDate dueDate() {
    return dueDate;
  }

  public long version() {
    return version;
  }

  public void markPaid() {
    transition(InvoiceStatus.PAID);
  }

  public void markOverdue() {
    transition(InvoiceStatus.OVERDUE);
  }

  public void voidInvoice() {
    transition(InvoiceStatus.VOID);
  }

  private void transition(InvoiceStatus target) {
    if (!status.canTransitionTo(target)) {
      throw new InvalidInvoiceTransition(id, status, target);
    }
    this.status = target;
    this.version++;
  }
}