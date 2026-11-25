package com.aegis.wealth.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

/**
 * Immutable money value object with validation.
 *
 * <p>Mirrors the reference implementation's {@code Money}: amounts are non-negative and currency is a
 * 3-letter ISO-4217 code. Arithmetic returns new instances; it never mutates.
 */
public record Money(BigDecimal amount, String currency) {

  private static final int SCALE = 2;

  public Money {
    Objects.requireNonNull(amount, "amount must not be null");
    Objects.requireNonNull(currency, "currency must not be null");
    if (amount.signum() < 0) {
      throw new IllegalArgumentException("amount must be non-negative");
    }
    String normalized = currency.trim().toUpperCase();
    try {
      Currency.getInstance(normalized);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("currency must be a 3-letter ISO-4217 code: " + currency, e);
    }
    amount = amount.setScale(SCALE, RoundingMode.HALF_UP);
    currency = normalized;
  }

  public static Money of(BigDecimal amount, String currency) {
    return new Money(amount, currency);
  }

  public static Money zero(String currency) {
    return new Money(BigDecimal.ZERO, currency);
  }

  public Money plus(Money other) {
    requireSameCurrency(other);
    return new Money(this.amount.add(other.amount), this.currency);
  }

  public Money minus(Money other) {
    requireSameCurrency(other);
    return new Money(this.amount.subtract(other.amount), this.currency);
  }

  public boolean isGreaterThan(Money other) {
    requireSameCurrency(other);
    return this.amount.compareTo(other.amount) > 0;
  }

  public boolean isLessThan(Money other) {
    requireSameCurrency(other);
    return this.amount.compareTo(other.amount) < 0;
  }

  public boolean isGreaterThanOrEqualTo(Money other) {
    requireSameCurrency(other);
    return this.amount.compareTo(other.amount) >= 0;
  }

  private void requireSameCurrency(Money other) {
    if (!this.currency.equals(other.currency)) {
      throw new IllegalArgumentException(
          "currency mismatch: " + this.currency + " vs " + other.currency);
    }
  }
}