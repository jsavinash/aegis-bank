package com.aegis.payments.error;

import com.aegis.payments.domain.PaymentStatus;
import java.util.UUID;

/** Raised on an illegal payment-state transition. Maps to HTTP 409. */
public class InvalidPaymentTransition extends PaymentError {

  public InvalidPaymentTransition(UUID id, PaymentStatus from, PaymentStatus to) {
    super(
        "invalid_transition",
        "cannot transition payment " + id + " from " + from + " to " + to);
  }
}