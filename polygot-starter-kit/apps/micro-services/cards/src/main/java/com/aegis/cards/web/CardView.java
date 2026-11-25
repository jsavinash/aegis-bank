package com.aegis.cards.web;

import com.aegis.cards.domain.Card;
import com.aegis.cards.domain.CardControls;
import com.aegis.cards.domain.CardStatus;
import com.aegis.cards.domain.CardType;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/** Immutable snapshot of a card for API responses. */
public record CardView(
    UUID id,
    String customerId,
    CardType type,
    CardStatus status,
    boolean online,
    boolean contactless,
    boolean atm,
    boolean international,
    BigDecimal dailyLimit,
    BigDecimal perTransactionLimit,
    BigDecimal spentToday,
    long version,
    Instant issuedAt) {

  public static CardView from(Card c) {
    CardControls ctl = c.controls();
    return new CardView(
        c.id(),
        c.customerId(),
        c.type(),
        c.status(),
        ctl.online(),
        ctl.contactless(),
        ctl.atm(),
        ctl.international(),
        ctl.dailyLimit(),
        ctl.perTransactionLimit(),
        c.spentToday(),
        c.version(),
        c.issuedAt());
  }
}