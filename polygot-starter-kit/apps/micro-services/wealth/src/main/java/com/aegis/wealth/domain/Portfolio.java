package com.aegis.wealth.domain;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Portfolio aggregate root: a single-currency collection of holdings keyed by symbol.
 *
 * <p>Holdings preserve insertion order. Adding to an existing symbol accumulates value. The entity is
 * not thread-safe on its own; the service serializes access with a per-portfolio lock.
 */
public final class Portfolio {

  private final UUID id;
  private final String customerId;
  private final String currency;
  private final Map<String, Money> holdings = new LinkedHashMap<>();

  private long version;

  public Portfolio(UUID id, String customerId, String currency) {
    this.id = Objects.requireNonNull(id, "id must not be null");
    this.customerId = requireText(customerId, "customerId");
    this.currency = Money.zero(requireText(currency, "currency")).currency();
    this.version = 0L;
  }

  public UUID id() {
    return id;
  }

  public String customerId() {
    return customerId;
  }

  public String currency() {
    return currency;
  }

  public long version() {
    return version;
  }

  public Map<String, Money> holdings() {
    return Collections.unmodifiableMap(holdings);
  }

  public void addHolding(String symbol, Money value) {
    String key = requireText(symbol, "symbol").trim().toUpperCase();
    Objects.requireNonNull(value, "value must not be null");
    if (!currency.equals(value.currency())) {
      throw new IllegalArgumentException(
          "currency mismatch: portfolio=" + currency + " holding=" + value.currency());
    }
    Money existing = holdings.get(key);
    holdings.put(key, existing == null ? value : existing.plus(value));
    version++;
  }

  public Money value() {
    Money total = Money.zero(currency);
    for (Money holding : holdings.values()) {
      total = total.plus(holding);
    }
    return total;
  }

  private static String requireText(String value, String field) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(field + " must not be blank");
    }
    return value;
  }
}