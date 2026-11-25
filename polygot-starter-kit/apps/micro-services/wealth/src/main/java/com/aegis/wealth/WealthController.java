package com.aegis.wealth;

import com.aegis.wealth.command.AddHolding;
import com.aegis.wealth.command.Contribute;
import com.aegis.wealth.command.CreateGoal;
import com.aegis.wealth.command.CreatePortfolio;
import com.aegis.wealth.web.GoalView;
import com.aegis.wealth.web.PortfolioView;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/** REST surface for the wealth domain. Thin: validation + delegation, no business logic. */
@RestController
public class WealthController {

  private final WealthService service;

  public WealthController(WealthService service) {
    this.service = service;
  }

  @PostMapping("/goals")
  public ResponseEntity<GoalView> createGoal(@Valid @RequestBody CreateGoal req) {
    GoalView view = service.createGoal(req);
    return ResponseEntity.created(location(view.id())).body(view);
  }

  @GetMapping("/goals/{id}")
  public GoalView getGoal(@PathVariable UUID id) {
    return service.getGoal(id);
  }

  @GetMapping("/goals")
  public List<GoalView> listGoals() {
    return service.listGoals();
  }

  @PostMapping("/goals/{id}/contribute")
  public GoalView contribute(@PathVariable UUID id, @Valid @RequestBody Contribute req) {
    return service.contribute(id, req);
  }

  @PostMapping("/goals/{id}/cancel")
  public GoalView cancelGoal(@PathVariable UUID id) {
    return service.cancelGoal(id);
  }

  @PostMapping("/portfolios")
  public ResponseEntity<PortfolioView> createPortfolio(@Valid @RequestBody CreatePortfolio req) {
    PortfolioView view = service.createPortfolio(req);
    return ResponseEntity.created(location(view.id())).body(view);
  }

  @GetMapping("/portfolios/{id}")
  public PortfolioView getPortfolio(@PathVariable UUID id) {
    return service.getPortfolio(id);
  }

  @GetMapping("/portfolios")
  public List<PortfolioView> listPortfolios() {
    return service.listPortfolios();
  }

  @PostMapping("/portfolios/{id}/holdings")
  public PortfolioView addHolding(@PathVariable UUID id, @Valid @RequestBody AddHolding req) {
    return service.addHolding(id, req);
  }

  private static URI location(UUID id) {
    return ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(id)
        .toUri();
  }
}