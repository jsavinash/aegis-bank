package com.aegis.lending.domain;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * Loan lifecycle state machine.
 *
 * <pre>
 *   UNDERWRITING -> APPROVED | DECLINED
 *   APPROVED     -> DISBURSED
 *   DISBURSED    -> ACTIVE
 *   ACTIVE       -> PAID_OFF | DEFAULTED
 *   PAID_OFF, DEFAULTED, DECLINED -> (terminal)
 * </pre>
 *
 * <p>Transitions are the only way state changes; illegal transitions raise
 * {@code InvalidLoanTransition}.
 */
public enum LoanStatus {
  UNDERWRITING,
  APPROVED,
  DECLINED,
  DISBURSED,
  ACTIVE,
  PAID_OFF,
  DEFAULTED;

  private static final Map<LoanStatus, Set<LoanStatus>> ALLOWED =
      Map.of(
          UNDERWRITING, EnumSet.of(APPROVED, DECLINED),
          APPROVED, EnumSet.of(DISBURSED),
          DECLINED, EnumSet.noneOf(LoanStatus.class),
          DISBURSED, EnumSet.of(ACTIVE),
          ACTIVE, EnumSet.of(PAID_OFF, DEFAULTED),
          PAID_OFF, EnumSet.noneOf(LoanStatus.class),
          DEFAULTED, EnumSet.noneOf(LoanStatus.class));

  public boolean canTransitionTo(LoanStatus target) {
    return ALLOWED.getOrDefault(this, Set.of()).contains(target);
  }
}