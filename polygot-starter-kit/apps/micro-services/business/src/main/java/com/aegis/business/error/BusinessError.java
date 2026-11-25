package com.aegis.business.error;

/** Base type for all business-domain errors. Carries a stable {@link #code()} for API mapping. */
public class BusinessError extends RuntimeException {

  private final String code;

  public BusinessError(String code, String message) {
    super(message);
    this.code = code;
  }

  public BusinessError(String code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public String code() {
    return code;
  }
}