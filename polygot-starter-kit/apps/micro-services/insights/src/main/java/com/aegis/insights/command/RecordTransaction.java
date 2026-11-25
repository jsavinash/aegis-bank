package com.aegis.insights.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/** Command to record a transaction for spend categorisation. {@code amount} must be positive. */
public record RecordTransaction(
    @NotBlank String accountId,
    @NotBlank String category,
    @NotNull BigDecimal amount,
    @NotBlank String currency,
    @NotBlank String merchant) {}