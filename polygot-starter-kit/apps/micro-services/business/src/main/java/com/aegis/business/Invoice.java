package com.aegis.business;
import java.math.BigDecimal; import java.util.UUID;
public record Invoice(UUID id, UUID accountId, BigDecimal amount, String status) {}
