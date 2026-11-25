package com.aegis.security.error;

import java.util.UUID;

/** Raised when a fraud alert is not found. Maps to HTTP 404. */
public class AlertNotFound extends SecurityError {

  public AlertNotFound(UUID id) {
    super("alert_not_found", "alert not found: " + id);
  }
}