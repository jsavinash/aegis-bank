package com.aegis.treasury.error;

/** Base type for all treasury-domain errors. Carries a stable {@link #code()} for API mapping. */
public class TreasuryError extends RuntimeException {

  private final String code;

  public TreasuryError(String code, String message) {
    super(message);
    this.code = code;
  }

  public TreasuryError(String code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public String code() {
    return code;
  }
}