package com.aegis.wealth;

import static org.junit.jupiter.api.Assertions.*;

import com.aegis.wealth.command.Contribute;
import com.aegis.wealth.command.CreateGoal;
import com.aegis.wealth.domain.GoalStatus;
import com.aegis.wealth.error.GoalNotFound;
import com.aegis.wealth.error.InvalidGoalTransition;
import com.aegis.wealth.web.GoalView;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class WealthServiceTest {

  private final WealthService service = new WealthService();

  private GoalView create() {
    return service.createGoal(new CreateGoal("c1", "Vacation", new BigDecimal("1000.00"), "USD"));
  }

  @Test
  void contributeReachingTargetCompletesGoal() {
    GoalView goal = create();
    assertEquals(GoalStatus.ACTIVE, goal.status());
    assertEquals(new BigDecimal("0.00"), goal.saved().amount());

    GoalView after = service.contribute(goal.id(), new Contribute(new BigDecimal("1000.00")));
    assertEquals(GoalStatus.COMPLETED, after.status());
    assertEquals(new BigDecimal("1000.00"), after.saved().amount());
  }

  @Test
  void partialContributeKeepsGoalActive() {
    GoalView goal = create();
    GoalView after = service.contribute(goal.id(), new Contribute(new BigDecimal("250.00")));
    assertEquals(GoalStatus.ACTIVE, after.status());
    assertEquals(new BigDecimal("250.00"), after.saved().amount());
  }

  @Test
  void contributeAfterCompleteThrows() {
    GoalView goal = create();
    service.contribute(goal.id(), new Contribute(new BigDecimal("1000.00")));
    assertThrows(
        InvalidGoalTransition.class,
        () -> service.contribute(goal.id(), new Contribute(new BigDecimal("10.00"))));
  }

  @Test
  void contributeAfterCancelThrows() {
    GoalView goal = create();
    service.cancelGoal(goal.id());
    assertThrows(
        InvalidGoalTransition.class,
        () -> service.contribute(goal.id(), new Contribute(new BigDecimal("10.00"))));
  }

  @Test
  void getUnknownGoalThrowsNotFound() {
    assertThrows(GoalNotFound.class, () -> service.getGoal(UUID.randomUUID()));
  }

  @Test
  void contributeUnknownGoalThrowsNotFound() {
    assertThrows(
        GoalNotFound.class,
        () -> service.contribute(UUID.randomUUID(), new Contribute(new BigDecimal("10.00"))));
  }

  @Test
  void nonPositiveContributeRejected() {
    GoalView goal = create();
    assertThrows(
        IllegalArgumentException.class,
        () -> service.contribute(goal.id(), new Contribute(new BigDecimal("0.00"))));
    assertThrows(
        IllegalArgumentException.class,
        () -> service.contribute(goal.id(), new Contribute(new BigDecimal("-5.00"))));
  }

  @Test
  void cancelMovesGoalToCancelled() {
    GoalView goal = create();
    GoalView after = service.cancelGoal(goal.id());
    assertEquals(GoalStatus.CANCELLED, after.status());
  }

  @Test
  void portfolioAccumulatesHoldingsAndTotalsValue() {
    var portfolio =
        service.createPortfolio(new com.aegis.wealth.command.CreatePortfolio("c1", "USD"));
    service.addHolding(
        portfolio.id(), new com.aegis.wealth.command.AddHolding("AAPL", new BigDecimal("100.00")));
    var after =
        service.addHolding(
            portfolio.id(), new com.aegis.wealth.command.AddHolding("AAPL", new BigDecimal("50.00")));
    assertEquals(new BigDecimal("150.00"), after.holdings().get("AAPL").amount());
    assertEquals(new BigDecimal("150.00"), after.value().amount());
  }
}