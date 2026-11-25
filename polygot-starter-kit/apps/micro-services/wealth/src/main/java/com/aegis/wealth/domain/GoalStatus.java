package com.aegis.wealth.domain;

import java.util.EnumSet;
import java.util.Set;

/**
 * Lifecycle state of a savings goal with an explicit transition table (state machine).
 *
 * <p>Transitions are the only way state changes; illegal transitions raise {@code InvalidGoalTransition}.
 * {@code COMPLETED} and {@code CANCELLED} are terminal.
 */
public enum GoalStatus {
  ACTIVE,
  COMPLETED,
  CANCELLED;

  private static final java.util.Map<GoalStatus, Set<GoalStatus>> ALLOWED =
      java.util.Map.of(
          ACTIVE, EnumSet.of(COMPLETED, CANCELLED),
          COMPLETED, EnumSet.noneOf(GoalStatus.class),
          CANCELLED, EnumSet.noneOf(GoalStatus.class));

  public boolean canTransitionTo(GoalStatus target) {
    return ALLOWED.getOrDefault(this, Set.of()).contains(target);
  }
}