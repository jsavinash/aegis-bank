package com.aegis.billpay;
import java.math.BigDecimal;
public record NewBill(String customerId, String payee, BigDecimal amount, String dueDate) {}
