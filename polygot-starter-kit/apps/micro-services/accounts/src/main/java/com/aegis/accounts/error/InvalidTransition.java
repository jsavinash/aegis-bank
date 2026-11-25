package com.aegis.accounts.error;

import com.aegis.accounts.domain.AccountStatus;
import java.util.UUID;

/** Raised on an illegal account-state transition. Maps to HTTP 409. */
public class InvalidTransition extends AccountError {

  public InvalidTransition(UUID id, AccountStatus from, AccountStatus to) {
    super(
        "invalid_transition",
        "cannot transition account " + id + " from " + from + " to " + to);
  }
}