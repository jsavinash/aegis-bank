package com.aegis.accounts.domain;

import java.util.EnumSet;
import java.util.Set;

/**
 * Lifecycle state of an account with an explicit transition table (state machine).
 *
 * <p>Transitions are the only way state changes; illegal transitions raise {@code InvalidTransition}.
 */
public enum AccountStatus {
  ACTIVE,
  FROZEN,
  CLOSED;

  private static final java.util.Map<AccountStatus, Set<AccountStatus>> ALLOWED =
      java.util.Map.of(
          ACTIVE, EnumSet.of(FROZEN, CLOSED),
          FROZEN, EnumSet.of(ACTIVE, CLOSED),
          CLOSED, EnumSet.noneOf(AccountStatus.class));

  public boolean canTransitionTo(AccountStatus target) {
    return ALLOWED.getOrDefault(this, Set.of()).contains(target);
  }
}