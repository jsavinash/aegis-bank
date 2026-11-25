package com.aegis.cards;

import static org.junit.jupiter.api.Assertions.*;

import com.aegis.cards.command.AuthorizeSpend;
import com.aegis.cards.command.IssueCard;
import com.aegis.cards.command.UpdateControls;
import com.aegis.cards.domain.CardStatus;
import com.aegis.cards.error.CardNotFound;
import com.aegis.cards.error.InvalidCardTransition;
import com.aegis.cards.web.CardView;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CardServiceTest {

  private final CardService service = new CardService();

  private CardView issue() {
    return service.issue(new IssueCard("c1", "PHYSICAL_DEBIT"));
  }

  @Test
  void issueStartsActive() {
    assertEquals(CardStatus.ACTIVE, issue().status());
  }

  @Test
  void freezeThenUnfreeze() {
    CardView c = issue();
    assertEquals(CardStatus.FROZEN, service.freeze(c.id()).status());
    assertEquals(CardStatus.ACTIVE, service.unfreeze(c.id()).status());
  }

  @Test
  void stolenIsTerminal() {
    CardView c = issue();
    service.reportStolen(c.id());
    assertThrows(InvalidCardTransition.class, () -> service.unfreeze(c.id()));
  }

  @Test
  void controlsBlockDisabledChannel() {
    CardView c = issue();
    service.updateControls(c.id(), new UpdateControls(true, true, true, false, null, null));
    assertThrows(
        IllegalArgumentException.class,
        () -> service.authorize(c.id(), new AuthorizeSpend(new BigDecimal("5.00"), "INTERNATIONAL")));
  }

  @Test
  void perTransactionLimitEnforced() {
    CardView c = issue();
    service.updateControls(c.id(), new UpdateControls(null, null, null, null, null, new BigDecimal("10.00")));
    assertThrows(
        IllegalArgumentException.class,
        () -> service.authorize(c.id(), new AuthorizeSpend(new BigDecimal("50.00"), "ONLINE")));
  }

  @Test
  void dailyLimitEnforcedAcrossAuthorizations() {
    CardView c = issue();
    service.updateControls(c.id(), new UpdateControls(null, null, null, null, new BigDecimal("10.00"), null));
    service.authorize(c.id(), new AuthorizeSpend(new BigDecimal("6.00"), "ONLINE"));
    assertThrows(
        IllegalArgumentException.class,
        () -> service.authorize(c.id(), new AuthorizeSpend(new BigDecimal("6.00"), "ONLINE")));
  }

  @Test
  void frozenCardRejectsSpend() {
    CardView c = issue();
    service.freeze(c.id());
    assertThrows(
        IllegalStateException.class,
        () -> service.authorize(c.id(), new AuthorizeSpend(new BigDecimal("1.00"), "ONLINE")));
  }

  @Test
  void unknownCardThrowsNotFound() {
    assertThrows(CardNotFound.class, () -> service.get(UUID.randomUUID()));
  }

  @Test
  void invalidTypeRejected() {
    assertThrows(IllegalArgumentException.class, () -> service.issue(new IssueCard("c1", "WOODEN")));
  }
}