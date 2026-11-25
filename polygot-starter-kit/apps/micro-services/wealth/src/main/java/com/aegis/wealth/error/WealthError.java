package com.aegis.wealth.error;

/** Base type for all wealth-domain errors. Carries a stable {@link #code()} for API mapping. */
public class WealthError extends RuntimeException {

  private final String code;

  public WealthError(String code, String message) {
    super(message);
    this.code = code;
  }

  public WealthError(String code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public String code() {
    return code;
  }
}