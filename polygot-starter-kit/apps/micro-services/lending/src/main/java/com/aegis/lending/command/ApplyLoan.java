package com.aegis.lending.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/** Command to apply for a new loan. */
public record ApplyLoan(
    @NotBlank String customerId,
    @NotBlank String type,
    @NotNull BigDecimal principal,
    @NotBlank String currency) {}