package com.aegis.accounts.error;

import java.util.UUID;

/** Raised when an account is not found. Maps to HTTP 404. */
public class AccountNotFound extends AccountError {

  public AccountNotFound(UUID id) {
    super("account_not_found", "account not found: " + id);
  }
}