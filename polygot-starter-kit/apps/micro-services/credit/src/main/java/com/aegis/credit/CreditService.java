package com.aegis.credit;
import java.util.*; import java.util.concurrent.ConcurrentHashMap; import org.springframework.stereotype.Service;
@Service
public class CreditService {
  private final Map<UUID,CreditProfile> store = new ConcurrentHashMap<>();
  public CreditProfile upsert(String customerId, int score){ UUID id=UUID.randomUUID(); CreditProfile p=new CreditProfile(id,customerId,score,band(score)); store.put(id,p); return p; }
  public Optional<CreditProfile> get(UUID id){ return Optional.ofNullable(store.get(id)); }
  public List<CreditProfile> list(){ return new ArrayList<>(store.values()); }
  public int simulate(int base, int delta){ return Math.max(300, Math.min(850, base+delta)); }
  private String band(int s){ return s>=740?"EXCELLENT":s>=670?"GOOD":s>=580?"FAIR":"POOR"; }
}
