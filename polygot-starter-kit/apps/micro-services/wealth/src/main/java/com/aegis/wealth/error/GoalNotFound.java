package com.aegis.wealth.error;

import java.util.UUID;

/** Raised when a savings goal is not found. Maps to HTTP 404. */
public class GoalNotFound extends WealthError {

  public GoalNotFound(UUID id) {
    super("goal_not_found", "goal not found: " + id);
  }
}