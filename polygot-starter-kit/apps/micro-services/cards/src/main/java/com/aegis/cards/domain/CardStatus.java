package com.aegis.cards.domain;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * Card lifecycle state machine.
 *
 * <pre>
 *   ACTIVE  -> FROZEN | CLOSED | LOST | STOLEN
 *   FROZEN  -> ACTIVE | CLOSED
 *   LOST    -> ACTIVE | CLOSED
 *   STOLEN  -> CLOSED
 *   CLOSED  -> (terminal)
 * </pre>
 */
public enum CardStatus {
  ACTIVE,
  FROZEN,
  LOST,
  STOLEN,
  CLOSED;

  private static final Map<CardStatus, Set<CardStatus>> ALLOWED =
      Map.of(
          ACTIVE, EnumSet.of(FROZEN, CLOSED, LOST, STOLEN),
          FROZEN, EnumSet.of(ACTIVE, CLOSED),
          LOST, EnumSet.of(ACTIVE, CLOSED),
          STOLEN, EnumSet.of(CLOSED),
          CLOSED, EnumSet.noneOf(CardStatus.class));

  public boolean canTransitionTo(CardStatus target) {
    return ALLOWED.getOrDefault(this, Set.of()).contains(target);
  }
}