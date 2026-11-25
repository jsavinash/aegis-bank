package com.aegis.payments;

import com.aegis.payments.command.InitiatePayment;
import com.aegis.payments.domain.Money;
import com.aegis.payments.domain.Payment;
import com.aegis.payments.domain.PaymentType;
import com.aegis.payments.error.PaymentNotFound;
import com.aegis.payments.web.PaymentView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import org.springframework.stereotype.Service;

/**
 * Payment application service.
 *
 * <p>Idempotency: a repeated {@code idempotencyKey} returns the original payment without creating a
 * second one (checked under the per-payment lock). Concurrency: per-payment {@link ReentrantLock}
 * serializes status transitions. Status changes follow the {@link Payment} state machine.
 */
@Service
public class PaymentService {

  private final Map<UUID, Payment> store = new ConcurrentHashMap<>();
  private final Map<String, UUID> idempotency = new ConcurrentHashMap<>();
  private final Map<UUID, ReentrantLock> locks = new ConcurrentHashMap<>();
  private final Map<String, ReentrantLock> keyLocks = new ConcurrentHashMap<>();

  public PaymentView initiate(InitiatePayment req) {
    ReentrantLock keyLock =
        keyLocks.computeIfAbsent(req.idempotencyKey(), k -> new ReentrantLock());
    keyLock.lock();
    try {
      UUID existing = idempotency.get(req.idempotencyKey());
      if (existing != null) {
        return PaymentView.from(store.get(existing));
      }
      Payment p =
          new Payment(
              UUID.randomUUID(),
              req.idempotencyKey(),
              req.fromAccount(),
              req.toAccount(),
              Money.of(req.amount(), req.currency()),
              parseType(req.type()));
      store.put(p.id(), p);
      idempotency.putIfAbsent(req.idempotencyKey(), p.id());
      return PaymentView.from(p);
    } finally {
      keyLock.unlock();
    }
  }

  public PaymentView get(UUID id) {
    return PaymentView.from(require(id));
  }

  public List<PaymentView> list() {
    List<PaymentView> out = new ArrayList<>(store.size());
    for (Payment p : store.values()) {
      out.add(PaymentView.from(p));
    }
    return out;
  }

  public PaymentView process(UUID id) {
    return mutate(id, Payment::markProcessing);
  }

  public PaymentView complete(UUID id) {
    return mutate(id, Payment::complete);
  }

  public PaymentView fail(UUID id) {
    return mutate(id, Payment::fail);
  }

  public PaymentView cancel(UUID id) {
    return mutate(id, Payment::cancel);
  }

  public PaymentView recall(UUID id) {
    return mutate(id, Payment::recall);
  }

  private PaymentView mutate(UUID id, Consumer<Payment> action) {
    ReentrantLock lock = lock(id);
    lock.lock();
    try {
      Payment p = require(id);
      action.accept(p);
      return PaymentView.from(p);
    } finally {
      lock.unlock();
    }
  }

  private Payment require(UUID id) {
    Payment p = store.get(id);
    if (p == null) {
      throw new PaymentNotFound(id);
    }
    return p;
  }

  private ReentrantLock lock(UUID id) {
    return locks.computeIfAbsent(id, k -> new ReentrantLock());
  }

  private PaymentType parseType(String raw) {
    try {
      return PaymentType.valueOf(raw.trim().toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("unknown payment type: " + raw, e);
    }
  }
}