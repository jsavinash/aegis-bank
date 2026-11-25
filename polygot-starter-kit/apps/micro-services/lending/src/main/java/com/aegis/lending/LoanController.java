package com.aegis.lending;
import java.net.URI; import java.util.*; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/loans")
public class LoanController {
  private final LoanService s; public LoanController(LoanService s){this.s=s;}
  @PostMapping public ResponseEntity<Loan> apply(@RequestBody NewLoan r){ Loan l=s.apply(r); return ResponseEntity.created(URI.create("/loans/"+l.id())).body(l); }
  @GetMapping("/{id}") public ResponseEntity<Loan> get(@PathVariable UUID id){ return s.get(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }
  @GetMapping public List<Loan> list(){ return s.list(); }
  @PostMapping("/{id}/decision") public Loan decide(@PathVariable UUID id, @RequestParam String status){ return s.decide(id,status); }
}
