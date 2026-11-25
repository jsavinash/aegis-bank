package com.aegis.credit;

import com.aegis.credit.command.UpsertProfile;
import com.aegis.credit.web.CreditProfileView;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/** REST surface for the credit domain. Thin: validation + delegation, no business logic. */
@RestController
@RequestMapping("/credit")
public class CreditController {

  private final CreditService service;

  public CreditController(CreditService service) {
    this.service = service;
  }

  @PostMapping("/profiles")
  public ResponseEntity<CreditProfileView> upsert(@Valid @RequestBody UpsertProfile req) {
    CreditProfileView view = service.upsert(req);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(view.id())
            .toUri();
    return ResponseEntity.created(location).body(view);
  }

  @GetMapping("/profiles/{id}")
  public CreditProfileView get(@PathVariable UUID id) {
    return service.get(id);
  }

  @GetMapping("/profiles")
  public List<CreditProfileView> list() {
    return service.list();
  }

  @GetMapping("/profiles/simulate")
  public int simulate(@RequestParam int base, @RequestParam int delta) {
    return service.simulate(base, delta);
  }
}