package com.aegis.accounts.command;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Command to move money (deposit or withdraw). {@code amount} must be positive; {@code idempotencyKey}
 * dedups retries so a repeated request never double-posts.
 */
public record MoveMoney(@NotNull BigDecimal amount, String idempotencyKey) {}