package com.aegis.lending;

import com.aegis.lending.command.ApplyLoan;
import com.aegis.lending.web.LoanView;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/** REST surface for the lending domain. Thin: validation + delegation, no business logic. */
@RestController
@RequestMapping("/loans")
public class LoanController {

  private final LoanService service;

  public LoanController(LoanService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<LoanView> apply(@Valid @RequestBody ApplyLoan req) {
    LoanView view = service.apply(req);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(view.id())
            .toUri();
    return ResponseEntity.created(location).body(view);
  }

  @GetMapping("/{id}")
  public LoanView get(@PathVariable UUID id) {
    return service.get(id);
  }

  @GetMapping
  public List<LoanView> list() {
    return service.list();
  }

  @PostMapping("/{id}/approve")
  public LoanView approve(@PathVariable UUID id) {
    return service.approve(id);
  }

  @PostMapping("/{id}/decline")
  public LoanView decline(@PathVariable UUID id) {
    return service.decline(id);
  }

  @PostMapping("/{id}/disburse")
  public LoanView disburse(@PathVariable UUID id) {
    return service.disburse(id);
  }

  @PostMapping("/{id}/activate")
  public LoanView activate(@PathVariable UUID id) {
    return service.activate(id);
  }

  @PostMapping("/{id}/pay-off")
  public LoanView payOff(@PathVariable UUID id) {
    return service.payOff(id);
  }

  @PostMapping("/{id}/default")
  public LoanView defaultLoan(@PathVariable UUID id) {
    return service.defaultLoan(id);
  }
}