package com.aegis.payments;

import java.math.BigDecimal;

public record NewPayment(
    String idempotencyKey,
    String fromAccount,
    String toAccount,
    BigDecimal amount,
    String currency,
    String type) {}
