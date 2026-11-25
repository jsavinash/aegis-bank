package com.aegis.wealth.error;

import java.util.UUID;

/** Raised when a portfolio is not found. Maps to HTTP 404. */
public class PortfolioNotFound extends WealthError {

  public PortfolioNotFound(UUID id) {
    super("portfolio_not_found", "portfolio not found: " + id);
  }
}