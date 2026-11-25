package com.aegis.accounts;

import com.aegis.accounts.command.MoveMoney;
import com.aegis.accounts.command.OpenAccount;
import com.aegis.accounts.command.StatusChange;
import com.aegis.accounts.domain.LedgerEntry;
import com.aegis.accounts.web.AccountView;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/** REST surface for the accounts domain. Thin: validation + delegation, no business logic. */
@RestController
@RequestMapping("/accounts")
public class AccountController {

  private final AccountService service;

  public AccountController(AccountService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<AccountView> open(@Valid @RequestBody OpenAccount req) {
    AccountView view = service.open(req);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(view.id())
            .toUri();
    return ResponseEntity.created(location).body(view);
  }

  @GetMapping("/{id}")
  public AccountView get(@PathVariable UUID id) {
    return service.get(id);
  }

  @GetMapping
  public List<AccountView> list() {
    return service.list();
  }

  @PostMapping("/{id}/deposits")
  public AccountView deposit(@PathVariable UUID id, @Valid @RequestBody MoveMoney req) {
    return service.deposit(id, req);
  }

  @PostMapping("/{id}/withdrawals")
  public AccountView withdraw(@PathVariable UUID id, @Valid @RequestBody MoveMoney req) {
    return service.withdraw(id, req);
  }

  @PostMapping("/{id}/freeze")
  public AccountView freeze(@PathVariable UUID id, @RequestBody(required = false) StatusChange req) {
    return service.freeze(id, req == null ? new StatusChange(null) : req);
  }

  @PostMapping("/{id}/unfreeze")
  public AccountView unfreeze(@PathVariable UUID id, @RequestBody(required = false) StatusChange req) {
    return service.unfreeze(id, req == null ? new StatusChange(null) : req);
  }

  @PostMapping("/{id}/close")
  public AccountView close(@PathVariable UUID id, @RequestBody(required = false) StatusChange req) {
    return service.close(id, req == null ? new StatusChange(null) : req);
  }

  @GetMapping("/{id}/ledger")
  public List<LedgerEntry> ledger(@PathVariable UUID id) {
    return service.ledger(id);
  }
}