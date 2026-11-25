package com.aegis.accounts;

import java.math.BigDecimal;
import java.util.UUID;

public record Account(
    UUID id, String customerId, String type, BigDecimal balance, String currency, String status) {}
