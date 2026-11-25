package com.aegis.insights.domain;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Immutable record of a single spend, used for categorisation and aggregation.
 *
 * <p>Validated at construction: {@code accountId}/{@code category}/{@code merchant} must be non-blank,
 * {@code amount} must be positive, and {@code occurredAt} must be present.
 */
public record Transaction(
    UUID id,
    String accountId,
    String category,
    Money amount,
    String merchant,
    Instant occurredAt) {

  public Transaction {
    Objects.requireNonNull(id, "id must not be null");
    requireText(accountId, "accountId");
    requireText(category, "category");
    Objects.requireNonNull(amount, "amount must not be null");
    if (amount.amount().signum() <= 0) {
      throw new IllegalArgumentException("amount must be positive");
    }
    requireText(merchant, "merchant");
    Objects.requireNonNull(occurredAt, "occurredAt must not be null");
  }

  private static String requireText(String value, String field) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(field + " must not be blank");
    }
    return value;
  }
}