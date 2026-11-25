package com.aegis.wealth.web;

import com.aegis.wealth.domain.GoalStatus;
import com.aegis.wealth.domain.Money;
import com.aegis.wealth.domain.SavingsGoal;
import java.util.UUID;

/** Immutable snapshot of a savings goal for API responses; never exposes the mutable entity. */
public record GoalView(
    UUID id,
    String customerId,
    String name,
    Money target,
    Money saved,
    String currency,
    GoalStatus status,
    long version) {

  public static GoalView from(SavingsGoal g) {
    return new GoalView(
        g.id(), g.customerId(), g.name(), g.target(), g.saved(), g.currency(), g.status(),
        g.version());
  }
}