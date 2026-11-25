package com.aegis.credit.web;

import com.aegis.credit.domain.CreditBand;
import com.aegis.credit.domain.CreditProfile;
import java.util.UUID;

/** Immutable snapshot of a credit profile for API responses; never exposes the mutable entity. */
public record CreditProfileView(
    UUID id, String customerId, int score, CreditBand band, long version) {

  public static CreditProfileView from(CreditProfile p) {
    return new CreditProfileView(p.id(), p.customerId(), p.score(), p.band(), p.version());
  }
}