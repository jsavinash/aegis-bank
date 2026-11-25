package com.aegis.payments;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {
  private final PaymentService service;

  public PaymentController(PaymentService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<Payment> initiate(@RequestBody NewPayment req) {
    Payment p = service.initiate(req);
    return ResponseEntity.created(URI.create("/payments/" + p.id())).body(p);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Payment> get(@PathVariable UUID id) {
    return service.get(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public List<Payment> list() {
    return service.list();
  }
}
