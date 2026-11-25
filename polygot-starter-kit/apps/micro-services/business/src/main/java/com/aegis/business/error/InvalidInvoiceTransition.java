package com.aegis.business.error;

import com.aegis.business.domain.InvoiceStatus;
import java.util.UUID;

/** Raised on an illegal invoice-state transition. Maps to HTTP 409. */
public class InvalidInvoiceTransition extends BusinessError {

  public InvalidInvoiceTransition(UUID id, InvoiceStatus from, InvoiceStatus to) {
    super(
        "invalid_transition",
        "cannot transition invoice " + id + " from " + from + " to " + to);
  }
}