package com.aegis.payments.domain;

/** Payment rail / method. Determines clearing behaviour and SLA. */
public enum PaymentType {
  DOMESTIC,
  P2P,
  WIRE,
  INTERNATIONAL,
  STANDING_ORDER,
  SCHEDULED,
  REQUEST_TO_PAY,
  QR
}