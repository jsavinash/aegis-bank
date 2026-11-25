package com.aegis.insights.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * Budget aggregate root: a per-category spending limit and its running spend.
 *
 * <p>Spends are allowed to exceed the limit (overspend is a valid, reported state); {@link
 * #isOverBudget()} and {@link #remaining()} surface it. The entity is not thread-safe on its own; the
 * service serializes access with a per-budget lock. {@code version} supports optimistic concurrency
 * checks at the persistence boundary.
 */
public final class Budget {

  private final UUID id;
  private final String customerId;
  private final String category;
  private final String currency;
  private final Money limit;

  private Money spent;
  private long version;

  public Budget(UUID id, String customerId, String category, Money limit) {
    this.id = Objects.requireNonNull(id, "id must not be null");
    this.customerId = requireText(customerId, "customerId");
    this.category = requireText(category, "category");
    this.limit = Objects.requireNonNull(limit, "limit must not be null");
    if (this.limit.amount().signum() <= 0) {
      throw new IllegalArgumentException("limit must be positive");
    }
    this.currency = this.limit.currency();
    this.spent = Money.zero(this.currency);
  }

  public UUID id() {
    return id;
  }

  public String customerId() {
    return customerId;
  }

  public String category() {
    return category;
  }

  public String currency() {
    return currency;
  }

  public Money limit() {
    return limit;
  }

  public Money spent() {
    return spent;
  }

  public long version() {
    return version;
  }

  /** Accumulate spend against this budget. Overspend is permitted and surfaced via {@link #isOverBudget()}. */
  public void spend(Money amount) {
    requirePositive(amount);
    requireSameCurrency(amount);
    this.spent = this.spent.plus(amount);
    this.version++;
  }

  /** True when accumulated spend strictly exceeds the limit. */
  public boolean isOverBudget() {
    return spent.isGreaterThan(limit);
  }

  /** Signed headroom: positive when under budget, negative when over. */
  public BigDecimal remaining() {
    return limit.amount().subtract(spent.amount());
  }

  private void requirePositive(Money amount) {
    if (amount.amount().signum() <= 0) {
      throw new IllegalArgumentException("amount must be positive");
    }
  }

  private void requireSameCurrency(Money amount) {
    if (!currency.equals(amount.currency())) {
      throw new IllegalArgumentException(
          "currency mismatch: budget=" + currency + " amount=" + amount.currency());
    }
  }

  private static String requireText(String value, String field) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(field + " must not be blank");
    }
    return value;
  }
}