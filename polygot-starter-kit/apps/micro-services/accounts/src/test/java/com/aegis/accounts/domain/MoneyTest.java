package com.aegis.accounts.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class MoneyTest {

  @Test
  void rejectsNegativeAmount() {
    assertThrows(IllegalArgumentException.class, () -> Money.of(new BigDecimal("-0.01"), "USD"));
  }

  @Test
  void rejectsBadCurrency() {
    assertThrows(IllegalArgumentException.class, () -> Money.of(BigDecimal.ONE, "US"));
    assertThrows(IllegalArgumentException.class, () -> Money.of(BigDecimal.ONE, "ZZZ"));
  }

  @Test
  void normalizesCurrencyAndScale() {
    Money m = Money.of(new BigDecimal("1.005"), "usd");
    assertEquals("USD", m.currency());
    assertEquals(new BigDecimal("1.01"), m.amount());
  }

  @Test
  void plusAndMinusStayInSameCurrency() {
    Money a = Money.of(new BigDecimal("10.00"), "USD");
    Money b = Money.of(new BigDecimal("2.50"), "USD");
    assertEquals(new BigDecimal("12.50"), a.plus(b).amount());
    assertEquals(new BigDecimal("7.50"), a.minus(b).amount());
  }

  @Test
  void rejectsCurrencyMismatch() {
    Money a = Money.of(BigDecimal.ONE, "USD");
    Money b = Money.of(BigDecimal.ONE, "EUR");
    assertThrows(IllegalArgumentException.class, () -> a.plus(b));
  }

  @Test
  void comparisons() {
    Money a = Money.of(new BigDecimal("10.00"), "USD");
    Money b = Money.of(new BigDecimal("2.50"), "USD");
    assertTrue(a.isGreaterThan(b));
    assertTrue(a.isGreaterThanOrEqualTo(a));
    assertTrue(b.isLessThan(a));
    assertFalse(a.isLessThan(b));
  }
}