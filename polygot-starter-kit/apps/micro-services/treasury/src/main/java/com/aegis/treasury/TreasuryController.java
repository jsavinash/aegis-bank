package com.aegis.treasury;

import com.aegis.treasury.command.Credit;
import com.aegis.treasury.command.Debit;
import com.aegis.treasury.command.SetPosition;
import com.aegis.treasury.web.CashPositionView;
import com.aegis.treasury.web.ForecastView;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/** REST surface for the treasury domain. Thin: validation + delegation, no business logic. */
@RestController
@RequestMapping("/treasury")
public class TreasuryController {

  private final TreasuryService service;

  public TreasuryController(TreasuryService service) {
    this.service = service;
  }

  /**
   * Create or replace a cash position. A freshly created position has {@code version == 0} and yields
   * 201 Created; replacing an existing one yields 200 OK. Both carry a Location header.
   */
  @PutMapping("/positions")
  public ResponseEntity<CashPositionView> setPosition(@Valid @RequestBody SetPosition req) {
    CashPositionView view = service.setPosition(req);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{accountId}")
            .buildAndExpand(view.accountId())
            .toUri();
    if (view.version() == 0) {
      return ResponseEntity.created(location).body(view);
    }
    return ResponseEntity.ok().location(location).body(view);
  }

  @GetMapping("/positions")
  public List<CashPositionView> all() {
    return service.all();
  }

  @GetMapping("/positions/{accountId}")
  public CashPositionView get(@PathVariable UUID accountId) {
    return service.get(accountId);
  }

  @PostMapping("/positions/{accountId}/credit")
  public CashPositionView credit(@PathVariable UUID accountId, @Valid @RequestBody Credit req) {
    return service.credit(accountId, req);
  }

  @PostMapping("/positions/{accountId}/debit")
  public CashPositionView debit(@PathVariable UUID accountId, @Valid @RequestBody Debit req) {
    return service.debit(accountId, req);
  }

  @GetMapping("/positions/{accountId}/forecast")
  public ForecastView forecast(
      @PathVariable UUID accountId, @RequestParam(defaultValue = "13") int weeks) {
    return service.forecast(accountId, weeks);
  }
}