package com.aegis.security.error;

/** Base type for all security-domain errors. Carries a stable {@link #code()} for API mapping. */
public class SecurityError extends RuntimeException {

  private final String code;

  public SecurityError(String code, String message) {
    super(message);
    this.code = code;
  }

  public SecurityError(String code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public String code() {
    return code;
  }
}