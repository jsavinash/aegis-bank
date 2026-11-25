package com.aegis.payments.error;

/** Base error for the payments domain; carries a stable {@link #code()} for API mapping. */
public class PaymentError extends RuntimeException {

  private final String code;

  public PaymentError(String code, String message) {
    super(message);
    this.code = code;
  }

  public PaymentError(String code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public String code() {
    return code;
  }
}