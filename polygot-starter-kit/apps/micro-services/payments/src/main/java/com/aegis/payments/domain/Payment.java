package com.aegis.payments.domain;

import com.aegis.payments.error.InvalidPaymentTransition;
import com.aegis.payments.error.PaymentError;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Payment aggregate root. Owns its status and applies the state machine. Amount is validated by
 * {@link Money}. The service serializes mutations per payment and enforces idempotency.
 */
public final class Payment {

  private final UUID id;
  private final String idempotencyKey;
  private final String fromAccount;
  private final String toAccount;
  private final Money amount;
  private final PaymentType type;
  private final Instant createdAt;

  private PaymentStatus status;
  private long version;

  public Payment(
      UUID id,
      String idempotencyKey,
      String fromAccount,
      String toAccount,
      Money amount,
      PaymentType type) {
    this.id = Objects.requireNonNull(id, "id must not be null");
    this.idempotencyKey = requireText(idempotencyKey, "idempotencyKey");
    this.fromAccount = requireText(fromAccount, "fromAccount");
    this.toAccount = requireText(toAccount, "toAccount");
    this.amount = Objects.requireNonNull(amount, "amount must not be null");
    if (amount.amount().signum() <= 0) {
      throw new IllegalArgumentException("payment amount must be positive");
    }
    this.type = Objects.requireNonNull(type, "type must not be null");
    this.createdAt = Instant.now();
    this.status = PaymentStatus.PENDING;
  }

  public UUID id() {
    return id;
  }

  public String idempotencyKey() {
    return idempotencyKey;
  }

  public String fromAccount() {
    return fromAccount;
  }

  public String toAccount() {
    return toAccount;
  }

  public Money amount() {
    return amount;
  }

  public PaymentType type() {
    return type;
  }

  public PaymentStatus status() {
    return status;
  }

  public long version() {
    return version;
  }

  public Instant createdAt() {
    return createdAt;
  }

  public void markProcessing() {
    transition(PaymentStatus.PROCESSING);
  }

  public void complete() {
    transition(PaymentStatus.COMPLETED);
  }

  public void fail() {
    transition(PaymentStatus.FAILED);
  }

  public void cancel() {
    transition(PaymentStatus.CANCELLED);
  }

  public void recall() {
    transition(PaymentStatus.RECALLED);
  }

  private void transition(PaymentStatus target) {
    if (!status.canTransitionTo(target)) {
      throw new InvalidPaymentTransition(id, status, target);
    }
    this.status = target;
    this.version++;
  }

  private static String requireText(String value, String field) {
    if (value == null || value.isBlank()) {
      throw new PaymentError("invalid_field", field + " must not be blank");
    }
    return value;
  }
}