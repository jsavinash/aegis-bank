package com.aegis.payments.domain;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * Payment lifecycle state machine.
 *
 * <pre>
 *   PENDING  -> PROCESSING | CANCELLED | FAILED
 *   PROCESSING -> COMPLETED | FAILED | RECALLED
 *   COMPLETED -> RECALLED
 *   FAILED, CANCELLED, RECALLED -> (terminal)
 * </pre>
 */
public enum PaymentStatus {
  PENDING,
  PROCESSING,
  COMPLETED,
  FAILED,
  CANCELLED,
  RECALLED;

  private static final Map<PaymentStatus, Set<PaymentStatus>> ALLOWED =
      Map.of(
          PENDING, EnumSet.of(PROCESSING, CANCELLED, FAILED),
          PROCESSING, EnumSet.of(COMPLETED, FAILED, RECALLED),
          COMPLETED, EnumSet.of(RECALLED),
          FAILED, EnumSet.noneOf(PaymentStatus.class),
          CANCELLED, EnumSet.noneOf(PaymentStatus.class),
          RECALLED, EnumSet.noneOf(PaymentStatus.class));

  public boolean canTransitionTo(PaymentStatus target) {
    return ALLOWED.getOrDefault(this, Set.of()).contains(target);
  }
}