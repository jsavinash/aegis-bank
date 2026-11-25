package com.aegis.payments.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/** Command to initiate a payment. {@code idempotencyKey} dedups retries. */
public record InitiatePayment(
    @NotBlank String idempotencyKey,
    @NotBlank String fromAccount,
    @NotBlank String toAccount,
    @NotNull BigDecimal amount,
    @NotBlank String currency,
    @NotBlank String type) {}