package com.aegis.billpay.error;

import com.aegis.billpay.domain.BillStatus;
import java.util.UUID;

/** Raised on an illegal bill-state transition. Maps to HTTP 409. */
public class InvalidBillTransition extends BillError {

  public InvalidBillTransition(UUID id, BillStatus from, BillStatus to) {
    super("invalid_transition", "cannot transition bill " + id + " from " + from + " to " + to);
  }
}