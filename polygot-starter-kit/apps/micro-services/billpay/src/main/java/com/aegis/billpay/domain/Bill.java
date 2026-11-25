package com.aegis.billpay.domain;

import com.aegis.billpay.error.InvalidBillTransition;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Bill aggregate root. Owns payee, amount, due date, and lifecycle status. State changes go through
 * the {@link BillStatus} state machine.
 */
public final class Bill {

  private final UUID id;
  private final String customerId;
  private final String payee;
  private final Money amount;
  private final LocalDate dueDate;

  private BillStatus status;
  private long version;

  public Bill(UUID id, String customerId, String payee, Money amount, LocalDate dueDate) {
    this.id = Objects.requireNonNull(id, "id must not be null");
    this.customerId = requireText(customerId, "customerId");
    this.payee = requireText(payee, "payee");
    this.amount = Objects.requireNonNull(amount, "amount must not be null");
    if (amount.amount().signum() <= 0) {
      throw new IllegalArgumentException("bill amount must be positive");
    }
    this.dueDate = Objects.requireNonNull(dueDate, "dueDate must not be null");
    this.status = BillStatus.SCHEDULED;
  }

  public UUID id() {
    return id;
  }

  public String customerId() {
    return customerId;
  }

  public String payee() {
    return payee;
  }

  public Money amount() {
    return amount;
  }

  public LocalDate dueDate() {
    return dueDate;
  }

  public BillStatus status() {
    return status;
  }

  public long version() {
    return version;
  }

  public void enableAutopay() {
    transition(BillStatus.AUTOPIAY);
  }

  public void pay() {
    transition(BillStatus.PAID);
  }

  public void cancel() {
    transition(BillStatus.CANCELLED);
  }

  /** Whether the bill is due on or before {@code asOf} and still payable. */
  public boolean isOverdue(LocalDate asOf) {
    return status != BillStatus.PAID && status != BillStatus.CANCELLED && dueDate.isBefore(asOf);
  }

  private void transition(BillStatus target) {
    if (!status.canTransitionTo(target)) {
      throw new InvalidBillTransition(id, status, target);
    }
    this.status = target;
    this.version++;
  }

  private static String requireText(String value, String field) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(field + " must not be blank");
    }
    return value;
  }
}