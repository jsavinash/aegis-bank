package com.aegis.payments;

import com.aegis.payments.command.InitiatePayment;
import com.aegis.payments.web.PaymentView;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/** REST surface for payments. Thin: validation + delegation. */
@RestController
@RequestMapping("/payments")
public class PaymentController {

  private final PaymentService service;

  public PaymentController(PaymentService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<PaymentView> initiate(@Valid @RequestBody InitiatePayment req) {
    PaymentView view = service.initiate(req);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(view.id())
            .toUri();
    return ResponseEntity.created(location).body(view);
  }

  @GetMapping("/{id}")
  public PaymentView get(@PathVariable UUID id) {
    return service.get(id);
  }

  @GetMapping
  public List<PaymentView> list() {
    return service.list();
  }

  @PostMapping("/{id}/process")
  public PaymentView process(@PathVariable UUID id) {
    return service.process(id);
  }

  @PostMapping("/{id}/complete")
  public PaymentView complete(@PathVariable UUID id) {
    return service.complete(id);
  }

  @PostMapping("/{id}/fail")
  public PaymentView fail(@PathVariable UUID id) {
    return service.fail(id);
  }

  @PostMapping("/{id}/cancel")
  public PaymentView cancel(@PathVariable UUID id) {
    return service.cancel(id);
  }

  @PostMapping("/{id}/recall")
  public PaymentView recall(@PathVariable UUID id) {
    return service.recall(id);
  }
}