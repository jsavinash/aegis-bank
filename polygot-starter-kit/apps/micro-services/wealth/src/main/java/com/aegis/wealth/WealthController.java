package com.aegis.wealth;
import java.math.BigDecimal; import java.net.URI; import java.util.*; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/goals")
public class WealthController {
  private final WealthService s; public WealthController(WealthService s){this.s=s;}
  @PostMapping public ResponseEntity<Goal> create(@RequestBody NewGoal r){ Goal g=s.create(r); return ResponseEntity.created(URI.create("/goals/"+g.id())).body(g); }
  @GetMapping("/{id}") public ResponseEntity<Goal> get(@PathVariable UUID id){ return s.get(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }
  @GetMapping public List<Goal> list(){ return s.list(); }
  @PostMapping("/{id}/contribute") public Goal contribute(@PathVariable UUID id, @RequestParam BigDecimal amount){ return s.contribute(id,amount); }
}
