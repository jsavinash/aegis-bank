package com.aegis.treasury;

import com.aegis.treasury.command.Credit;
import com.aegis.treasury.command.Debit;
import com.aegis.treasury.command.SetPosition;
import com.aegis.treasury.domain.CashPosition;
import com.aegis.treasury.domain.Forecast;
import com.aegis.treasury.domain.Money;
import com.aegis.treasury.error.CashPositionNotFound;
import com.aegis.treasury.web.CashPositionView;
import com.aegis.treasury.web.ForecastView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import org.springframework.stereotype.Service;

/**
 * Treasury application service.
 *
 * <p>Concurrency: a per-account {@link ReentrantLock} serializes all mutations to one cash position, so
 * concurrent credits/debits can never interleave and drive the balance negative (mirrors the reference
 * implementation's per-entity lock). The store is keyed by account id, which is also the position id.
 */
@Service
public class TreasuryService {

  private final Map<UUID, CashPosition> store = new ConcurrentHashMap<>();
  private final Map<UUID, ReentrantLock> locks = new ConcurrentHashMap<>();

  /** Create or replace the cash position for an account (upsert). */
  public CashPositionView setPosition(SetPosition req) {
    UUID accountId = req.accountId();
    ReentrantLock lock = lock(accountId);
    lock.lock();
    try {
      Money next = Money.of(req.balance(), req.currency());
      CashPosition position = store.get(accountId);
      if (position == null) {
        position = new CashPosition(accountId, next);
        store.put(accountId, position);
      } else {
        position.setBalance(next);
      }
      return CashPositionView.from(position);
    } finally {
      lock.unlock();
    }
  }

  public CashPositionView get(UUID accountId) {
    return CashPositionView.from(require(accountId));
  }

  public List<CashPositionView> all() {
    List<CashPositionView> out = new ArrayList<>(store.size());
    for (CashPosition p : store.values()) {
      out.add(CashPositionView.from(p));
    }
    return out;
  }

  public CashPositionView credit(UUID accountId, Credit req) {
    return mutate(accountId, position -> position.credit(money(position, req.amount())));
  }

  public CashPositionView debit(UUID accountId, Debit req) {
    return mutate(accountId, position -> position.debit(money(position, req.amount())));
  }

  /** Deterministic linear projection of the position balance over {@code weeks} periods. */
  public ForecastView forecast(UUID accountId, int weeks) {
    CashPosition position = require(accountId);
    return new ForecastView(weeks, Forecast.project(position, weeks));
  }

  private CashPositionView mutate(UUID accountId, Consumer<CashPosition> action) {
    ReentrantLock lock = lock(accountId);
    lock.lock();
    try {
      CashPosition position = require(accountId);
      action.accept(position);
      return CashPositionView.from(position);
    } finally {
      lock.unlock();
    }
  }

  private Money money(CashPosition position, java.math.BigDecimal amount) {
    return Money.of(amount, position.currency());
  }

  private CashPosition require(UUID accountId) {
    CashPosition position = store.get(accountId);
    if (position == null) {
      throw new CashPositionNotFound(accountId);
    }
    return position;
  }

  private ReentrantLock lock(UUID accountId) {
    return locks.computeIfAbsent(accountId, k -> new ReentrantLock());
  }
}