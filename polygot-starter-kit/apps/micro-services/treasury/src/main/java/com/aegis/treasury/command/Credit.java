package com.aegis.treasury.command;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/** Command to credit (add funds to) a cash position. */
public record Credit(@NotNull BigDecimal amount) {}