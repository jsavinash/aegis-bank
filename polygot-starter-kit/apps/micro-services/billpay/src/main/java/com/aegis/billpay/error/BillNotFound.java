package com.aegis.billpay.error;

import java.util.UUID;

/** Raised when a bill is not found. Maps to HTTP 404. */
public class BillNotFound extends BillError {

  public BillNotFound(UUID id) {
    super("bill_not_found", "bill not found: " + id);
  }
}