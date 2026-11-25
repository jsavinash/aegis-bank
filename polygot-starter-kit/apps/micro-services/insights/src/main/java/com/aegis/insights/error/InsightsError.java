package com.aegis.insights.error;

/** Base type for all insights-domain errors. Carries a stable {@link #code()} for API mapping. */
public class InsightsError extends RuntimeException {

  private final String code;

  public InsightsError(String code, String message) {
    super(message);
    this.code = code;
  }

  public InsightsError(String code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public String code() {
    return code;
  }
}