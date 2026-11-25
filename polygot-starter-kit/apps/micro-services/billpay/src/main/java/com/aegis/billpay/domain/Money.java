package com.aegis.billpay.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

/** Immutable money value object with validation (non-negative, ISO-4217, scale 2). */
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
}