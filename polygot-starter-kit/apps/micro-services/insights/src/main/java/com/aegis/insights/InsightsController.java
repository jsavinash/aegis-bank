package com.aegis.insights;
import java.math.BigDecimal; import java.net.URI; import java.util.*; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/budgets")
public class InsightsController {
  private final InsightsService s; public InsightsController(InsightsService s){this.s=s;}
  @PostMapping public ResponseEntity<Budget> create(@RequestBody NewBudget r){ Budget b=s.create(r); return ResponseEntity.created(URI.create("/budgets/"+b.id())).body(b); }
  @GetMapping("/{id}") public ResponseEntity<Budget> get(@PathVariable UUID id){ return s.get(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }
  @GetMapping public List<Budget> list(){ return s.list(); }
  @PostMapping("/{id}/spend") public Budget spend(@PathVariable UUID id, @RequestParam BigDecimal amount){ return s.spend(id,amount); }
}
