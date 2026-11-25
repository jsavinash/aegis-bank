package com.aegis.wealth.domain;

import com.aegis.wealth.error.InvalidGoalTransition;
import java.util.Objects;
import java.util.UUID;

/**
 * SavingsGoal aggregate root: owns its target, saved amount, and lifecycle status.
 *
 * <p>All mutations go through methods that enforce the state machine. The entity is not thread-safe on
 * its own; the service serializes access with a per-goal lock. {@code version} supports optimistic
 * concurrency checks at the persistence boundary.
 */
public final class SavingsGoal {

  private final UUID id;
  private final String customerId;
  private final String name;
  private final Money target;
  private final String currency;

  private Money saved;
  private GoalStatus status;
  private long version;

  public SavingsGoal(UUID id, String customerId, String name, Money target, String currency) {
    this.id = Objects.requireNonNull(id, "id must not be null");
    this.customerId = requireText(customerId, "customerId");
    this.name = requireText(name, "name");
    this.target = Objects.requireNonNull(target, "target must not be null");
    this.currency = Money.zero(requireText(currency, "currency")).currency();
    if (!this.currency.equals(target.currency())) {
      throw new IllegalArgumentException(
          "currency mismatch: goal=" + this.currency + " target=" + target.currency());
    }
    this.saved = Money.zero(this.currency);
    this.status = GoalStatus.ACTIVE;
    this.version = 0L;
  }

  public UUID id() {
    return id;
  }

  public String customerId() {
    return customerId;
  }

  public String name() {
    return name;
  }

  public Money target() {
    return target;
  }

  public Money saved() {
    return saved;
  }

  public String currency() {
    return currency;
  }

  public GoalStatus status() {
    return status;
  }

  public long version() {
    return version;
  }

  /**
   * Add to the saved amount. Reaching or exceeding the target auto-completes the goal. Contributing to a
   * goal that is no longer {@code ACTIVE} is rejected.
   */
  public void contribute(Money amount) {
    if (status != GoalStatus.ACTIVE) {
      throw new InvalidGoalTransition(id, status);
    }
    requirePositive(amount);
    requireSameCurrency(amount);
    this.saved = saved.plus(amount);
    this.version++;
    if (saved.isGreaterThanOrEqualTo(target)) {
      transition(GoalStatus.COMPLETED);
    }
  }

  public void cancel() {
    transition(GoalStatus.CANCELLED);
  }

  private void transition(GoalStatus target) {
    if (!status.canTransitionTo(target)) {
      throw new InvalidGoalTransition(id, status, target);
    }
    this.status = target;
    this.version++;
  }

  private void requirePositive(Money amount) {
    if (amount.amount().signum() <= 0) {
      throw new IllegalArgumentException("amount must be positive");
    }
  }

  private void requireSameCurrency(Money amount) {
    if (!currency.equals(amount.currency())) {
      throw new IllegalArgumentException(
          "currency mismatch: goal=" + currency + " amount=" + amount.currency());
    }
  }

  private static String requireText(String value, String field) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(field + " must not be blank");
    }
    return value;
  }
}