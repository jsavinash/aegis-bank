package com.aegis.billpay;
import java.math.BigDecimal; import java.util.UUID;
public record Bill(UUID id, String customerId, String payee, BigDecimal amount, String dueDate, String status) {}
