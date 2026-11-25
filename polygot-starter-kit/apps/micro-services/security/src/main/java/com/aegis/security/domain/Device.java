package com.aegis.security.domain;

import com.aegis.security.error.InvalidTransition;
import java.util.Objects;
import java.util.UUID;

/**
 * Device aggregate root: owns its trust lifecycle.
 *
 * <p>All mutations go through methods that enforce the state machine. The entity is not thread-safe
 * on its own; the service serializes access with a per-device lock.
 */
public final class Device {

  private final UUID id;
  private final String customerId;
  private final String fingerprint;

  private DeviceStatus status;

  public Device(UUID id, String customerId, String fingerprint) {
    this.id = Objects.requireNonNull(id, "id must not be null");
    this.customerId = requireText(customerId, "customerId");
    this.fingerprint = requireText(fingerprint, "fingerprint");
    this.status = DeviceStatus.PENDING;
  }

  public UUID id() {
    return id;
  }

  public String customerId() {
    return customerId;
  }

  public String fingerprint() {
    return fingerprint;
  }

  public DeviceStatus status() {
    return status;
  }

  /** Convenience projection: a device is trusted only while in {@link DeviceStatus#TRUSTED}. */
  public boolean trusted() {
    return status == DeviceStatus.TRUSTED;
  }

  /** Trust this device: {@code PENDING -> TRUSTED}. */
  public void trust() {
    transition(DeviceStatus.TRUSTED);
  }

  /** Revoke this device: {@code PENDING|TRUSTED -> REVOKED}. */
  public void revoke() {
    transition(DeviceStatus.REVOKED);
  }

  private void transition(DeviceStatus target) {
    if (!status.canTransitionTo(target)) {
      throw new InvalidTransition("device", id, status.name(), target.name());
    }
    this.status = target;
  }

  private static String requireText(String value, String field) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(field + " must not be blank");
    }
    return value;
  }
}