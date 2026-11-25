package com.aegis.insights.web;

import com.aegis.insights.domain.Budget;
import com.aegis.insights.domain.Money;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Immutable snapshot of a budget for API responses; never exposes the mutable entity.
 *
 * <p>{@code remaining} is a signed {@link BigDecimal} (negative when over budget) because a {@link
 * Money} value object cannot represent a negative amount.
 */
public record BudgetView(
    UUID id,
    String customerId,
    String category,
    Money limit,
    Money spent,
    BigDecimal remaining,
    boolean overBudget,
    String currency,
    long version) {

  public static BudgetView from(Budget b) {
    return new BudgetView(
        b.id(),
        b.customerId(),
        b.category(),
        b.limit(),
        b.spent(),
        b.remaining(),
        b.isOverBudget(),
        b.currency(),
        b.version());
  }
}