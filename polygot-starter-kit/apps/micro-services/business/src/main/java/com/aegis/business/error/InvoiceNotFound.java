package com.aegis.business.error;

import java.util.UUID;

/** Raised when an invoice is not found. Maps to HTTP 404. */
public class InvoiceNotFound extends BusinessError {

  public InvoiceNotFound(UUID id) {
    super("invoice_not_found", "invoice not found: " + id);
  }
}