package com.aegis.security;
import java.util.UUID;
public record Device(UUID id, String customerId, String fingerprint, boolean trusted) {}
