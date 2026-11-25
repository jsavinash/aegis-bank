package com.aegis.cards.command;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/** Command to authorize a spend on a channel (used by the card-auth hot path / testing). */
public record AuthorizeSpend(@NotNull BigDecimal amount, @NotNull String channel) {}