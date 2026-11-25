package com.aegis.lending;
import java.util.*; import java.util.concurrent.ConcurrentHashMap; import org.springframework.stereotype.Service;
@Service
public class LoanService {
  private final Map<UUID,Loan> store = new ConcurrentHashMap<>();
  public Loan apply(NewLoan r){ Loan l=new Loan(UUID.randomUUID(), r.customerId(), r.principal(), r.currency(), "UNDERWRITING"); store.put(l.id(),l); return l; }
  public Optional<Loan> get(UUID id){ return Optional.ofNullable(store.get(id)); }
  public List<Loan> list(){ return new ArrayList<>(store.values()); }
  public Loan decide(UUID id, String s){ Loan l=store.get(id); if(l==null) return null; Loan n=new Loan(l.id(),l.customerId(),l.principal(),l.currency(),s); store.put(id,n); return n; }
}
