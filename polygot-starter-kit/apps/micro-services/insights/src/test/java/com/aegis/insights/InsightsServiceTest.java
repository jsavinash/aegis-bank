package com.aegis.insights;

import static org.junit.jupiter.api.Assertions.*;

import com.aegis.insights.command.CreateBudget;
import com.aegis.insights.command.RecordSpend;
import com.aegis.insights.command.RecordTransaction;
import com.aegis.insights.error.BudgetNotFound;
import com.aegis.insights.web.BudgetView;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class InsightsServiceTest {

  private final InsightsService service = new InsightsService();

  private BudgetView budget() {
    return service.createBudget(new CreateBudget("c1", "FOOD", new BigDecimal("100.00"), "USD"));
  }

  @Test
  void spendAccumulates() {
    BudgetView b = budget();
    service.spend(b.id(), new RecordSpend(new BigDecimal("30.00")));
    BudgetView after = service.spend(b.id(), new RecordSpend(new BigDecimal("20.00")));
    assertEquals(new BigDecimal("50.00"), after.spent().amount());
    assertEquals(new BigDecimal("50.00"), after.remaining());
    assertFalse(after.overBudget());
  }

  @Test
  void overspendIsFlaggedAndRemainingIsNegative() {
    BudgetView b = budget();
    BudgetView after = service.spend(b.id(), new RecordSpend(new BigDecimal("150.00")));
    assertTrue(after.overBudget());
    assertEquals(new BigDecimal("-50.00"), after.remaining());
  }

  @Test
  void unknownBudgetThrowsNotFound() {
    assertThrows(BudgetNotFound.class, () -> service.getBudget(UUID.randomUUID()));
    assertThrows(
        BudgetNotFound.class,
        () -> service.spend(UUID.randomUUID(), new RecordSpend(new BigDecimal("1.00"))));
  }

  @Test
  void nonPositiveSpendRejected() {
    BudgetView b = budget();
    assertThrows(
        IllegalArgumentException.class,
        () -> service.spend(b.id(), new RecordSpend(BigDecimal.ZERO)));
    assertThrows(
        IllegalArgumentException.class,
        () -> service.spend(b.id(), new RecordSpend(new BigDecimal("-5.00"))));
  }

  @Test
  void spendByCategoryAggregates() {
    service.recordTransaction(
        new RecordTransaction("a1", "FOOD", new BigDecimal("10.00"), "USD", "Store A"));
    service.recordTransaction(
        new RecordTransaction("a2", "FOOD", new BigDecimal("15.00"), "USD", "Store B"));
    service.recordTransaction(
        new RecordTransaction("a1", "RENT", new BigDecimal("100.00"), "USD", "Landlord"));
    Map<String, BigDecimal> totals = service.spendByCategory();
    assertEquals(new BigDecimal("25.00"), totals.get("FOOD"));
    assertEquals(new BigDecimal("100.00"), totals.get("RENT"));
    assertEquals(2, totals.size());
  }

  @Test
  void nonPositiveTransactionRejected() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            service.recordTransaction(
                new RecordTransaction("a1", "FOOD", BigDecimal.ZERO, "USD", "Store A")));
  }
}