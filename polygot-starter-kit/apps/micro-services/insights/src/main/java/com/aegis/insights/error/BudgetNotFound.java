package com.aegis.insights.error;

import java.util.UUID;

/** Raised when a budget is not found. Maps to HTTP 404. */
public class BudgetNotFound extends InsightsError {

  public BudgetNotFound(UUID id) {
    super("budget_not_found", "budget not found: " + id);
  }
}