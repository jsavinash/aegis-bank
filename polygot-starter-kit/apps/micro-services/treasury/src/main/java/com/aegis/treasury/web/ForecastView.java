package com.aegis.treasury.web;

import java.math.BigDecimal;

/** Immutable forecast projection for a cash position over a number of weeks. */
public record ForecastView(int weeks, BigDecimal projectedBalance) {}