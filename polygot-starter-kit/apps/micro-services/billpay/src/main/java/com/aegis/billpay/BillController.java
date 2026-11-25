package com.aegis.billpay;

import com.aegis.billpay.command.AddBill;
import com.aegis.billpay.web.BillView;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/** REST surface for bill pay. Thin: validation + delegation. */
@RestController
@RequestMapping("/bills")
public class BillController {

  private final BillService service;

  public BillController(BillService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<BillView> add(@Valid @RequestBody AddBill req) {
    BillView view = service.add(req);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(view.id())
            .toUri();
    return ResponseEntity.created(location).body(view);
  }

  @GetMapping("/{id}")
  public BillView get(@PathVariable UUID id) {
    return service.get(id);
  }

  @GetMapping
  public List<BillView> list() {
    return service.list();
  }

  @GetMapping("/overdue")
  public List<BillView> overdue() {
    return service.overdue();
  }

  @PostMapping("/{id}/autopay")
  public BillView enableAutopay(@PathVariable UUID id) {
    return service.enableAutopay(id);
  }

  @PostMapping("/{id}/pay")
  public BillView pay(@PathVariable UUID id) {
    return service.pay(id);
  }

  @PostMapping("/{id}/cancel")
  public BillView cancel(@PathVariable UUID id) {
    return service.cancel(id);
  }
}