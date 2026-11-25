package com.aegis.billpay.web;

import com.aegis.billpay.domain.Bill;
import com.aegis.billpay.domain.BillStatus;
import com.aegis.billpay.domain.Money;
import java.time.LocalDate;
import java.util.UUID;

/** Immutable snapshot of a bill for API responses. */
public record BillView(
    UUID id,
    String customerId,
    String payee,
    Money amount,
    LocalDate dueDate,
    BillStatus status,
    boolean overdue,
    long version) {

  public static BillView from(Bill b) {
    return new BillView(
        b.id(), b.customerId(), b.payee(), b.amount(), b.dueDate(), b.status(),
        b.isOverdue(LocalDate.now()), b.version());
  }
}