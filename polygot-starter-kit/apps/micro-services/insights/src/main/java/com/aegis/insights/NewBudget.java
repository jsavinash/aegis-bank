package com.aegis.insights;
import java.math.BigDecimal;
public record NewBudget(String customerId, String category, BigDecimal limit) {}
