package com.aegis.cards;

import com.aegis.cards.command.AuthorizeSpend;
import com.aegis.cards.command.IssueCard;
import com.aegis.cards.command.UpdateControls;
import com.aegis.cards.web.CardView;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/** REST surface for cards. Thin: validation + delegation. */
@RestController
@RequestMapping("/cards")
public class CardController {

  private final CardService service;

  public CardController(CardService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<CardView> issue(@Valid @RequestBody IssueCard req) {
    CardView view = service.issue(req);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(view.id())
            .toUri();
    return ResponseEntity.created(location).body(view);
  }

  @GetMapping("/{id}")
  public CardView get(@PathVariable UUID id) {
    return service.get(id);
  }

  @GetMapping
  public List<CardView> list() {
    return service.list();
  }

  @PostMapping("/{id}/freeze")
  public CardView freeze(@PathVariable UUID id) {
    return service.freeze(id);
  }

  @PostMapping("/{id}/unfreeze")
  public CardView unfreeze(@PathVariable UUID id) {
    return service.unfreeze(id);
  }

  @PostMapping("/{id}/lost")
  public CardView reportLost(@PathVariable UUID id) {
    return service.reportLost(id);
  }

  @PostMapping("/{id}/stolen")
  public CardView reportStolen(@PathVariable UUID id) {
    return service.reportStolen(id);
  }

  @PostMapping("/{id}/close")
  public CardView close(@PathVariable UUID id) {
    return service.close(id);
  }

  @PatchMapping("/{id}/controls")
  public CardView updateControls(@PathVariable UUID id, @RequestBody UpdateControls req) {
    return service.updateControls(id, req);
  }

  @PostMapping("/{id}/authorize")
  public CardView authorize(@PathVariable UUID id, @Valid @RequestBody AuthorizeSpend req) {
    return service.authorize(id, req);
  }
}