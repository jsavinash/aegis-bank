package com.aegis.business.domain;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Business account aggregate root: a company profile with a stable tax id.
 *
 * <p>Simple and immutable apart from construction; the service stores it and serializes invoice
 * mutations against it with a per-account lock.
 */
public final class BusinessAccount {

  private final UUID id;
  private final String name;
  private final String taxId;
  private final Instant createdAt;

  public BusinessAccount(UUID id, String name, String taxId) {
    this.id = Objects.requireNonNull(id, "id must not be null");
    this.name = requireText(name, "name");
    this.taxId = requireText(taxId, "taxId");
    this.createdAt = Instant.now();
  }

  public UUID id() {
    return id;
  }

  public String name() {
    return name;
  }

  public String taxId() {
    return taxId;
  }

  public Instant createdAt() {
    return createdAt;
  }

  private static String requireText(String value, String field) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(field + " must not be blank");
    }
    return value;
  }
}