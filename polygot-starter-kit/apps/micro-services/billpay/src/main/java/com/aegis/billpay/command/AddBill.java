package com.aegis.billpay.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

/** Command to add a new bill. */
public record AddBill(
    @NotBlank String customerId,
    @NotBlank String payee,
    @NotNull BigDecimal amount,
    @NotBlank String currency,
    @NotNull LocalDate dueDate) {}