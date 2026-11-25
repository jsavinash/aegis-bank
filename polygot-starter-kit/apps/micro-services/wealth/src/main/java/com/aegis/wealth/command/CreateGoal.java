package com.aegis.wealth.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/** Command to create a new savings goal. */
public record CreateGoal(
    @NotBlank String customerId,
    @NotBlank String name,
    @NotNull BigDecimal target,
    @NotBlank String currency) {}