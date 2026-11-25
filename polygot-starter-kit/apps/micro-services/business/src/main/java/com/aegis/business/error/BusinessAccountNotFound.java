package com.aegis.business.error;

import java.util.UUID;

/** Raised when a business account is not found. Maps to HTTP 404. */
public class BusinessAccountNotFound extends BusinessError {

  public BusinessAccountNotFound(UUID id) {
    super("business_account_not_found", "business account not found: " + id);
  }
}