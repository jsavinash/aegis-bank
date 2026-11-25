package com.aegis.security.error;

import java.util.UUID;

/**
 * Raised on an illegal security-domain state transition (device or alert). Maps to HTTP 409.
 *
 * <p>Generic across entity types: the caller supplies the entity label and the from/to states.
 */
public class InvalidTransition extends SecurityError {

  public InvalidTransition(String entity, UUID id, String from, String to) {
    super(
        "invalid_transition",
        "cannot transition " + entity + " " + id + " from " + from + " to " + to);
  }
}