package com.aegis.credit.domain;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Credit profile aggregate: owns a customer's credit score and its derived band.
 *
 * <p>The score is validated to the inclusive range {@code 300..850} at construction; any other value
 * raises {@link IllegalArgumentException}. The entity is immutable: {@link #withScore(int)} returns a
 * new profile with a recomputed band (used by the what-if simulator). {@code version} supports
 * optimistic concurrency checks at the persistence boundary.
 */
public record CreditProfile(
    UUID id, String customerId, int score, CreditBand band, long version, Instant updatedAt) {

  public CreditProfile {
    Objects.requireNonNull(id, "id must not be null");
    customerId = requireText(customerId, "customerId");
    Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    if (score < MIN_SCORE || score > MAX_SCORE) {
      throw new IllegalArgumentException(
          "score must be between " + MIN_SCORE + " and " + MAX_SCORE + ": " + score);
    }
    band = CreditBand.fromScore(score);
  }

  public static final int MIN_SCORE = 300;
  public static final int MAX_SCORE = 850;

  /** Returns a copy of this profile with {@code newScore}; the band is recomputed. */
  public CreditProfile withScore(int newScore) {
    return new CreditProfile(id, customerId, newScore, null, version, updatedAt);
  }

  private static String requireText(String value, String field) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(field + " must not be blank");
    }
    return value;
  }
}