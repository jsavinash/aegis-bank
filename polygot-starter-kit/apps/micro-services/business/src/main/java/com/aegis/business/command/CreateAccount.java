package com.aegis.business.command;

import jakarta.validation.constraints.NotBlank;

/** Command to create a business account. */
public record CreateAccount(@NotBlank String name, @NotBlank String taxId) {}