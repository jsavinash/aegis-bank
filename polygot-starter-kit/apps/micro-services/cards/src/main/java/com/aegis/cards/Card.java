package com.aegis.cards;
import java.util.UUID;
public record Card(UUID id, String customerId, String type, String status) {}
