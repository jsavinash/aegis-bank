package com.aegis.lending;
import java.math.BigDecimal; import java.util.UUID;
public record Loan(UUID id, String customerId, BigDecimal principal, String currency, String status) {}
