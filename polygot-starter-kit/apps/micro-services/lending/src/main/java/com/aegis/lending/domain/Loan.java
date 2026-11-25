package com.aegis.lending.domain;

import com.aegis.lending.error.InvalidLoanTransition;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Loan aggregate root. Owns its principal, status, and lifecycle.
 *
 * <p>All mutations go through methods that enforce the {@link LoanStatus} state machine. The entity is
 * not thread-safe on its own; the service serializes access with a per-loan lock. {@code version}
 * supports optimistic concurrency checks at the persistence boundary.
 */
public final class Loan {

  private final UUID id;
  private final String customerId;
  private final LoanType type;
  private final Money principal;
  private final Instant createdAt;

  private LoanStatus status;
  private long version;

  public Loan(UUID id, String customerId, LoanType type, Money principal) {
    this.id = Objects.requireNonNull(id, "id must not be null");
    this.customerId = requireText(customerId, "customerId");
    this.type = Objects.requireNonNull(type, "type must not be null");
    this.principal = Objects.requireNonNull(principal, "principal must not be null");
    if (principal.amount().signum() <= 0) {
      throw new IllegalArgumentException("loan principal must be positive");
    }
    this.status = LoanStatus.UNDERWRITING;
    this.createdAt = Instant.now();
  }

  public UUID id() {
    return id;
  }

  public String customerId() {
    return customerId;
  }

  public LoanType type() {
    return type;
  }

  public Money principal() {
    return principal;
  }

  public String currency() {
    return principal.currency();
  }

  public LoanStatus status() {
    return status;
  }

  public long version() {
    return version;
  }

  public Instant createdAt() {
    return createdAt;
  }

  public void approve() {
    transition(LoanStatus.APPROVED);
  }

  public void decline() {
    transition(LoanStatus.DECLINED);
  }

  public void disburse() {
    transition(LoanStatus.DISBURSED);
  }

  public void activate() {
    transition(LoanStatus.ACTIVE);
  }

  public void payOff() {
    transition(LoanStatus.PAID_OFF);
  }

  public void defaultLoan() {
    transition(LoanStatus.DEFAULTED);
  }

  private void transition(LoanStatus target) {
    if (!status.canTransitionTo(target)) {
      throw new InvalidLoanTransition(id, status, target);
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