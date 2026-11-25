package com.aegis.treasury.error;

import java.util.UUID;

/** Raised when a cash position is not found. Maps to HTTP 404. */
public class CashPositionNotFound extends TreasuryError {

  public CashPositionNotFound(UUID accountId) {
    super("cash_position_not_found", "cash position not found: " + accountId);
  }
}