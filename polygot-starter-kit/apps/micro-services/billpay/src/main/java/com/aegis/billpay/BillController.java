package com.aegis.billpay;
import java.net.URI; import java.util.*; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/bills")
public class BillController {
  private final BillService s; public BillController(BillService s){this.s=s;}
  @PostMapping public ResponseEntity<Bill> add(@RequestBody NewBill r){ Bill b=s.add(r); return ResponseEntity.created(URI.create("/bills/"+b.id())).body(b); }
  @GetMapping("/{id}") public ResponseEntity<Bill> get(@PathVariable UUID id){ return s.get(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }
  @GetMapping public List<Bill> list(){ return s.list(); }
}
