package com.aegis.accounts;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {
  private final AccountService service;

  public AccountController(AccountService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<Account> open(@RequestBody NewAccount req) {
    Account a = service.open(req);
    return ResponseEntity.created(URI.create("/accounts/" + a.id())).body(a);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Account> get(@PathVariable UUID id) {
    return service.get(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public List<Account> list() {
    return service.list();
  }
}
