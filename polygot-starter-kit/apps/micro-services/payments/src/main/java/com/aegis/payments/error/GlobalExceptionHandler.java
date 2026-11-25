package com.aegis.payments.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Maps payments-domain errors to RFC 7807 {@code ProblemDetail} responses. */
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(PaymentNotFound.class)
  public ProblemDetail handleNotFound(PaymentNotFound e) {
    return problem(HttpStatus.NOT_FOUND, e.code(), e.getMessage());
  }

  @ExceptionHandler(InvalidPaymentTransition.class)
  public ProblemDetail handleInvalidTransition(InvalidPaymentTransition e) {
    return problem(HttpStatus.CONFLICT, e.code(), e.getMessage());
  }

  @ExceptionHandler(PaymentError.class)
  public ProblemDetail handlePaymentError(PaymentError e) {
    return problem(HttpStatus.BAD_REQUEST, e.code(), e.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ProblemDetail handleIllegalArgument(IllegalArgumentException e) {
    return problem(HttpStatus.BAD_REQUEST, "validation_error", e.getMessage());
  }

  private ProblemDetail problem(HttpStatus status, String code, String detail) {
    ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, detail);
    pd.setTitle(status.getReasonPhrase());
    pd.setProperty("code", code);
    return pd;
  }
}