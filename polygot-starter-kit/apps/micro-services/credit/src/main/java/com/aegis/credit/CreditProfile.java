package com.aegis.credit;
import java.util.UUID;
public record CreditProfile(UUID id, String customerId, int score, String band) {}
