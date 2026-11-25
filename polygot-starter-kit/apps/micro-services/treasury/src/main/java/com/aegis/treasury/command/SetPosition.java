package com.aegis.treasury.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

/** Command to create or replace a cash position for an account. */
public record SetPosition(
    @NotNull UUID accountId, @NotNull BigDecimal balance, @NotBlank String currency) {}