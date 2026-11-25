package com.aegis.lending.error;

import java.util.UUID;

/** Raised when a loan is not found. Maps to HTTP 404. */
public class LoanNotFound extends LoanError {

  public LoanNotFound(UUID id) {
    super("loan_not_found", "loan not found: " + id);
  }
}