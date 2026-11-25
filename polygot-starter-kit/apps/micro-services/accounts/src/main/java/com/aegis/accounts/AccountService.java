package com.aegis.accounts;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
  private final Map<UUID, Account> store = new ConcurrentHashMap<>();

  public Account open(NewAccount req) {
    Account a =
        new Account(
            UUID.randomUUID(),
            req.customerId(),
            req.type(),
            BigDecimal.ZERO,
            req.currency(),
            "ACTIVE");
    store.put(a.id(), a);
    return a;
  }

  public Optional<Account> get(UUID id) {
    return Optional.ofNullable(store.get(id));
  }

  public List<Account> list() {
    return new ArrayList<>(store.values());
  }
}
