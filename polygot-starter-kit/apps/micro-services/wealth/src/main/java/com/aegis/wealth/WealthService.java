package com.aegis.wealth;
import java.math.BigDecimal; import java.util.*; import java.util.concurrent.ConcurrentHashMap; import org.springframework.stereotype.Service;
@Service
public class WealthService {
  private final Map<UUID,Goal> store = new ConcurrentHashMap<>();
  public Goal create(NewGoal r){ Goal g=new Goal(UUID.randomUUID(), r.customerId(), r.name(), r.target(), BigDecimal.ZERO, r.currency()); store.put(g.id(),g); return g; }
  public Goal contribute(UUID id, BigDecimal amt){ Goal g=store.get(id); if(g==null) return null; Goal n=new Goal(g.id(),g.customerId(),g.name(),g.target(),g.saved().add(amt),g.currency()); store.put(id,n); return n; }
  public Optional<Goal> get(UUID id){ return Optional.ofNullable(store.get(id)); }
  public List<Goal> list(){ return new ArrayList<>(store.values()); }
}
