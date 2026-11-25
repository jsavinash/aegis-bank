package com.aegis.accounts.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/** Command to open a new account. */
public record OpenAccount(
    @NotBlank String customerId, @NotNull String type, @NotBlank String currency) {}