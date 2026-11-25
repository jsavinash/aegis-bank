package com.aegis.payments.error;

import java.util.UUID;

/** Raised when a payment is not found. Maps to HTTP 404. */
public class PaymentNotFound extends PaymentError {

  public PaymentNotFound(UUID id) {
    super("payment_not_found", "payment not found: " + id);
  }
}