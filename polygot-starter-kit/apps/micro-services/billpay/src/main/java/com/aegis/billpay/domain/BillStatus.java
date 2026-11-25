package com.aegis.billpay.domain;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * Bill lifecycle state machine.
 *
 * <pre>
 *   SCHEDULED -> AUTOPIAY | PAID | CANCELLED
 *   AUTOPIAY  -> PAID | CANCELLED
 *   PAID      -> (terminal)
 *   CANCELLED -> (terminal)
 * </pre>
 */
public enum BillStatus {
  SCHEDULED,
  AUTOPIAY,
  PAID,
  CANCELLED;

  private static final Map<BillStatus, Set<BillStatus>> ALLOWED =
      Map.of(
          SCHEDULED, EnumSet.of(AUTOPIAY, PAID, CANCELLED),
          AUTOPIAY, EnumSet.of(PAID, CANCELLED),
          PAID, EnumSet.noneOf(BillStatus.class),
          CANCELLED, EnumSet.noneOf(BillStatus.class));

  public boolean canTransitionTo(BillStatus target) {
    return ALLOWED.getOrDefault(this, Set.of()).contains(target);
  }
}