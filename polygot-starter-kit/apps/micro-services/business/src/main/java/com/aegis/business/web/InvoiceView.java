package com.aegis.business.web;

import com.aegis.business.domain.Invoice;
import com.aegis.business.domain.InvoiceStatus;
import com.aegis.business.domain.Money;
import java.time.LocalDate;
import java.util.UUID;

/** Immutable snapshot of an invoice for API responses. */
public record InvoiceView(
    UUID id, UUID accountId, Money amount, InvoiceStatus status, LocalDate dueDate, long version) {

  public static InvoiceView from(Invoice i) {
    return new InvoiceView(i.id(), i.accountId(), i.amount(), i.status(), i.dueDate(), i.version());
  }
}