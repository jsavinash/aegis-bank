package com.aegis.cards.error;

import com.aegis.cards.domain.CardStatus;
import java.util.UUID;

/** Raised on an illegal card-state transition. Maps to HTTP 409. */
public class InvalidCardTransition extends CardError {

  public InvalidCardTransition(UUID id, CardStatus from, CardStatus to) {
    super("invalid_transition", "cannot transition card " + id + " from " + from + " to " + to);
  }
}