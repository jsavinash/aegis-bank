package com.aegis.wealth.command;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/** Command to contribute an amount to a savings goal. The amount must be positive. */
public record Contribute(@NotNull BigDecimal amount) {}