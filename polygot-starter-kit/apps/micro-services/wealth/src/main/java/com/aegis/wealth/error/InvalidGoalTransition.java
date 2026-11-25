package com.aegis.wealth.error;

import com.aegis.wealth.domain.GoalStatus;
import java.util.UUID;

/** Raised on an illegal savings-goal state transition or mutation. Maps to HTTP 409. */
public class InvalidGoalTransition extends WealthError {

  public InvalidGoalTransition(UUID id, GoalStatus from, GoalStatus to) {
    super(
        "invalid_goal_transition",
        "cannot transition goal " + id + " from " + from + " to " + to);
  }

  public InvalidGoalTransition(UUID id, GoalStatus status) {
    super(
        "invalid_goal_transition",
        "goal " + id + " is " + status + " and cannot be modified");
  }
}