package com.aegis.accounts.domain;

import java.time.Instant;
import java.util.UUID;

/**
 * A single double-entry ledger posting. Every balance change on an account produces one posting; the
 * counter-posting lives in the system/clearing account, keeping the ledger balanced.
 *
 * <p>{@code direction} is {@code DEBIT} (money out) or {@code CREDIT} (money in) relative to the
 * account. The {@code idempotencyKey} ties the posting back to the originating command.
 */
public record LedgerEntry(
    UUID id,
    UUID accountId,
    Money amount,
    Direction direction,
    String idempotencyKey,
    Instant occurredAt) {

  public LedgerEntry {
    java.util.Objects.requireNonNull(accountId, "accountId must not be null");
    java.util.Objects.requireNonNull(amount, "amount must not be null");
    java.util.Objects.requireNonNull(direction, "direction must not be null");
    if (amount.amount().signum() <= 0) {
      throw new IllegalArgumentException("ledger amount must be positive");
    }
  }

  public enum Direction {
    DEBIT,
    CREDIT
  }
}