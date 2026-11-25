package com.aegis.accounts.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.aegis.accounts.error.InsufficientFunds;
import com.aegis.accounts.error.InvalidTransition;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class AccountTest {

  private Account activeAccount() {
    return new Account(UUID.randomUUID(), "c1", AccountType.CHECKING, "USD");
  }

  @Test
  void depositCreditsBalanceAndPostsLedger() {
    Account a = activeAccount();
    a.deposit(Money.of(new BigDecimal("100.00"), "USD"), "k1");
    assertEquals(new BigDecimal("100.00"), a.balance().amount());
    assertEquals(1, a.ledger().size());
    assertEquals(LedgerEntry.Direction.CREDIT, a.ledger().get(0).direction());
  }

  @Test
  void withdrawDebitsBalance() {
    Account a = activeAccount();
    a.deposit(Money.of(new BigDecimal("100.00"), "USD"), "k1");
    a.withdraw(Money.of(new BigDecimal("40.00"), "USD"), "k2");
    assertEquals(new BigDecimal("60.00"), a.balance().amount());
  }

  @Test
  void withdrawBeyondBalanceThrowsAndLeavesBalanceUntouched() {
    Account a = activeAccount();
    a.deposit(Money.of(new BigDecimal("10.00"), "USD"), "k1");
    assertThrows(InsufficientFunds.class, () -> a.withdraw(Money.of(new BigDecimal("50.00"), "USD"), "k2"));
    assertEquals(new BigDecimal("10.00"), a.balance().amount());
    assertEquals(1, a.ledger().size());
  }

  @Test
  void rejectsNonPositiveAmounts() {
    Account a = activeAccount();
    assertThrows(IllegalArgumentException.class, () -> a.deposit(Money.ZERO("USD"), "k"));
  }

  @Test
  void stateMachineAllowsValidTransitions() {
    Account a = activeAccount();
    a.freeze();
    assertEquals(AccountStatus.FROZEN, a.status());
    a.unfreeze();
    assertEquals(AccountStatus.ACTIVE, a.status());
    a.close();
    assertEquals(AccountStatus.CLOSED, a.status());
  }

  @Test
  void stateMachineRejectsInvalidTransitions() {
    Account a = activeAccount();
    a.close();
    assertThrows(InvalidTransition.class, a::unfreeze);
    assertThrows(InvalidTransition.class, a::freeze);
  }

  @Test
  void closedAccountRejectsDeposits() {
    Account a = activeAccount();
    a.close();
    assertThrows(RuntimeException.class, () -> a.deposit(Money.of(new BigDecimal("1.00"), "USD"), "k"));
  }

  @Test
  void versionIncrementsOnEachMutation() {
    Account a = activeAccount();
    long v0 = a.version();
    a.deposit(Money.of(new BigDecimal("5.00"), "USD"), "k1");
    a.freeze();
    assertTrue(a.version() > v0);
  }
}