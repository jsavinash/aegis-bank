package com.aegis.treasury;

import static org.junit.jupiter.api.Assertions.*;

import com.aegis.treasury.command.Credit;
import com.aegis.treasury.command.Debit;
import com.aegis.treasury.command.SetPosition;
import com.aegis.treasury.error.CashPositionNotFound;
import com.aegis.treasury.error.InsufficientFunds;
import com.aegis.treasury.web.CashPositionView;
import com.aegis.treasury.web.ForecastView;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class TreasuryServiceTest {

  private final TreasuryService service = new TreasuryService();

  private CashPositionView set(UUID accountId, String balance) {
    return service.setPosition(new SetPosition(accountId, new BigDecimal(balance), "USD"));
  }

  @Test
  void setThenGet() {
    UUID accountId = UUID.randomUUID();
    CashPositionView created = set(accountId, "1000.00");
    assertEquals(accountId, created.accountId());
    assertEquals(accountId, created.id());
    assertEquals(new BigDecimal("1000.00"), created.balance().amount());
    assertEquals("USD", created.currency());
    assertEquals(0, created.version());

    CashPositionView fetched = service.get(accountId);
    assertEquals(new BigDecimal("1000.00"), fetched.balance().amount());
  }

  @Test
  void setPositionUpsertsExisting() {
    UUID accountId = UUID.randomUUID();
    set(accountId, "100.00");
    CashPositionView replaced = set(accountId, "250.00");
    assertEquals(new BigDecimal("250.00"), replaced.balance().amount());
    assertEquals(1, replaced.version());
  }

  @Test
  void creditThenDebit() {
    UUID accountId = UUID.randomUUID();
    set(accountId, "100.00");
    CashPositionView afterCredit = service.credit(accountId, new Credit(new BigDecimal("50.00")));
    assertEquals(new BigDecimal("150.00"), afterCredit.balance().amount());
    CashPositionView afterDebit = service.debit(accountId, new Debit(new BigDecimal("30.00")));
    assertEquals(new BigDecimal("120.00"), afterDebit.balance().amount());
  }

  @Test
  void debitBeyondBalanceThrowsInsufficientFunds() {
    UUID accountId = UUID.randomUUID();
    set(accountId, "10.00");
    assertThrows(
        InsufficientFunds.class,
        () -> service.debit(accountId, new Debit(new BigDecimal("99.00"))));
  }

  @Test
  void forecastIsLinearProjection() {
    UUID accountId = UUID.randomUUID();
    set(accountId, "1000.00");
    ForecastView view = service.forecast(accountId, 13);
    assertEquals(13, view.weeks());
    assertEquals(new BigDecimal("13000.00"), view.projectedBalance());
  }

  @Test
  void forecastDefaultsAreDeterministic() {
    UUID accountId = UUID.randomUUID();
    set(accountId, "200.00");
    assertEquals(service.forecast(accountId, 13).projectedBalance(), service.forecast(accountId, 13).projectedBalance());
    assertEquals(new BigDecimal("0.00"), service.forecast(accountId, 0).projectedBalance());
  }

  @Test
  void getUnknownThrowsNotFound() {
    assertThrows(CashPositionNotFound.class, () -> service.get(UUID.randomUUID()));
  }

  @Test
  void forecastUnknownThrowsNotFound() {
    assertThrows(CashPositionNotFound.class, () -> service.forecast(UUID.randomUUID(), 13));
  }

  @Test
  void negativeBalanceRejected() {
    UUID accountId = UUID.randomUUID();
    assertThrows(
        IllegalArgumentException.class,
        () -> service.setPosition(new SetPosition(accountId, new BigDecimal("-1.00"), "USD")));
  }

  @Test
  void negativeAmountsRejected() {
    UUID accountId = UUID.randomUUID();
    set(accountId, "100.00");
    assertThrows(IllegalArgumentException.class, () -> service.credit(accountId, new Credit(new BigDecimal("-1.00"))));
    assertThrows(IllegalArgumentException.class, () -> service.debit(accountId, new Debit(new BigDecimal("-1.00"))));
  }

  @Test
  void allReturnsEveryPosition() {
    UUID a = UUID.randomUUID();
    UUID b = UUID.randomUUID();
    set(a, "1.00");
    set(b, "2.00");
    assertTrue(service.all().size() >= 2);
  }
}