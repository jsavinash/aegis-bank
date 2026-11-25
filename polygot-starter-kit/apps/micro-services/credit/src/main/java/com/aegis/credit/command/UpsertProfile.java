package com.aegis.credit.command;

import jakarta.validation.constraints.NotBlank;

/** Command to create or replace a customer's credit profile. */
public record UpsertProfile(@NotBlank String customerId, int score) {}