package com.aegis.payments;

import static org.junit.jupiter.api.Assertions.*;

import com.aegis.payments.command.InitiatePayment;
import com.aegis.payments.domain.PaymentStatus;
import com.aegis.payments.error.InvalidPaymentTransition;
import com.aegis.payments.error.PaymentNotFound;
import com.aegis.payments.web.PaymentView;
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

class PaymentServiceTest {

  private final PaymentService service = new PaymentService();

  private PaymentView initiate(String key) {
    return service.initiate(
        new InitiatePayment(key, "a1", "a2", new BigDecimal("10.00"), "USD", "DOMESTIC"));
  }

  @Test
  void initiateStartsPending() {
    PaymentView p = initiate("k1");
    assertEquals(PaymentStatus.PENDING, p.status());
    assertEquals(new BigDecimal("10.00"), p.amount().amount());
  }

  @Test
  void idempotencyReturnsSamePayment() {
    PaymentView a = initiate("dup");
    PaymentView b = initiate("dup");
    assertEquals(a.id(), b.id());
    assertEquals(1, service.list().size());
  }

  @Test
  void happyPathTransitions() {
    PaymentView p = initiate("k2");
    assertEquals(PaymentStatus.PROCESSING, service.process(p.id()).status());
    assertEquals(PaymentStatus.COMPLETED, service.complete(p.id()).status());
  }

  @Test
  void cannotCompletePendingDirectly() {
    PaymentView p = initiate("k3");
    assertThrows(InvalidPaymentTransition.class, () -> service.complete(p.id()));
  }

  @Test
  void cancelFromPendingIsAllowed() {
    PaymentView p = initiate("k4");
    assertEquals(PaymentStatus.CANCELLED, service.cancel(p.id()).status());
  }

  @Test
  void recallAfterCompletionIsAllowed() {
    PaymentView p = initiate("k5");
    service.process(p.id());
    service.complete(p.id());
    assertEquals(PaymentStatus.RECALLED, service.recall(p.id()).status());
  }

  @Test
  void terminalStateRejectsFurtherTransitions() {
    PaymentView p = initiate("k6");
    service.cancel(p.id());
    assertThrows(InvalidPaymentTransition.class, () -> service.process(p.id()));
  }

  @Test
  void unknownPaymentThrowsNotFound() {
    assertThrows(PaymentNotFound.class, () -> service.get(UUID.randomUUID()));
  }

  @Test
  void rejectsNonPositiveAmount() {
    assertThrows(
        IllegalArgumentException.class,
        () -> service.initiate(
            new InitiatePayment("z", "a1", "a2", BigDecimal.ZERO, "USD", "DOMESTIC")));
  }

  @Test
  void rejectsUnknownType() {
    assertThrows(
        IllegalArgumentException.class,
        () -> service.initiate(
            new InitiatePayment("z", "a1", "a2", BigDecimal.ONE, "USD", "TELEPATHY")));
  }

  @Test
  void concurrentSameKeyCreatesSinglePayment() throws Exception {
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
              ids.add(initiate("race").id());
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