package com.aegis.business.command;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/** Command to create an invoice under a business account. */
public record CreateInvoice(
    @NotNull UUID accountId,
    @NotNull BigDecimal amount,
    @NotBlank String currency,
    @NotNull LocalDate dueDate) {}