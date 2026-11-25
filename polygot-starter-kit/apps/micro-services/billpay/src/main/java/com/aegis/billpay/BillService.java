package com.aegis.billpay;
import java.util.*; import java.util.concurrent.ConcurrentHashMap; import org.springframework.stereotype.Service;
@Service
public class BillService {
  private final Map<UUID,Bill> store = new ConcurrentHashMap<>();
  public Bill add(NewBill r){ Bill b=new Bill(UUID.randomUUID(), r.customerId(), r.payee(), r.amount(), r.dueDate(), "SCHEDULED"); store.put(b.id(),b); return b; }
  public Optional<Bill> get(UUID id){ return Optional.ofNullable(store.get(id)); }
  public List<Bill> list(){ return new ArrayList<>(store.values()); }
}
