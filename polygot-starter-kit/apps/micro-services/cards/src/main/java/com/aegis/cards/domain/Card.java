package com.aegis.cards.domain;

import com.aegis.cards.error.InvalidCardTransition;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Card aggregate root: owns status, controls, and spend authorization. State changes go through the
 * {@link CardStatus} state machine; spend authorization enforces the {@link CardControls}.
 */
public final class Card {

  private final UUID id;
  private final String customerId;
  private final CardType type;
  private final Instant issuedAt;
  private final CardControls controls = new CardControls();

  private CardStatus status;
  private long version;
  private BigDecimal spentToday = BigDecimal.ZERO;

  public Card(UUID id, String customerId, CardType type) {
    this.id = Objects.requireNonNull(id, "id must not be null");
    this.customerId = requireText(customerId, "customerId");
    this.type = Objects.requireNonNull(type, "type must not be null");
    this.issuedAt = Instant.now();
    this.status = CardStatus.ACTIVE;
  }

  public UUID id() {
    return id;
  }

  public String customerId() {
    return customerId;
  }

  public CardType type() {
    return type;
  }

  public CardStatus status() {
    return status;
  }

  public CardControls controls() {
    return controls;
  }

  public long version() {
    return version;
  }

  public Instant issuedAt() {
    return issuedAt;
  }

  public BigDecimal spentToday() {
    return spentToday;
  }

  public void freeze() {
    transition(CardStatus.FROZEN);
  }

  public void unfreeze() {
    transition(CardStatus.ACTIVE);
  }

  public void reportLost() {
    transition(CardStatus.LOST);
  }

  public void reportStolen() {
    transition(CardStatus.STOLEN);
  }

  public void close() {
    transition(CardStatus.CLOSED);
  }

  /** Authorize a spend on the given channel, updating the daily total; throws if blocked. */
  public void authorize(BigDecimal amount, CardControls.Channel channel) {
    if (status != CardStatus.ACTIVE) {
      throw new IllegalStateException("card is " + status + ", not ACTIVE");
    }
    controls.authorize(amount, channel);
    if (controls.dailyLimit() != null && spentToday.add(amount).compareTo(controls.dailyLimit()) > 0) {
      throw new IllegalArgumentException("amount exceeds daily limit");
    }
    this.spentToday = spentToday.add(amount);
    this.version++;
  }

  private void transition(CardStatus target) {
    if (!status.canTransitionTo(target)) {
      throw new InvalidCardTransition(id, status, target);
    }
    this.status = target;
    this.version++;
  }

  private static String requireText(String value, String field) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(field + " must not be blank");
    }
    return value;
  }
}