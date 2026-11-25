package com.aegis.business;
import java.math.BigDecimal; import java.util.*; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/business")
public class BusinessController {
  private final BusinessService s; public BusinessController(BusinessService s){this.s=s;}
  @PostMapping("/accounts") public ResponseEntity<BusinessAccount> account(@RequestParam String name, @RequestParam String taxId){ BusinessAccount a=s.account(name,taxId); return ResponseEntity.ok(a); }
  @GetMapping("/accounts") public List<BusinessAccount> accounts(){ return s.accounts(); }
  @PostMapping("/invoices") public ResponseEntity<Invoice> invoice(@RequestParam UUID accountId, @RequestParam BigDecimal amount){ Invoice i=s.invoice(accountId,amount); return ResponseEntity.ok(i); }
  @GetMapping("/invoices") public List<Invoice> invoices(){ return s.invoices(); }
}
