package com.aegis.cards.command;

import java.math.BigDecimal;

/** Command to update a card's channel controls / spend limits. Null fields are left unchanged. */
public record UpdateControls(
    Boolean online,
    Boolean contactless,
    Boolean atm,
    Boolean international,
    BigDecimal dailyLimit,
    BigDecimal perTransactionLimit) {}