package com.aegis.billpay.error;

/** Base error for the bill-pay domain; carries a stable {@link #code()} for API mapping. */
public class BillError extends RuntimeException {

  private final String code;

  public BillError(String code, String message) {
    super(message);
    this.code = code;
  }

  public String code() {
    return code;
  }
}