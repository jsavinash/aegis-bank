package com.aegis.cards;
import java.util.*; import java.util.concurrent.ConcurrentHashMap; import org.springframework.stereotype.Service;
@Service
public class CardService {
  private final Map<UUID,Card> store = new ConcurrentHashMap<>();
  public Card issue(NewCard r){ Card c=new Card(UUID.randomUUID(), r.customerId(), r.type(), "ACTIVE"); store.put(c.id(),c); return c; }
  public Optional<Card> get(UUID id){ return Optional.ofNullable(store.get(id)); }
  public List<Card> list(){ return new ArrayList<>(store.values()); }
  public Card setStatus(UUID id, String s){ Card c=store.get(id); if(c==null) return null; Card n=new Card(c.id(),c.customerId(),c.type(),s); store.put(id,n); return n; }
}
