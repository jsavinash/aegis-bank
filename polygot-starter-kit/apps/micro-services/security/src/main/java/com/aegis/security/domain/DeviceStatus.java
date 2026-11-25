package com.aegis.security.domain;

import java.util.EnumSet;
import java.util.Set;

/**
 * Lifecycle state of a device with an explicit transition table (state machine).
 *
 * <p>Transitions are the only way state changes; illegal transitions raise {@code InvalidTransition}.
 * A device starts {@link #PENDING}, may become {@link #TRUSTED} or be {@link #REVOKED}, and
 * {@code REVOKED} is terminal.
 */
public enum DeviceStatus {
  PENDING,
  TRUSTED,
  REVOKED;

  private static final java.util.Map<DeviceStatus, Set<DeviceStatus>> ALLOWED =
      java.util.Map.of(
          PENDING, EnumSet.of(TRUSTED, REVOKED),
          TRUSTED, EnumSet.of(REVOKED),
          REVOKED, EnumSet.noneOf(DeviceStatus.class));

  public boolean canTransitionTo(DeviceStatus target) {
    return ALLOWED.getOrDefault(this, Set.of()).contains(target);
  }
}