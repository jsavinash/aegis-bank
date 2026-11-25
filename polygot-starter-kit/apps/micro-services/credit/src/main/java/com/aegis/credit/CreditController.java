package com.aegis.credit;
import java.net.URI; import java.util.*; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/credit")
public class CreditController {
  private final CreditService s; public CreditController(CreditService s){this.s=s;}
  @PostMapping("/profiles") public ResponseEntity<CreditProfile> upsert(@RequestParam String customerId, @RequestParam int score){ CreditProfile p=s.upsert(customerId,score); return ResponseEntity.created(URI.create("/credit/profiles/"+p.id())).body(p); }
  @GetMapping("/profiles/{id}") public ResponseEntity<CreditProfile> get(@PathVariable UUID id){ return s.get(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }
  @GetMapping("/profiles") public List<CreditProfile> list(){ return s.list(); }
  @GetMapping("/simulate") public int simulate(@RequestParam int base, @RequestParam int delta){ return s.simulate(base,delta); }
}
