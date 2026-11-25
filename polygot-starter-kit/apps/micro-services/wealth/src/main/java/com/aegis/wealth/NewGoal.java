package com.aegis.wealth;
import java.math.BigDecimal;
public record NewGoal(String customerId, String name, BigDecimal target, String currency) {}
