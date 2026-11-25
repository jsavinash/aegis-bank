package com.aegis.accounts;

import static org.junit.jupiter.api.Assertions.*;

import com.aegis.accounts.command.MoveMoney;
import com.aegis.accounts.command.OpenAccount;
import com.aegis.accounts.command.StatusChange;
import com.aegis.accounts.domain.AccountStatus;
import com.aegis.accounts.error.AccountNotFound;
import com.aegis.accounts.error.InsufficientFunds;
import com.aegis.accounts.web.AccountView;
import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

class AccountServiceTest {

  private final AccountService service = new AccountService();

  private AccountView open() {
    return service.open(new OpenAccount("c1", "CHECKING", "USD"));
  }

  @Test
  void openAndGet() {
    AccountView a = open();
    assertEquals(AccountStatus.ACTIVE, a.status());
    assertEquals(new BigDecimal("0.00"), a.balance().amount());
    assertDoesNotThrow(() -> service.get(a.id()));
  }

  @Test
  void getUnknownThrowsNotFound() {
    assertThrows(AccountNotFound.class, () -> service.get(java.util.UUID.randomUUID()));
  }

  @Test
  void depositThenWithdraw() {
    AccountView a = open();
    service.deposit(a.id(), new MoveMoney(new BigDecimal("100.00"), "d1"));
    AccountView after = service.withdraw(a.id(), new MoveMoney(new BigDecimal("30.00"), "w1"));
    assertEquals(new BigDecimal("70.00"), after.balance().amount());
    assertEquals(2, service.ledger(a.id()).size());
  }

  @Test
  void withdrawBeyondBalanceThrows() {
    AccountView a = open();
    service.deposit(a.id(), new MoveMoney(new BigDecimal("10.00"), "d1"));
    assertThrows(
        InsufficientFunds.class,
        () -> service.withdraw(a.id(), new MoveMoney(new BigDecimal("99.00"), "w1")));
  }

  @Test
  void idempotencyKeyReplaysWithoutDoublePosting() {
    AccountView a = open();
    MoveMoney req = new MoveMoney(new BigDecimal("25.00"), "same-key");
    AccountView first = service.deposit(a.id(), req);
    AccountView second = service.deposit(a.id(), req);
    assertEquals(first.balance(), second.balance());
    assertEquals(1, service.ledger(a.id()).size());
  }

  @Test
  void freezeBlocksFurtherDebits() {
    AccountView a = open();
    service.freeze(a.id(), new StatusChange("f1"));
    assertThrows(
        RuntimeException.class,
        () -> service.deposit(a.id(), new MoveMoney(new BigDecimal("1.00"), "d1")));
  }

  @Test
  void concurrentDepositsDoNotLoseUpdates() throws Exception {
    AccountView a = open();
    int threads = 16;
    ExecutorService pool = Executors.newFixedThreadPool(threads);
    CountDownLatch ready = new CountDownLatch(threads);
    CountDownLatch go = new CountDownLatch(1);
    AtomicInteger ok = new AtomicInteger();
    for (int i = 0; i < threads; i++) {
      pool.submit(
          () -> {
            ready.countDown();
            try {
              go.await();
              service.deposit(a.id(), new MoveMoney(new BigDecimal("1.00"), "k-" + i));
              ok.incrementAndGet();
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
            }
          });
    }
    assertTrue(ready.await(5, TimeUnit.SECONDS));
    go.countDown();
    pool.shutdown();
    assertTrue(pool.awaitTermination(10, TimeUnit.SECONDS));
    assertEquals(threads, ok.get());
    assertEquals(new BigDecimal("16.00"), service.get(a.id()).balance().amount());
    assertEquals(threads, service.ledger(a.id()).size());
  }
}