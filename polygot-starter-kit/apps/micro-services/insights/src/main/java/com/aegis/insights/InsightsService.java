package com.aegis.insights;

import com.aegis.insights.command.CreateBudget;
import com.aegis.insights.command.RecordSpend;
import com.aegis.insights.command.RecordTransaction;
import com.aegis.insights.domain.Budget;
import com.aegis.insights.domain.Money;
import com.aegis.insights.domain.Transaction;
import com.aegis.insights.error.BudgetNotFound;
import com.aegis.insights.web.BudgetView;
import com.aegis.insights.web.TransactionView;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.stereotype.Service;

/**
 * Insights application service.
 *
 * <p>Concurrency: a per-budget {@link ReentrantLock} serializes all mutations to one budget, so
 * concurrent spends can never interleave and lose updates (mirrors the reference implementation's
 * per-entity lock). Stores are {@link ConcurrentHashMap}s; reads of immutable views are lock-free.
 */
@Service
public class InsightsService {

  private final Map<UUID, Budget> budgets = new ConcurrentHashMap<>();
  private final Map<UUID, ReentrantLock> locks = new ConcurrentHashMap<>();
  private final Map<UUID, Transaction> transactions = new ConcurrentHashMap<>();

  public BudgetView createBudget(CreateBudget req) {
    Money limit = Money.of(req.limit(), req.currency());
    Budget budget = new Budget(UUID.randomUUID(), req.customerId(), req.category(), limit);
    budgets.put(budget.id(), budget);
    return BudgetView.from(budget);
  }

  public BudgetView getBudget(UUID id) {
    return BudgetView.from(require(id));
  }

  public List<BudgetView> listBudgets() {
    List<BudgetView> out = new ArrayList<>(budgets.size());
    for (Budget b : budgets.values()) {
      out.add(BudgetView.from(b));
    }
    return out;
  }

  /** Records spend under the budget's lock, then snapshots the mutated budget to an immutable view. */
  public BudgetView spend(UUID id, RecordSpend req) {
    ReentrantLock lock = lock(id);
    lock.lock();
    try {
      Budget budget = require(id);
      budget.spend(Money.of(req.amount(), budget.currency()));
      return BudgetView.from(budget);
    } finally {
      lock.unlock();
    }
  }

  public TransactionView recordTransaction(RecordTransaction req) {
    Money amount = Money.of(req.amount(), req.currency());
    Transaction tx =
        new Transaction(
            UUID.randomUUID(),
            req.accountId(),
            req.category(),
            amount,
            req.merchant(),
            Instant.now());
    transactions.put(tx.id(), tx);
    return TransactionView.from(tx);
  }

  public List<TransactionView> listTransactions() {
    List<TransactionView> out = new ArrayList<>(transactions.size());
    for (Transaction t : transactions.values()) {
      out.add(TransactionView.from(t));
    }
    return out;
  }

  /** Aggregates recorded transactions into total spend per category. */
  public Map<String, BigDecimal> spendByCategory() {
    Map<String, BigDecimal> totals = new LinkedHashMap<>();
    for (Transaction t : transactions.values()) {
      totals.merge(t.category(), t.amount().amount(), BigDecimal::add);
    }
    return totals;
  }

  private Budget require(UUID id) {
    Budget budget = budgets.get(id);
    if (budget == null) {
      throw new BudgetNotFound(id);
    }
    return budget;
  }

  private ReentrantLock lock(UUID id) {
    return locks.computeIfAbsent(id, k -> new ReentrantLock());
  }
}