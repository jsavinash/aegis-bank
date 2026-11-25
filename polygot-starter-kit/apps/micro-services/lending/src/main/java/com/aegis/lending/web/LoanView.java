package com.aegis.lending.web;

import com.aegis.lending.domain.Loan;
import com.aegis.lending.domain.LoanStatus;
import com.aegis.lending.domain.LoanType;
import com.aegis.lending.domain.Money;
import java.time.Instant;
import java.util.UUID;

/** Immutable snapshot of a loan for API responses; never exposes the mutable entity. */
public record LoanView(
    UUID id,
    String customerId,
    LoanType type,
    Money principal,
    LoanStatus status,
    long version,
    Instant createdAt) {

  public static LoanView from(Loan l) {
    return new LoanView(
        l.id(), l.customerId(), l.type(), l.principal(), l.status(), l.version(), l.createdAt());
  }
}