package com.aegis.security.domain;

import java.util.EnumSet;
import java.util.Set;

/**
 * Lifecycle state of a fraud alert with an explicit transition table (state machine).
 *
 * <p>An alert starts {@link #OPEN} and may be {@link #CONFIRMED} or {@link #DISMISSED}; both
 * terminal states are final. Illegal transitions raise {@code InvalidTransition}.
 */
public enum AlertStatus {
  OPEN,
  CONFIRMED,
  DISMISSED;

  private static final java.util.Map<AlertStatus, Set<AlertStatus>> ALLOWED =
      java.util.Map.of(
          OPEN, EnumSet.of(CONFIRMED, DISMISSED),
          CONFIRMED, EnumSet.noneOf(AlertStatus.class),
          DISMISSED, EnumSet.noneOf(AlertStatus.class));

  public boolean canTransitionTo(AlertStatus target) {
    return ALLOWED.getOrDefault(this, Set.of()).contains(target);
  }
}