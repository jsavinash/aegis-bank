package com.aegis.cards;
import java.net.URI; import java.util.*; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/cards")
public class CardController {
  private final CardService s; public CardController(CardService s){this.s=s;}
  @PostMapping public ResponseEntity<Card> issue(@RequestBody NewCard r){ Card c=s.issue(r); return ResponseEntity.created(URI.create("/cards/"+c.id())).body(c); }
  @GetMapping("/{id}") public ResponseEntity<Card> get(@PathVariable UUID id){ return s.get(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }
  @GetMapping public List<Card> list(){ return s.list(); }
  @PostMapping("/{id}/freeze") public Card freeze(@PathVariable UUID id){ return s.setStatus(id,"FROZEN"); }
  @PostMapping("/{id}/unfreeze") public Card unfreeze(@PathVariable UUID id){ return s.setStatus(id,"ACTIVE"); }
}
