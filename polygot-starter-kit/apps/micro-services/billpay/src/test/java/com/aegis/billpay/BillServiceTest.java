package com.aegis.billpay;

import static org.junit.jupiter.api.Assertions.*;

import com.aegis.billpay.command.AddBill;
import com.aegis.billpay.domain.BillStatus;
import com.aegis.billpay.error.BillNotFound;
import com.aegis.billpay.error.InvalidBillTransition;
import com.aegis.billpay.web.BillView;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class BillServiceTest {

  private final BillService service = new BillService();

  private BillView add() {
    return service.add(
        new AddBill("c1", "ACME", new BigDecimal("50.00"), "USD", LocalDate.now().plusDays(3)));
  }

  @Test
  void addStartsScheduled() {
    assertEquals(BillStatus.SCHEDULED, add().status());
  }

  @Test
  void autopayThenPaySucceeds() {
    BillView b = add();
    assertEquals(BillStatus.AUTOPIAY, service.enableAutopay(b.id()).status());
    assertEquals(BillStatus.PAID, service.pay(b.id()).status());
  }

  @Test
  void cannotPayCancelledBill() {
    BillView b = add();
    service.cancel(b.id());
    assertThrows(InvalidBillTransition.class, () -> service.pay(b.id()));
  }

  @Test
  void overdueDetection() {
    service.add(
        new AddBill("c1", "ACME", new BigDecimal("10.00"), "USD", LocalDate.now().minusDays(1)));
    assertFalse(service.overdue().isEmpty());
  }

  @Test
  void unknownBillThrowsNotFound() {
    assertThrows(BillNotFound.class, () -> service.get(UUID.randomUUID()));
  }

  @Test
  void rejectsNonPositiveAmount() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            service.add(
                new AddBill("c1", "ACME", BigDecimal.ZERO, "USD", LocalDate.now().plusDays(1))));
  }
}