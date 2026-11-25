package com.aegis.payments;

import java.math.BigDecimal;
import java.util.UUID;

public record Payment(
    UUID id,
    String idempotencyKey,
    String fromAccount,
    String toAccount,
    BigDecimal amount,
    String currency,
    String type,
    String status) {}
