package com.aegis.wealth.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/** Command to add (or accumulate) a holding in a portfolio. */
public record AddHolding(@NotBlank String symbol, @NotNull BigDecimal value) {}