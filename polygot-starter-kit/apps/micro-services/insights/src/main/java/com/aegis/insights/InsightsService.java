package com.aegis.insights;
import java.math.BigDecimal; import java.util.*; import java.util.concurrent.ConcurrentHashMap; import org.springframework.stereotype.Service;
@Service
public class InsightsService {
  private final Map<UUID,Budget> store = new ConcurrentHashMap<>();
  public Budget create(NewBudget r){ Budget b=new Budget(UUID.randomUUID(), r.customerId(), r.category(), r.limit(), BigDecimal.ZERO); store.put(b.id(),b); return b; }
  public Budget spend(UUID id, BigDecimal amt){ Budget b=store.get(id); if(b==null) return null; Budget n=new Budget(b.id(),b.customerId(),b.category(),b.limit(),b.spent().add(amt)); store.put(id,n); return n; }
  public Optional<Budget> get(UUID id){ return Optional.ofNullable(store.get(id)); }
  public List<Budget> list(){ return new ArrayList<>(store.values()); }
}
