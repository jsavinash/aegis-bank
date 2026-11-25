package com.aegis.security.command;

import jakarta.validation.constraints.NotBlank;

/** Command to register a new device for a customer. */
public record RegisterDevice(
    @NotBlank String customerId, @NotBlank String fingerprint) {}