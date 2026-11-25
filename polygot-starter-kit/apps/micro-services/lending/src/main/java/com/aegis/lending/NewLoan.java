package com.aegis.lending;
import java.math.BigDecimal;
public record NewLoan(String customerId, BigDecimal principal, String currency) {}
