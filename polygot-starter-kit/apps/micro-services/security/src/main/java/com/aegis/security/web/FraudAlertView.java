package com.aegis.security.web;

import com.aegis.security.domain.AlertStatus;
import com.aegis.security.domain.FraudAlert;
import java.math.BigDecimal;
import java.util.UUID;

/** Immutable snapshot of a fraud alert for API responses; never exposes the mutable entity. */
public record FraudAlertView(
    UUID id,
    String customerId,
    BigDecimal amount,
    double riskScore,
    AlertStatus status) {

  public static FraudAlertView from(FraudAlert a) {
    return new FraudAlertView(a.id(), a.customerId(), a.amount(), a.riskScore(), a.status());
  }
}