package com.aegis.security.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/** Command to raise a fraud alert for a customer. */
public record RaiseAlert(
    @NotBlank String customerId, @NotNull BigDecimal amount) {}