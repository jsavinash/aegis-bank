package com.aegis.insights.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/** Command to create a spending budget for a customer and category. */
public record CreateBudget(
    @NotBlank String customerId,
    @NotBlank String category,
    @NotNull BigDecimal limit,
    @NotBlank String currency) {}