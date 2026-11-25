package com.aegis.wealth.web;

import com.aegis.wealth.domain.Money;
import com.aegis.wealth.domain.Portfolio;
import java.util.Map;
import java.util.UUID;

/** Immutable snapshot of a portfolio for API responses; never exposes the mutable entity. */
public record PortfolioView(
    UUID id,
    String customerId,
    String currency,
    Map<String, Money> holdings,
    Money value,
    long version) {

  public static PortfolioView from(Portfolio p) {
    return new PortfolioView(
        p.id(), p.customerId(), p.currency(), p.holdings(), p.value(), p.version());
  }
}