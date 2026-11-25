package com.aegis.accounts.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Maps account-domain errors to RFC 7807 {@code ProblemDetail} responses. */
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(AccountNotFound.class)
  public ProblemDetail handleNotFound(AccountNotFound e) {
    return problem(HttpStatus.NOT_FOUND, e.code(), e.getMessage());
  }

  @ExceptionHandler(InvalidTransition.class)
  public ProblemDetail handleInvalidTransition(InvalidTransition e) {
    return problem(HttpStatus.CONFLICT, e.code(), e.getMessage());
  }

  @ExceptionHandler(InsufficientFunds.class)
  public ProblemDetail handleInsufficientFunds(InsufficientFunds e) {
    return problem(HttpStatus.CONFLICT, e.code(), e.getMessage());
  }

  @ExceptionHandler(AccountError.class)
  public ProblemDetail handleAccountError(AccountError e) {
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