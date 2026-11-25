package com.aegis.business.domain;

import java.util.EnumSet;
import java.util.Set;

/**
 * Lifecycle state of an invoice with an explicit transition table (state machine).
 *
 * <p>Transitions are the only way state changes; illegal transitions raise
 * {@code InvalidInvoiceTransition}. {@code OPEN} may be settled, become {@code OVERDUE}, or be
 * voided; {@code OVERDUE} may still be settled or voided; {@code PAID} and {@code VOID} are
 * terminal.
 */
public enum InvoiceStatus {
  OPEN,
  PAID,
  OVERDUE,
  VOID;

  private static final java.util.Map<InvoiceStatus, Set<InvoiceStatus>> ALLOWED =
      java.util.Map.of(
          OPEN, EnumSet.of(PAID, OVERDUE, VOID),
          OVERDUE, EnumSet.of(PAID, VOID),
          PAID, EnumSet.noneOf(InvoiceStatus.class),
          VOID, EnumSet.noneOf(InvoiceStatus.class));

  public boolean canTransitionTo(InvoiceStatus target) {
    return ALLOWED.getOrDefault(this, Set.of()).contains(target);
  }
}