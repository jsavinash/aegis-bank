package com.aegis.credit;

import com.aegis.credit.command.Simulate;
import com.aegis.credit.command.UpsertProfile;
import com.aegis.credit.domain.CreditProfile;
import com.aegis.credit.error.CreditProfileNotFound;
import com.aegis.credit.web.CreditProfileView;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.stereotype.Service;

/**
 * Credit application service.
 *
 * <p>Concurrency: a per-customer {@link ReentrantLock} serializes all mutations to one customer's
 * profile, so concurrent upserts can never interleave (mirrors the reference implementation's
 * per-entity lock). An index maps {@code customerId -> id} so an upsert replaces the existing
 * profile in place rather than creating duplicates.
 */
@Service
public class CreditService {

  private final Map<UUID, CreditProfile> store = new ConcurrentHashMap<>();
  private final Map<String, UUID> index = new ConcurrentHashMap<>();
  private final Map<String, ReentrantLock> locks = new ConcurrentHashMap<>();

  public CreditProfileView upsert(UpsertProfile req) {
    return upsert(req.customerId(), req.score());
  }

  public CreditProfileView upsert(String customerId, int score) {
    ReentrantLock lock = lock(customerId);
    lock.lock();
    try {
      UUID existingId = index.get(customerId);
      CreditProfile current = existingId == null ? null : store.get(existingId);
      CreditProfile profile;
      if (current != null) {
        profile =
            new CreditProfile(
                current.id(),
                customerId,
                score,
                null,
                current.version() + 1,
                Instant.now());
        store.put(profile.id(), profile);
      } else {
        UUID id = UUID.randomUUID();
        profile = new CreditProfile(id, customerId, score, null, 0L, Instant.now());
        store.put(id, profile);
        index.put(customerId, id);
      }
      return CreditProfileView.from(profile);
    } finally {
      lock.unlock();
    }
  }

  public CreditProfileView get(UUID id) {
    return CreditProfileView.from(require(id));
  }

  public List<CreditProfileView> list() {
    List<CreditProfileView> out = new ArrayList<>(store.size());
    for (CreditProfile p : store.values()) {
      out.add(CreditProfileView.from(p));
    }
    return out;
  }

  public Optional<CreditProfileView> getByCustomer(String customerId) {
    UUID id = index.get(customerId);
    CreditProfile profile = id == null ? null : store.get(id);
    return Optional.ofNullable(profile).map(CreditProfileView::from);
  }

  /** Pure what-if simulation: applies {@code delta} to {@code baseScore} and clamps to 300..850. */
  public int simulate(int baseScore, int delta) {
    long raw = (long) baseScore + delta;
    return (int) Math.max(CreditProfile.MIN_SCORE, Math.min(CreditProfile.MAX_SCORE, raw));
  }

  public int simulate(Simulate req) {
    return simulate(req.baseScore(), req.delta());
  }

  private CreditProfile require(UUID id) {
    CreditProfile profile = store.get(id);
    if (profile == null) {
      throw new CreditProfileNotFound(id);
    }
    return profile;
  }

  private ReentrantLock lock(String customerId) {
    return locks.computeIfAbsent(customerId, k -> new ReentrantLock());
  }
}