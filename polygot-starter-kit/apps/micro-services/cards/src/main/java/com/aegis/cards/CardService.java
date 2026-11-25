package com.aegis.cards;

import com.aegis.cards.command.AuthorizeSpend;
import com.aegis.cards.command.IssueCard;
import com.aegis.cards.command.UpdateControls;
import com.aegis.cards.domain.Card;
import com.aegis.cards.domain.CardControls;
import com.aegis.cards.domain.CardType;
import com.aegis.cards.error.CardNotFound;
import com.aegis.cards.web.CardView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import org.springframework.stereotype.Service;

/**
 * Card application service. Per-card {@link ReentrantLock} serializes mutations (freeze, controls,
 * spend authorization) so concurrent authorizations cannot race the daily limit.
 */
@Service
public class CardService {

  private final Map<UUID, Card> store = new ConcurrentHashMap<>();
  private final Map<UUID, ReentrantLock> locks = new ConcurrentHashMap<>();

  public CardView issue(IssueCard req) {
    Card card = new Card(UUID.randomUUID(), req.customerId(), parseType(req.type()));
    store.put(card.id(), card);
    return CardView.from(card);
  }

  public CardView get(UUID id) {
    return CardView.from(require(id));
  }

  public List<CardView> list() {
    List<CardView> out = new ArrayList<>(store.size());
    for (Card c : store.values()) {
      out.add(CardView.from(c));
    }
    return out;
  }

  public CardView freeze(UUID id) {
    return mutate(id, Card::freeze);
  }

  public CardView unfreeze(UUID id) {
    return mutate(id, Card::unfreeze);
  }

  public CardView reportLost(UUID id) {
    return mutate(id, Card::reportLost);
  }

  public CardView reportStolen(UUID id) {
    return mutate(id, Card::reportStolen);
  }

  public CardView close(UUID id) {
    return mutate(id, Card::close);
  }

  public CardView updateControls(UUID id, UpdateControls req) {
    return mutate(
        id,
        card -> {
          CardControls c = card.controls();
          if (req.online() != null) c.setOnline(req.online());
          if (req.contactless() != null) c.setContactless(req.contactless());
          if (req.atm() != null) c.setAtm(req.atm());
          if (req.international() != null) c.setInternational(req.international());
          if (req.dailyLimit() != null) c.setDailyLimit(req.dailyLimit());
          if (req.perTransactionLimit() != null) c.setPerTransactionLimit(req.perTransactionLimit());
        });
  }

  public CardView authorize(UUID id, AuthorizeSpend req) {
    CardControls.Channel channel = parseChannel(req.channel());
    return mutate(id, card -> card.authorize(req.amount(), channel));
  }

  private CardView mutate(UUID id, Consumer<Card> action) {
    ReentrantLock lock = lock(id);
    lock.lock();
    try {
      Card card = require(id);
      action.accept(card);
      return CardView.from(card);
    } finally {
      lock.unlock();
    }
  }

  private Card require(UUID id) {
    Card card = store.get(id);
    if (card == null) {
      throw new CardNotFound(id);
    }
    return card;
  }

  private ReentrantLock lock(UUID id) {
    return locks.computeIfAbsent(id, k -> new ReentrantLock());
  }

  private CardType parseType(String raw) {
    try {
      return CardType.valueOf(raw.trim().toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("unknown card type: " + raw, e);
    }
  }

  private CardControls.Channel parseChannel(String raw) {
    try {
      return CardControls.Channel.valueOf(raw.trim().toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("unknown channel: " + raw, e);
    }
  }
}