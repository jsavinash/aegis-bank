package com.aegis.security.web;

import com.aegis.security.domain.Device;
import com.aegis.security.domain.DeviceStatus;
import java.util.UUID;

/** Immutable snapshot of a device for API responses; never exposes the mutable entity. */
public record DeviceView(
    UUID id,
    String customerId,
    String fingerprint,
    boolean trusted,
    DeviceStatus status) {

  public static DeviceView from(Device d) {
    return new DeviceView(d.id(), d.customerId(), d.fingerprint(), d.trusted(), d.status());
  }
}