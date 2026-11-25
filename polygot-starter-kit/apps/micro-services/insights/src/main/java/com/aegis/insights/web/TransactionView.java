package com.aegis.insights.web;

import com.aegis.insights.domain.Money;
import com.aegis.insights.domain.Transaction;
import java.time.Instant;
import java.util.UUID;

/** Immutable snapshot of a transaction for API responses. */
public record TransactionView(
    UUID id,
    String accountId,
    String category,
    Money amount,
    String merchant,
    Instant occurredAt) {

  public static TransactionView from(Transaction t) {
    return new TransactionView(
        t.id(), t.accountId(), t.category(), t.amount(), t.merchant(), t.occurredAt());
  }
}