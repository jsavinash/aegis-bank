package com.aegis.insights.command;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/** Command to record spend against a budget. {@code amount} must be positive. */
public record RecordSpend(@NotNull BigDecimal amount) {}