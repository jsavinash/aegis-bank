package com.aegis.treasury.domain;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Deterministic 13-week style cash forecast.
 *
 * <p>Pure function: a simple linear projection of the current balance over {@code weeks} periods
 * ({@code balance * weeks}). It never mutates the position and produces the same result for the same
 * inputs.
 */
public final class Forecast {

  private Forecast() {}

  public static BigDecimal project(CashPosition position, int weeks) {
    Objects.requireNonNull(position, "position must not be null");
    if (weeks < 0) {
      throw new IllegalArgumentException("weeks must be non-negative");
    }
    return position.balance().amount().multiply(BigDecimal.valueOf(weeks));
  }
}