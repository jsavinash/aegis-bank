package com.aegis.cards.error;

import java.util.UUID;

/** Raised when a card is not found. Maps to HTTP 404. */
public class CardNotFound extends CardError {

  public CardNotFound(UUID id) {
    super("card_not_found", "card not found: " + id);
  }
}