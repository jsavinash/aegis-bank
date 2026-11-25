package com.aegis.wealth;

import com.aegis.wealth.command.AddHolding;
import com.aegis.wealth.command.Contribute;
import com.aegis.wealth.command.CreateGoal;
import com.aegis.wealth.command.CreatePortfolio;
import com.aegis.wealth.domain.Money;
import com.aegis.wealth.domain.Portfolio;
import com.aegis.wealth.domain.SavingsGoal;
import com.aegis.wealth.error.GoalNotFound;
import com.aegis.wealth.error.PortfolioNotFound;
import com.aegis.wealth.web.GoalView;
import com.aegis.wealth.web.PortfolioView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import org.springframework.stereotype.Service;

/**
 * Wealth application service.
 *
 * <p>Concurrency: a per-entity {@link ReentrantLock} serializes all mutations to one goal or portfolio,
 * so concurrent contributions can never interleave (mirrors the reference implementation's per-entity
 * lock). Storage is in-memory; {@code version} on each entity supports optimistic concurrency checks at
 * the persistence boundary.
 */
@Service
public class WealthService {

  private final Map<UUID, SavingsGoal> goals = new ConcurrentHashMap<>();
  private final Map<UUID, ReentrantLock> goalLocks = new ConcurrentHashMap<>();
  private final Map<UUID, Portfolio> portfolios = new ConcurrentHashMap<>();
  private final Map<UUID, ReentrantLock> portfolioLocks = new ConcurrentHashMap<>();

  public GoalView createGoal(CreateGoal req) {
    Money target = Money.of(req.target(), req.currency());
    SavingsGoal goal =
        new SavingsGoal(UUID.randomUUID(), req.customerId(), req.name(), target, target.currency());
    goals.put(goal.id(), goal);
    return GoalView.from(goal);
  }

  public GoalView getGoal(UUID id) {
    return GoalView.from(requireGoal(id));
  }

  public List<GoalView> listGoals() {
    List<GoalView> out = new ArrayList<>(goals.size());
    for (SavingsGoal g : goals.values()) {
      out.add(GoalView.from(g));
    }
    return out;
  }

  public GoalView contribute(UUID id, Contribute req) {
    return mutateGoal(id, goal -> goal.contribute(Money.of(req.amount(), goal.currency())));
  }

  public GoalView cancelGoal(UUID id) {
    return mutateGoal(id, SavingsGoal::cancel);
  }

  public PortfolioView createPortfolio(CreatePortfolio req) {
    Portfolio portfolio = new Portfolio(UUID.randomUUID(), req.customerId(), req.currency());
    portfolios.put(portfolio.id(), portfolio);
    return PortfolioView.from(portfolio);
  }

  public PortfolioView getPortfolio(UUID id) {
    return PortfolioView.from(requirePortfolio(id));
  }

  public List<PortfolioView> listPortfolios() {
    List<PortfolioView> out = new ArrayList<>(portfolios.size());
    for (Portfolio p : portfolios.values()) {
      out.add(PortfolioView.from(p));
    }
    return out;
  }

  public PortfolioView addHolding(UUID id, AddHolding req) {
    return mutatePortfolio(
        id, portfolio -> portfolio.addHolding(req.symbol(), Money.of(req.value(), portfolio.currency())));
  }

  /**
   * Runs a goal mutation under the goal's lock. The action mutates the goal in place; we then snapshot
   * it to an immutable view.
   */
  private GoalView mutateGoal(UUID id, Consumer<SavingsGoal> action) {
    ReentrantLock lock = lock(goalLocks, id);
    lock.lock();
    try {
      SavingsGoal goal = requireGoal(id);
      action.accept(goal);
      return GoalView.from(goal);
    } finally {
      lock.unlock();
    }
  }

  /** Runs a portfolio mutation under the portfolio's lock. */
  private PortfolioView mutatePortfolio(UUID id, Consumer<Portfolio> action) {
    ReentrantLock lock = lock(portfolioLocks, id);
    lock.lock();
    try {
      Portfolio portfolio = requirePortfolio(id);
      action.accept(portfolio);
      return PortfolioView.from(portfolio);
    } finally {
      lock.unlock();
    }
  }

  private SavingsGoal requireGoal(UUID id) {
    SavingsGoal goal = goals.get(id);
    if (goal == null) {
      throw new GoalNotFound(id);
    }
    return goal;
  }

  private Portfolio requirePortfolio(UUID id) {
    Portfolio portfolio = portfolios.get(id);
    if (portfolio == null) {
      throw new PortfolioNotFound(id);
    }
    return portfolio;
  }

  private static ReentrantLock lock(Map<UUID, ReentrantLock> locks, UUID id) {
    return locks.computeIfAbsent(id, k -> new ReentrantLock());
  }
}