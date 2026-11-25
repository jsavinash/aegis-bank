package com.aegis.credit.error;

import java.util.UUID;

/** Raised when a credit profile is not found. Maps to HTTP 404. */
public class CreditProfileNotFound extends CreditError {

  public CreditProfileNotFound(UUID id) {
    super("credit_profile_not_found", "credit profile not found: " + id);
  }
}