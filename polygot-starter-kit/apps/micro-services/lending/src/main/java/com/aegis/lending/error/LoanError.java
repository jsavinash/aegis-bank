package com.aegis.lending.error;

/** Base type for all lending-domain errors. Carries a stable {@link #code()} for API mapping. */
public class LoanError extends RuntimeException {

  private final String code;

  public LoanError(String code, String message) {
    super(message);
    this.code = code;
  }

  public LoanError(String code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public String code() {
    return code;
  }
}