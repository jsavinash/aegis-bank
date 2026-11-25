package com.aegis.lending;

import com.aegis.lending.command.ApplyLoan;
import com.aegis.lending.domain.Loan;
import com.aegis.lending.domain.LoanType;
import com.aegis.lending.domain.Money;
import com.aegis.lending.error.LoanNotFound;
import com.aegis.lending.web.LoanView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import org.springframework.stereotype.Service;

/**
 * Loan application service.
 *
 * <p>Concurrency: a per-loan {@link ReentrantLock} serializes all mutations to one loan, so concurrent
 * transitions can never interleave (mirrors the reference implementation's per-entity lock).
 * Idempotency: {@code apply} is keyed by a request key derived from the command, so a repeated
 * application returns the original loan instead of creating a second one.
 */
@Service
public class LoanService {

  private final Map<UUID, Loan> store = new ConcurrentHashMap<>();
  private final Map<String, UUID> idempotency = new ConcurrentHashMap<>();
  private final Map<UUID, ReentrantLock> locks = new ConcurrentHashMap<>();
  private final Map<String, ReentrantLock> keyLocks = new ConcurrentHashMap<>();

  public LoanView apply(ApplyLoan req) {
    LoanType type = parseType(req.type());
    Money principal = Money.of(req.principal(), req.currency());
    String requestKey = requestKey(req, principal.currency());
    ReentrantLock keyLock = keyLocks.computeIfAbsent(requestKey, k -> new ReentrantLock());
    keyLock.lock();
    try {
      UUID existing = idempotency.get(requestKey);
      if (existing != null) {
        return LoanView.from(require(existing));
      }
      Loan loan = new Loan(UUID.randomUUID(), req.customerId(), type, principal);
      store.put(loan.id(), loan);
      idempotency.putIfAbsent(requestKey, loan.id());
      return LoanView.from(loan);
    } finally {
      keyLock.unlock();
    }
  }

  public LoanView get(UUID id) {
    return LoanView.from(require(id));
  }

  public List<LoanView> list() {
    List<LoanView> out = new ArrayList<>(store.size());
    for (Loan l : store.values()) {
      out.add(LoanView.from(l));
    }
    return out;
  }

  public LoanView approve(UUID id) {
    return mutate(id, Loan::approve);
  }

  public LoanView decline(UUID id) {
    return mutate(id, Loan::decline);
  }

  public LoanView disburse(UUID id) {
    return mutate(id, Loan::disburse);
  }

  public LoanView activate(UUID id) {
    return mutate(id, Loan::activate);
  }

  public LoanView payOff(UUID id) {
    return mutate(id, Loan::payOff);
  }

  public LoanView defaultLoan(UUID id) {
    return mutate(id, Loan::defaultLoan);
  }

  private LoanView mutate(UUID id, Consumer<Loan> action) {
    ReentrantLock lock = lock(id);
    lock.lock();
    try {
      Loan loan = require(id);
      action.accept(loan);
      return LoanView.from(loan);
    } finally {
      lock.unlock();
    }
  }

  private Loan require(UUID id) {
    Loan loan = store.get(id);
    if (loan == null) {
      throw new LoanNotFound(id);
    }
    return loan;
  }

  private ReentrantLock lock(UUID id) {
    return locks.computeIfAbsent(id, k -> new ReentrantLock());
  }

  private LoanType parseType(String raw) {
    if (raw == null) {
      throw new IllegalArgumentException("type must not be null");
    }
    try {
      return LoanType.valueOf(raw.trim().toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("unknown loan type: " + raw, e);
    }
  }

  private static String requestKey(ApplyLoan req, String currency) {
    return req.customerId()
        + "|"
        + req.type().trim().toUpperCase()
        + "|"
        + req.principal().toPlainString()
        + "|"
        + currency;
  }
}