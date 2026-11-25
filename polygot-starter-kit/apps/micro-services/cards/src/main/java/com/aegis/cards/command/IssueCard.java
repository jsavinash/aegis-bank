package com.aegis.cards.command;

import jakarta.validation.constraints.NotBlank;

/** Command to issue a new card. */
public record IssueCard(@NotBlank String customerId, @NotBlank String type) {}