package com.aegis.cards.error;

/** Base error for the cards domain; carries a stable {@link #code()} for API mapping. */
public class CardError extends RuntimeException {

  private final String code;

  public CardError(String code, String message) {
    super(message);
    this.code = code;
  }

  public CardError(String code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public String code() {
    return code;
  }
}