package com.aegis.accounts;

import com.aegis.accounts.command.MoveMoney;
import com.aegis.accounts.command.OpenAccount;
import com.aegis.accounts.command.StatusChange;
import com.aegis.accounts.domain.Account;
import com.aegis.accounts.domain.AccountType;
import com.aegis.accounts.domain.LedgerEntry;
import com.aegis.accounts.domain.Money;
import com.aegis.accounts.error.AccountNotFound;
import com.aegis.accounts.web.AccountView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import org.springframework.stereotype.Service;

/**
 * Account application service.
 *
 * <p>Concurrency: a per-account {@link ReentrantLock} serializes all mutations to one account, so
 * concurrent deposits/withdrawals can never interleave and drive the balance negative (mirrors the
 * reference implementation's per-entity lock). Idempotency: every mutating command may carry a key;
 * the first result is cached and replays are returned without re-posting to the ledger.
 */
@Service
public class AccountService {

  private final Map<UUID, Account> store = new ConcurrentHashMap<>();
  private final Map<UUID, ReentrantLock> locks = new ConcurrentHashMap<>();
  private final Map<String, AccountView> idempotency = new ConcurrentHashMap<>();

  public AccountView open(OpenAccount req) {
    AccountType type = parseType(req.type());
    Money initial = Money.zero(req.currency());
    Account account = new Account(UUID.randomUUID(), req.customerId(), type, initial.currency());
    store.put(account.id(), account);
    return AccountView.from(account);
  }

  public AccountView get(UUID id) {
    return AccountView.from(require(id));
  }

  public List<AccountView> list() {
    List<AccountView> out = new ArrayList<>(store.size());
    for (Account a : store.values()) {
      out.add(AccountView.from(a));
    }
    return out;
  }

  public AccountView deposit(UUID id, MoveMoney req) {
    return mutate(id, req.idempotencyKey(), account -> account.deposit(money(account, req), req.idempotencyKey()));
  }

  public AccountView withdraw(UUID id, MoveMoney req) {
    return mutate(id, req.idempotencyKey(), account -> account.withdraw(money(account, req), req.idempotencyKey()));
  }

  public AccountView freeze(UUID id, StatusChange req) {
    return mutate(id, req.idempotencyKey(), Account::freeze);
  }

  public AccountView unfreeze(UUID id, StatusChange req) {
    return mutate(id, req.idempotencyKey(), Account::unfreeze);
  }

  public AccountView close(UUID id, StatusChange req) {
    return mutate(id, req.idempotencyKey(), Account::close);
  }

  public List<LedgerEntry> ledger(UUID id) {
    return require(id).ledger();
  }

  /**
   * Runs a mutation under the account's lock with idempotency. The action mutates the account in
   * place; we then snapshot it to an immutable view. A cached view short-circuits replays.
   */
  private AccountView mutate(UUID id, String idempotencyKey, Consumer<Account> action) {
    String cacheKey = idempotencyKey == null ? null : "acct:" + id + ":" + idempotencyKey;
    ReentrantLock lock = lock(id);
    lock.lock();
    try {
      if (cacheKey != null) {
        AccountView cached = idempotency.get(cacheKey);
        if (cached != null) {
          return cached;
        }
      }
      Account account = require(id);
      action.accept(account);
      AccountView view = AccountView.from(account);
      if (cacheKey != null) {
        idempotency.putIfAbsent(cacheKey, view);
      }
      return view;
    } finally {
      lock.unlock();
    }
  }

  private Money money(Account account, MoveMoney req) {
    return Money.of(req.amount(), account.currency());
  }

  private Account require(UUID id) {
    Account account = store.get(id);
    if (account == null) {
      throw new AccountNotFound(id);
    }
    return account;
  }

  private ReentrantLock lock(UUID id) {
    return locks.computeIfAbsent(id, k -> new ReentrantLock());
  }

  private AccountType parseType(String raw) {
    if (raw == null) {
      throw new IllegalArgumentException("type must not be null");
    }
    try {
      return AccountType.valueOf(raw.trim().toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("unknown account type: " + raw, e);
    }
  }
}