package com.aegis.treasury.web;

import com.aegis.treasury.domain.CashPosition;
import com.aegis.treasury.domain.Money;
import java.util.UUID;

/** Immutable snapshot of a cash position for API responses; never exposes the mutable entity. */
public record CashPositionView(
    UUID id, UUID accountId, Money balance, String currency, long version) {

  public static CashPositionView from(CashPosition p) {
    return new CashPositionView(p.id(), p.accountId(), p.balance(), p.currency(), p.version());
  }
}