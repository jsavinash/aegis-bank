package com.aegis.credit.error;

/** Base type for all credit-domain errors. Carries a stable {@link #code()} for API mapping. */
public class CreditError extends RuntimeException {

  private final String code;

  public CreditError(String code, String message) {
    super(message);
    this.code = code;
  }

  public CreditError(String code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public String code() {
    return code;
  }
}