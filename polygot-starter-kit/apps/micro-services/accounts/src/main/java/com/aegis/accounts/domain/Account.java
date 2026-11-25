package com.aegis.accounts.domain;

import com.aegis.accounts.error.AccountError;
import com.aegis.accounts.error.InsufficientFunds;
import com.aegis.accounts.error.InvalidTransition;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Account aggregate root: owns its balance, status, and ledger postings.
 *
 * <p>All mutations go through methods that enforce the state machine and produce balanced ledger
 * entries. The entity is not thread-safe on its own; the service serializes access with a per-account
 * lock. {@code version} supports optimistic concurrency checks at the persistence boundary.
 */
public final class Account {

  private final UUID id;
  private final String customerId;
  private final AccountType type;
  private final String currency;
  private final Instant openedAt;
  private final List<LedgerEntry> ledger = new ArrayList<>();

  private Money balance;
  private AccountStatus status;
  private long version;

  public Account(UUID id, String customerId, AccountType type, String currency) {
    this.id = Objects.requireNonNull(id, "id must not be null");
    this.customerId = requireText(customerId, "customerId");
    this.type = Objects.requireNonNull(type, "type must not be null");
    this.currency = Money.zero(requireText(currency, "currency")).currency();
    this.balance = Money.zero(this.currency);
    this.status = AccountStatus.ACTIVE;
    this.openedAt = Instant.now();
  }

  public UUID id() {
    return id;
  }

  public String customerId() {
    return customerId;
  }

  public AccountType type() {
    return type;
  }

  public String currency() {
    return currency;
  }

  public Money balance() {
    return balance;
  }

  public AccountStatus status() {
    return status;
  }

  public long version() {
    return version;
  }

  public Instant openedAt() {
    return openedAt;
  }

  public List<LedgerEntry> ledger() {
    return Collections.unmodifiableList(ledger);
  }

  /** Credit the account (money in). Idempotency key dedups the posting at the service layer. */
  public LedgerEntry deposit(Money amount, String idempotencyKey) {
    requireActive();
    requirePositive(amount);
    requireSameCurrency(amount);
    Money next = balance.plus(amount);
    LedgerEntry entry =
        new LedgerEntry(
            UUID.randomUUID(), id, amount, LedgerEntry.Direction.CREDIT, idempotencyKey, Instant.now());
    applyBalance(next, entry);
    return entry;
  }

  /** Debit the account (money out). Fails if funds are insufficient; never drives balance negative. */
  public LedgerEntry withdraw(Money amount, String idempotencyKey) {
    requireActive();
    requirePositive(amount);
    requireSameCurrency(amount);
    if (balance.isLessThan(amount)) {
      throw new InsufficientFunds(id, balance, amount);
    }
    Money next = balance.minus(amount);
    LedgerEntry entry =
        new LedgerEntry(
            UUID.randomUUID(), id, amount, LedgerEntry.Direction.DEBIT, idempotencyKey, Instant.now());
    applyBalance(next, entry);
    return entry;
  }

  public void freeze() {
    transition(AccountStatus.FROZEN);
  }

  public void unfreeze() {
    transition(AccountStatus.ACTIVE);
  }

  public void close() {
    transition(AccountStatus.CLOSED);
  }

  private void transition(AccountStatus target) {
    if (!status.canTransitionTo(target)) {
      throw new InvalidTransition(id, status, target);
    }
    this.status = target;
    this.version++;
  }

  private void applyBalance(Money next, LedgerEntry entry) {
    this.balance = next;
    this.ledger.add(entry);
    this.version++;
  }

  private void requireActive() {
    if (status != AccountStatus.ACTIVE) {
      throw new AccountError("invalid_state", "account " + id + " is " + status + ", not ACTIVE");
    }
  }

  private void requirePositive(Money amount) {
    if (amount.amount().signum() <= 0) {
      throw new IllegalArgumentException("amount must be positive");
    }
  }

  private void requireSameCurrency(Money amount) {
    if (!currency.equals(amount.currency())) {
      throw new IllegalArgumentException(
          "currency mismatch: account=" + currency + " amount=" + amount.currency());
    }
  }

  private static String requireText(String value, String field) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(field + " must not be blank");
    }
    return value;
  }
}