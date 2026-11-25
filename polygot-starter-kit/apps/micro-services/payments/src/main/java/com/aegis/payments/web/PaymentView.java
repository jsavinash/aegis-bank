package com.aegis.payments.web;

import com.aegis.payments.domain.Money;
import com.aegis.payments.domain.Payment;
import com.aegis.payments.domain.PaymentStatus;
import com.aegis.payments.domain.PaymentType;
import java.time.Instant;
import java.util.UUID;

/** Immutable snapshot of a payment for API responses. */
public record PaymentView(
    UUID id,
    String fromAccount,
    String toAccount,
    Money amount,
    PaymentType type,
    PaymentStatus status,
    long version,
    Instant createdAt) {

  public static PaymentView from(Payment p) {
    return new PaymentView(
        p.id(), p.fromAccount(), p.toAccount(), p.amount(), p.type(), p.status(), p.version(),
        p.createdAt());
  }
}