package com.aegis.lending;

import static org.junit.jupiter.api.Assertions.*;

import com.aegis.lending.command.ApplyLoan;
import com.aegis.lending.domain.LoanStatus;
import com.aegis.lending.error.InvalidLoanTransition;
import com.aegis.lending.error.LoanNotFound;
import com.aegis.lending.web.LoanView;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

class LoanServiceTest {

  private final LoanService service = new LoanService();

  private LoanView apply() {
    return service.apply(new ApplyLoan("c1", "PERSONAL", new BigDecimal("5000.00"), "USD"));
  }

  @Test
  void applyStartsUnderwriting() {
    LoanView loan = apply();
    assertEquals(LoanStatus.UNDERWRITING, loan.status());
    assertEquals(new BigDecimal("5000.00"), loan.principal().amount());
    assertDoesNotThrow(() -> service.get(loan.id()));
  }

  @Test
  void happyPathThroughLifecycle() {
    LoanView loan = apply();
    assertEquals(LoanStatus.APPROVED, service.approve(loan.id()).status());
    assertEquals(LoanStatus.DISBURSED, service.disburse(loan.id()).status());
    assertEquals(LoanStatus.ACTIVE, service.activate(loan.id()).status());
    assertEquals(LoanStatus.PAID_OFF, service.payOff(loan.id()).status());
  }

  @Test
  void declineIsTerminal() {
    LoanView loan = apply();
    assertEquals(LoanStatus.DECLINED, service.decline(loan.id()).status());
    assertThrows(InvalidLoanTransition.class, () -> service.approve(loan.id()));
  }

  @Test
  void illegalTransitionThrows() {
    LoanView loan = apply();
    assertThrows(InvalidLoanTransition.class, () -> service.disburse(loan.id()));
  }

  @Test
  void payOffOnlyFromActive() {
    LoanView loan = apply();
    assertThrows(InvalidLoanTransition.class, () -> service.payOff(loan.id()));
  }

  @Test
  void getUnknownThrowsNotFound() {
    assertThrows(LoanNotFound.class, () -> service.get(UUID.randomUUID()));
  }

  @Test
  void transitionUnknownThrowsNotFound() {
    assertThrows(LoanNotFound.class, () -> service.approve(UUID.randomUUID()));
  }

  @Test
  void nonPositivePrincipalRejected() {
    assertThrows(
        IllegalArgumentException.class,
        () -> service.apply(new ApplyLoan("c1", "PERSONAL", BigDecimal.ZERO, "USD")));
  }

  @Test
  void idempotencyKeyReplaysSameLoan() {
    ApplyLoan req = new ApplyLoan("c1", "PERSONAL", new BigDecimal("5000.00"), "USD");
    LoanView first = service.apply(req);
    LoanView second = service.apply(req);
    assertEquals(first.id(), second.id());
    assertEquals(1, service.list().size());
  }

  @Test
  void concurrentSameKeyCreatesSingleLoan() throws Exception {
    ApplyLoan req = new ApplyLoan("c2", "AUTO", new BigDecimal("9000.00"), "USD");
    int threads = 16;
    ExecutorService pool = Executors.newFixedThreadPool(threads);
    CountDownLatch ready = new CountDownLatch(threads);
    CountDownLatch go = new CountDownLatch(1);
    List<UUID> ids = Collections.synchronizedList(new ArrayList<>());
    for (int i = 0; i < threads; i++) {
      pool.submit(
          () -> {
            ready.countDown();
            try {
              go.await();
              ids.add(service.apply(req).id());
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
            }
          });
    }
    assertTrue(ready.await(5, TimeUnit.SECONDS));
    go.countDown();
    pool.shutdown();
    assertTrue(pool.awaitTermination(10, TimeUnit.SECONDS));
    assertEquals(1, service.list().size());
    assertEquals(1, ids.stream().distinct().count());
  }
}