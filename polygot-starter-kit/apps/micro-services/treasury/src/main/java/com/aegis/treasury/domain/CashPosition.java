package com.aegis.treasury.domain;

import com.aegis.treasury.error.InsufficientFunds;
import java.util.Objects;
import java.util.UUID;

/**
 * CashPosition aggregate root: global cash visibility for a single account.
 *
 * <p>The entity is not thread-safe on its own; the service serializes access with a per-account lock.
 * {@code version} supports optimistic concurrency checks at the persistence boundary. The identifier
 * is the owning account id, so a position is unique per account.
 */
public final class CashPosition {

  private final UUID id;
  private final UUID accountId;
  private final String currency;

  private Money balance;
  private long version;

  public CashPosition(UUID accountId, Money balance) {
    this.accountId = Objects.requireNonNull(accountId, "accountId must not be null");
    this.balance = Objects.requireNonNull(balance, "balance must not be null");
    this.id = this.accountId;
    this.currency = balance.currency();
  }

  public UUID id() {
    return id;
  }

  public UUID accountId() {
    return accountId;
  }

  public Money balance() {
    return balance;
  }

  public String currency() {
    return currency;
  }

  public long version() {
    return version;
  }

  /** Replaces the balance. Non-negativity is enforced by {@link Money}. */
  public void setBalance(Money next) {
    requireSameCurrency(next);
    this.balance = Objects.requireNonNull(next, "balance must not be null");
    this.version++;
  }

  /** Credit the position (money in). */
  public void credit(Money amount) {
    requirePositive(amount);
    requireSameCurrency(amount);
    this.balance = balance.plus(amount);
    this.version++;
  }

  /** Debit the position (money out). Fails if funds are insufficient; never drives balance negative. */
  public void debit(Money amount) {
    requirePositive(amount);
    requireSameCurrency(amount);
    if (balance.isLessThan(amount)) {
      throw new InsufficientFunds(accountId, balance, amount);
    }
    this.balance = balance.minus(amount);
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
          "currency mismatch: position=" + currency + " amount=" + amount.currency());
    }
  }
}