package com.aegis.business.web;

import com.aegis.business.domain.BusinessAccount;
import java.time.Instant;
import java.util.UUID;

/** Immutable snapshot of a business account for API responses. */
public record BusinessAccountView(UUID id, String name, String taxId, Instant createdAt) {

  public static BusinessAccountView from(BusinessAccount a) {
    return new BusinessAccountView(a.id(), a.name(), a.taxId(), a.createdAt());
  }
}