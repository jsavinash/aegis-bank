package com.aegis.treasury.error;

import com.aegis.treasury.domain.Money;
import java.util.UUID;

/** Raised when a debit exceeds the available balance. Maps to HTTP 409. */
public class InsufficientFunds extends TreasuryError {

  public InsufficientFunds(UUID accountId, Money available, Money requested) {
    super(
        "insufficient_funds",
        "position "
            + accountId
            + " has "
            + available.amount()
            + " "
            + available.currency()
            + " but "
            + requested.amount()
            + " "
            + requested.currency()
            + " was requested");
  }
}