package com.aegis.business;

import static org.junit.jupiter.api.Assertions.*;

import com.aegis.business.command.CreateAccount;
import com.aegis.business.command.CreateInvoice;
import com.aegis.business.domain.InvoiceStatus;
import com.aegis.business.error.BusinessAccountNotFound;
import com.aegis.business.error.InvalidInvoiceTransition;
import com.aegis.business.web.BusinessAccountView;
import com.aegis.business.web.InvoiceView;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class BusinessServiceTest {

  private final BusinessService service = new BusinessService();

  private BusinessAccountView account() {
    return service.createAccount(new CreateAccount("ACME", "T1"));
  }

  private InvoiceView invoice(UUID accountId) {
    return service.createInvoice(
        new CreateInvoice(accountId, new BigDecimal("100.00"), "USD", LocalDate.now().plusDays(7)));
  }

  @Test
  void createAccountAndInvoice() {
    BusinessAccountView a = account();
    InvoiceView i = invoice(a.id());
    assertEquals(InvoiceStatus.OPEN, i.status());
  }

  @Test
  void invoiceRequiresExistingAccount() {
    assertThrows(
        BusinessAccountNotFound.class,
        () -> service.createInvoice(
            new CreateInvoice(UUID.randomUUID(), BigDecimal.ONE, "USD", LocalDate.now())));
  }

  @Test
  void invoiceLifecycle() {
    BusinessAccountView a = account();
    InvoiceView i = invoice(a.id());
    assertEquals(InvoiceStatus.OVERDUE, service.markOverdue(i.id()).status());
    assertEquals(InvoiceStatus.PAID, service.markPaid(i.id()).status());
  }

  @Test
  void paidInvoiceIsTerminal() {
    BusinessAccountView a = account();
    InvoiceView i = invoice(a.id());
    service.markPaid(i.id());
    assertThrows(InvalidInvoiceTransition.class, () -> service.markOverdue(i.id()));
  }

  @Test
  void voidFromOpenIsAllowed() {
    BusinessAccountView a = account();
    InvoiceView i = invoice(a.id());
    assertEquals(InvoiceStatus.VOID, service.voidInvoice(i.id()).status());
  }
}