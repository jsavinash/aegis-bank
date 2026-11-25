package com.aegis.accounts.web;

import com.aegis.accounts.domain.Account;
import com.aegis.accounts.domain.AccountStatus;
import com.aegis.accounts.domain.AccountType;
import com.aegis.accounts.domain.Money;
import java.time.Instant;
import java.util.UUID;

/** Immutable snapshot of an account for API responses; never exposes the mutable entity. */
public record AccountView(
    UUID id,
    String customerId,
    AccountType type,
    Money balance,
    AccountStatus status,
    long version,
    Instant openedAt) {

  public static AccountView from(Account a) {
    return new AccountView(
        a.id(), a.customerId(), a.type(), a.balance(), a.status(), a.version(), a.openedAt());
  }
}