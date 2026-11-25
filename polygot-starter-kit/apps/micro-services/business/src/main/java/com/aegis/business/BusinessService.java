package com.aegis.business;

import com.aegis.business.command.CreateAccount;
import com.aegis.business.command.CreateInvoice;
import com.aegis.business.domain.BusinessAccount;
import com.aegis.business.domain.Invoice;
import com.aegis.business.domain.Money;
import com.aegis.business.error.BusinessAccountNotFound;
import com.aegis.business.error.InvoiceNotFound;
import com.aegis.business.web.BusinessAccountView;
import com.aegis.business.web.InvoiceView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import org.springframework.stereotype.Service;

/**
 * Business banking application service. Per-invoice {@link ReentrantLock} serializes invoice
 * mutations; invoices are validated against an existing business account on creation.
 */
@Service
public class BusinessService {

  private final Map<UUID, BusinessAccount> accounts = new ConcurrentHashMap<>();
  private final Map<UUID, Invoice> invoices = new ConcurrentHashMap<>();
  private final Map<UUID, ReentrantLock> locks = new ConcurrentHashMap<>();

  public BusinessAccountView createAccount(CreateAccount req) {
    BusinessAccount a = new BusinessAccount(UUID.randomUUID(), req.name(), req.taxId());
    accounts.put(a.id(), a);
    return BusinessAccountView.from(a);
  }

  public BusinessAccountView getAccount(UUID id) {
    BusinessAccount a = accounts.get(id);
    if (a == null) {
      throw new BusinessAccountNotFound(id);
    }
    return BusinessAccountView.from(a);
  }

  public List<BusinessAccountView> listAccounts() {
    List<BusinessAccountView> out = new ArrayList<>(accounts.size());
    for (BusinessAccount a : accounts.values()) {
      out.add(BusinessAccountView.from(a));
    }
    return out;
  }

  public InvoiceView createInvoice(CreateInvoice req) {
    if (!accounts.containsKey(req.accountId())) {
      throw new BusinessAccountNotFound(req.accountId());
    }
    Invoice i =
        new Invoice(
            UUID.randomUUID(),
            req.accountId(),
            Money.of(req.amount(), req.currency()),
            req.dueDate());
    invoices.put(i.id(), i);
    return InvoiceView.from(i);
  }

  public InvoiceView getInvoice(UUID id) {
    return InvoiceView.from(requireInvoice(id));
  }

  public List<InvoiceView> listInvoices() {
    List<InvoiceView> out = new ArrayList<>(invoices.size());
    for (Invoice i : invoices.values()) {
      out.add(InvoiceView.from(i));
    }
    return out;
  }

  public InvoiceView markPaid(UUID id) {
    return mutate(id, Invoice::markPaid);
  }

  public InvoiceView markOverdue(UUID id) {
    return mutate(id, Invoice::markOverdue);
  }

  public InvoiceView voidInvoice(UUID id) {
    return mutate(id, Invoice::voidInvoice);
  }

  private InvoiceView mutate(UUID id, Consumer<Invoice> action) {
    ReentrantLock lock = lock(id);
    lock.lock();
    try {
      Invoice invoice = requireInvoice(id);
      action.accept(invoice);
      return InvoiceView.from(invoice);
    } finally {
      lock.unlock();
    }
  }

  private Invoice requireInvoice(UUID id) {
    Invoice invoice = invoices.get(id);
    if (invoice == null) {
      throw new InvoiceNotFound(id);
    }
    return invoice;
  }

  private ReentrantLock lock(UUID id) {
    return locks.computeIfAbsent(id, k -> new ReentrantLock());
  }
}