package com.aegis.billpay;

import com.aegis.billpay.command.AddBill;
import com.aegis.billpay.domain.Bill;
import com.aegis.billpay.domain.Money;
import com.aegis.billpay.error.BillNotFound;
import com.aegis.billpay.web.BillView;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import org.springframework.stereotype.Service;

/** Bill-pay application service. Per-bill lock serializes status transitions. */
@Service
public class BillService {

  private final Map<UUID, Bill> store = new ConcurrentHashMap<>();
  private final Map<UUID, ReentrantLock> locks = new ConcurrentHashMap<>();

  public BillView add(AddBill req) {
    Bill bill =
        new Bill(
            UUID.randomUUID(),
            req.customerId(),
            req.payee(),
            Money.of(req.amount(), req.currency()),
            req.dueDate());
    store.put(bill.id(), bill);
    return BillView.from(bill);
  }

  public BillView get(UUID id) {
    return BillView.from(require(id));
  }

  public List<BillView> list() {
    List<BillView> out = new ArrayList<>(store.size());
    for (Bill b : store.values()) {
      out.add(BillView.from(b));
    }
    return out;
  }

  public BillView enableAutopay(UUID id) {
    return mutate(id, Bill::enableAutopay);
  }

  public BillView pay(UUID id) {
    return mutate(id, Bill::pay);
  }

  public BillView cancel(UUID id) {
    return mutate(id, Bill::cancel);
  }

  /** List bills overdue as of today (reminders / alerts feature). */
  public List<BillView> overdue() {
    LocalDate today = LocalDate.now();
    List<BillView> out = new ArrayList<>();
    for (Bill b : store.values()) {
      if (b.isOverdue(today)) {
        out.add(BillView.from(b));
      }
    }
    return out;
  }

  private BillView mutate(UUID id, Consumer<Bill> action) {
    ReentrantLock lock = lock(id);
    lock.lock();
    try {
      Bill bill = require(id);
      action.accept(bill);
      return BillView.from(bill);
    } finally {
      lock.unlock();
    }
  }

  private Bill require(UUID id) {
    Bill bill = store.get(id);
    if (bill == null) {
      throw new BillNotFound(id);
    }
    return bill;
  }

  private ReentrantLock lock(UUID id) {
    return locks.computeIfAbsent(id, k -> new ReentrantLock());
  }
}