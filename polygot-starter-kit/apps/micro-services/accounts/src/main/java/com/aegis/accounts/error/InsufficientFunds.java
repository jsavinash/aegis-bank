package com.aegis.accounts.error;

import com.aegis.accounts.domain.Money;
import java.util.UUID;

/** Raised when a debit exceeds the available balance. Maps to HTTP 409. */
public class InsufficientFunds extends AccountError {

  public InsufficientFunds(UUID id, Money available, Money requested) {
    super(
        "insufficient_funds",
        "account "
            + id
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