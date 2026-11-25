package com.aegis.treasury.command;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/** Command to debit (remove funds from) a cash position. */
public record Debit(@NotNull BigDecimal amount) {}