package com.aegis.wealth.command;

import jakarta.validation.constraints.NotBlank;

/** Command to open a new portfolio. */
public record CreatePortfolio(@NotBlank String customerId, @NotBlank String currency) {}