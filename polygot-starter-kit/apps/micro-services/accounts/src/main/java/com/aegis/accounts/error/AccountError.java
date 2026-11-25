package com.aegis.accounts.error;

/** Base type for all account-domain errors. Carries a stable {@link #code()} for API mapping. */
public class AccountError extends RuntimeException {

  private final String code;

  public AccountError(String code, String message) {
    super(message);
    this.code = code;
  }

  public AccountError(String code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public String code() {
    return code;
  }
}