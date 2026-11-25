package com.aegis.lending.error;

import com.aegis.lending.domain.LoanStatus;
import java.util.UUID;

/** Raised on an illegal loan-state transition. Maps to HTTP 409. */
public class InvalidLoanTransition extends LoanError {

  public InvalidLoanTransition(UUID id, LoanStatus from, LoanStatus to) {
    super(
        "invalid_transition",
        "cannot transition loan " + id + " from " + from + " to " + to);
  }
}