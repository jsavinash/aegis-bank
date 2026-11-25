package com.aegis.wealth;
import java.math.BigDecimal; import java.util.UUID;
public record Goal(UUID id, String customerId, String name, BigDecimal target, BigDecimal saved, String currency) {}
