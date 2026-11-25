package com.aegis.business;
import java.math.BigDecimal; import java.util.*; import java.util.concurrent.ConcurrentHashMap; import org.springframework.stereotype.Service;
@Service
public class BusinessService {
  private final Map<UUID,BusinessAccount> accounts = new ConcurrentHashMap<>();
  private final Map<UUID,Invoice> invoices = new ConcurrentHashMap<>();
  public BusinessAccount account(String name, String taxId){ BusinessAccount a=new BusinessAccount(UUID.randomUUID(),name,taxId); accounts.put(a.id(),a); return a; }
  public Invoice invoice(UUID accountId, BigDecimal amount){ Invoice i=new Invoice(UUID.randomUUID(),accountId,amount,"OPEN"); invoices.put(i.id(),i); return i; }
  public List<BusinessAccount> accounts(){ return new ArrayList<>(accounts.values()); }
  public List<Invoice> invoices(){ return new ArrayList<>(invoices.values()); }
}
