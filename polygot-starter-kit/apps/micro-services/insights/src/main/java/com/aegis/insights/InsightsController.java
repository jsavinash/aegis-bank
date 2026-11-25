package com.aegis.insights;

import com.aegis.insights.command.CreateBudget;
import com.aegis.insights.command.RecordSpend;
import com.aegis.insights.command.RecordTransaction;
import com.aegis.insights.web.BudgetView;
import com.aegis.insights.web.TransactionView;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/** REST surface for the insights (money management) domain. Thin: validation + delegation. */
@RestController
public class InsightsController {

  private final InsightsService service;

  public InsightsController(InsightsService service) {
    this.service = service;
  }

  @PostMapping("/budgets")
  public ResponseEntity<BudgetView> createBudget(@Valid @RequestBody CreateBudget req) {
    BudgetView view = service.createBudget(req);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(view.id())
            .toUri();
    return ResponseEntity.created(location).body(view);
  }

  @GetMapping("/budgets/{id}")
  public BudgetView getBudget(@PathVariable UUID id) {
    return service.getBudget(id);
  }

  @GetMapping("/budgets")
  public List<BudgetView> listBudgets() {
    return service.listBudgets();
  }

  @PostMapping("/budgets/{id}/spend")
  public BudgetView spend(@PathVariable UUID id, @Valid @RequestBody RecordSpend req) {
    return service.spend(id, req);
  }

  @PostMapping("/transactions")
  public TransactionView recordTransaction(@Valid @RequestBody RecordTransaction req) {
    return service.recordTransaction(req);
  }

  @GetMapping("/transactions")
  public List<TransactionView> listTransactions() {
    return service.listTransactions();
  }

  @GetMapping("/transactions/by-category")
  public Map<String, BigDecimal> spendByCategory() {
    return service.spendByCategory();
  }
}