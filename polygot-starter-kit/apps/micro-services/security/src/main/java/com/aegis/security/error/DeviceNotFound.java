package com.aegis.security.error;

import java.util.UUID;

/** Raised when a device is not found. Maps to HTTP 404. */
public class DeviceNotFound extends SecurityError {

  public DeviceNotFound(UUID id) {
    super("device_not_found", "device not found: " + id);
  }
}