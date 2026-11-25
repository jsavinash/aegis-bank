package com.aegis.insights;
import java.math.BigDecimal; import java.util.UUID;
public record Budget(UUID id, String customerId, String category, BigDecimal limit, BigDecimal spent) {}
