package com.aegis.lending.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Maps lending-domain errors to RFC 7807 {@code ProblemDetail} responses. */
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(LoanNotFound.class)
  public ProblemDetail handleNotFound(LoanNotFound e) {
    return problem(HttpStatus.NOT_FOUND, e.code(), e.getMessage());
  }

  @ExceptionHandler(InvalidLoanTransition.class)
  public ProblemDetail handleInvalidTransition(InvalidLoanTransition e) {
    return problem(HttpStatus.CONFLICT, e.code(), e.getMessage());
  }

  @ExceptionHandler(LoanError.class)
  public ProblemDetail handleLoanError(LoanError e) {
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