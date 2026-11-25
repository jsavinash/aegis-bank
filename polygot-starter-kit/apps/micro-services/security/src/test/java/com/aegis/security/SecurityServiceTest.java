package com.aegis.security;

import static org.junit.jupiter.api.Assertions.*;

import com.aegis.security.command.RaiseAlert;
import com.aegis.security.command.RegisterDevice;
import com.aegis.security.domain.AlertStatus;
import com.aegis.security.domain.DeviceStatus;
import com.aegis.security.error.AlertNotFound;
import com.aegis.security.error.DeviceNotFound;
import com.aegis.security.error.InvalidTransition;
import com.aegis.security.web.DeviceView;
import com.aegis.security.web.FraudAlertView;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class SecurityServiceTest {

  private final SecurityService service = new SecurityService();

  private DeviceView register() {
    return service.registerDevice(new RegisterDevice("c1", "fp1"));
  }

  private FraudAlertView raise(String amount) {
    return service.raiseAlert(new RaiseAlert("c1", new BigDecimal(amount)));
  }

  @Test
  void registerStartsPending() {
    DeviceView d = register();
    assertEquals(DeviceStatus.PENDING, d.status());
    assertFalse(d.trusted());
    assertDoesNotThrow(() -> service.getDevice(d.id()));
  }

  @Test
  void trustThenRevoke() {
    DeviceView d = register();
    DeviceView trusted = service.trustDevice(d.id());
    assertEquals(DeviceStatus.TRUSTED, trusted.status());
    assertTrue(trusted.trusted());
    DeviceView revoked = service.revokeDevice(d.id());
    assertEquals(DeviceStatus.REVOKED, revoked.status());
    assertFalse(revoked.trusted());
  }

  @Test
  void revokeFromPendingIsAllowed() {
    DeviceView d = register();
    assertEquals(DeviceStatus.REVOKED, service.revokeDevice(d.id()).status());
  }

  @Test
  void doubleTrustIsRejected() {
    DeviceView d = register();
    service.trustDevice(d.id());
    assertThrows(InvalidTransition.class, () -> service.trustDevice(d.id()));
  }

  @Test
  void revokedIsTerminal() {
    DeviceView d = register();
    service.revokeDevice(d.id());
    assertThrows(InvalidTransition.class, () -> service.trustDevice(d.id()));
    assertThrows(InvalidTransition.class, () -> service.revokeDevice(d.id()));
  }

  @Test
  void unknownDeviceThrowsNotFound() {
    assertThrows(DeviceNotFound.class, () -> service.getDevice(UUID.randomUUID()));
    assertThrows(DeviceNotFound.class, () -> service.trustDevice(UUID.randomUUID()));
    assertThrows(DeviceNotFound.class, () -> service.revokeDevice(UUID.randomUUID()));
  }

  @Test
  void raiseAlertComputesLowRisk() {
    FraudAlertView a = raise("100.00");
    assertEquals(0.1, a.riskScore());
    assertEquals(AlertStatus.OPEN, a.status());
  }

  @Test
  void raiseAlertComputesHighRisk() {
    assertEquals(0.9, raise("10000.01").riskScore());
  }

  @Test
  void thresholdIsStrictlyAboveTenThousand() {
    assertEquals(0.1, raise("10000").riskScore());
  }

  @Test
  void riskScoreIsClamped() {
    FraudAlertView a = service.raiseAlert(new RaiseAlert("c1", new BigDecimal("1.00")));
    assertEquals(0.1, a.riskScore());
    assertTrue(a.riskScore() >= 0.0 && a.riskScore() <= 1.0);
  }

  @Test
  void confirmAlert() {
    FraudAlertView a = raise("50.00");
    assertEquals(AlertStatus.CONFIRMED, service.confirmAlert(a.id()).status());
  }

  @Test
  void dismissAlert() {
    FraudAlertView a = raise("50.00");
    assertEquals(AlertStatus.DISMISSED, service.dismissAlert(a.id()).status());
  }

  @Test
  void confirmedIsTerminal() {
    FraudAlertView a = raise("50.00");
    service.confirmAlert(a.id());
    assertThrows(InvalidTransition.class, () -> service.dismissAlert(a.id()));
  }

  @Test
  void dismissedIsTerminal() {
    FraudAlertView a = raise("50.00");
    service.dismissAlert(a.id());
    assertThrows(InvalidTransition.class, () -> service.confirmAlert(a.id()));
  }

  @Test
  void unknownAlertThrowsNotFound() {
    assertThrows(AlertNotFound.class, () -> service.getAlert(UUID.randomUUID()));
    assertThrows(AlertNotFound.class, () -> service.confirmAlert(UUID.randomUUID()));
    assertThrows(AlertNotFound.class, () -> service.dismissAlert(UUID.randomUUID()));
  }

  @Test
  void negativeAmountIsRejected() {
    assertThrows(IllegalArgumentException.class, () -> raise("-1.00"));
  }

  @Test
  void zeroAmountIsRejected() {
    assertThrows(IllegalArgumentException.class, () -> raise("0.00"));
  }

  @Test
  void freezeAndIsFrozen() {
    assertFalse(service.isFrozen("c9"));
    service.freeze("c9");
    assertTrue(service.isFrozen("c9"));
  }
}