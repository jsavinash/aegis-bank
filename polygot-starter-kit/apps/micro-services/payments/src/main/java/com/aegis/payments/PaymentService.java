package com.aegis.payments;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
  private final Map<UUID, Payment> store = new ConcurrentHashMap<>();
  private final Map<String, UUID> idempotency = new ConcurrentHashMap<>();

  public Payment initiate(NewPayment req) {
    UUID existing = idempotency.get(req.idempotencyKey());
    if (existing != null) {
      return store.get(existing);
    }
    Payment p =
        new Payment(
            UUID.randomUUID(),
            req.idempotencyKey(),
            req.fromAccount(),
            req.toAccount(),
            req.amount(),
            req.currency(),
            req.type(),
            "PENDING");
    store.put(p.id(), p);
    idempotency.put(p.idempotencyKey(), p.id());
    return p;
  }

  public Optional<Payment> get(UUID id) {
    return Optional.ofNullable(store.get(id));
  }

  public List<Payment> list() {
    return new ArrayList<>(store.values());
  }
}
