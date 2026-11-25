package com.aegis.security.domain;

import com.aegis.security.error.InvalidTransition;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * FraudAlert aggregate root: a scored suspicious-activity alert with its own lifecycle.
 *
 * <p>The {@code riskScore} is clamped to {@code [0, 1]} so callers cannot inject out-of-range
 * values. Amount must be strictly positive. The entity is not thread-safe on its own; the service
 * serializes access with a per-alert lock.
 */
public final class FraudAlert {

  /** High-value threshold: amounts strictly above this score {@code 0.9}, otherwise {@code 0.1}. */
  private static final double HIGH_VALUE_THRESHOLD = 10_000d;

  private final UUID id;
  private final String customerId;
  private final BigDecimal amount;
  private final double riskScore;

  private AlertStatus status;

  public FraudAlert(UUID id, String customerId, BigDecimal amount, double riskScore) {
    this.id = Objects.requireNonNull(id, "id must not be null");
    this.customerId = requireText(customerId, "customerId");
    this.amount = requirePositive(Objects.requireNonNull(amount, "amount must not be null"));
    this.riskScore = clamp(riskScore);
    this.status = AlertStatus.OPEN;
  }

  /**
   * Baseline risk scoring: a pure function of amount. Amounts strictly above {@code 10000} score
   * {@code 0.9}; everything else scores {@code 0.1}.
   */
  public static double riskScore(double amount) {
    return amount > HIGH_VALUE_THRESHOLD ? 0.9 : 0.1;
  }

  public UUID id() {
    return id;
  }

  public String customerId() {
    return customerId;
  }

  public BigDecimal amount() {
    return amount;
  }

  public double riskScore() {
    return riskScore;
  }

  public AlertStatus status() {
    return status;
  }

  /** Confirm this alert: {@code OPEN -> CONFIRMED}. */
  public void confirm() {
    transition(AlertStatus.CONFIRMED);
  }

  /** Dismiss this alert: {@code OPEN -> DISMISSED}. */
  public void dismiss() {
    transition(AlertStatus.DISMISSED);
  }

  private void transition(AlertStatus target) {
    if (!status.canTransitionTo(target)) {
      throw new InvalidTransition("alert", id, status.name(), target.name());
    }
    this.status = target;
  }

  private static double clamp(double value) {
    if (value < 0.0) {
      return 0.0;
    }
    return Math.min(value, 1.0);
  }

  private static BigDecimal requirePositive(BigDecimal amount) {
    if (amount.signum() <= 0) {
      throw new IllegalArgumentException("amount must be positive");
    }
    return amount;
  }

  private static String requireText(String value, String field) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(field + " must not be blank");
    }
    return value;
  }
}