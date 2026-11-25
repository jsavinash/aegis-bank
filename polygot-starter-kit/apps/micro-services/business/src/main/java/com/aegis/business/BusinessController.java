package com.aegis.business;

import com.aegis.business.command.CreateAccount;
import com.aegis.business.command.CreateInvoice;
import com.aegis.business.web.BusinessAccountView;
import com.aegis.business.web.InvoiceView;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/** REST surface for business banking. Thin: validation + delegation. */
@RestController
@RequestMapping("/business")
public class BusinessController {

  private final BusinessService service;

  public BusinessController(BusinessService service) {
    this.service = service;
  }

  @PostMapping("/accounts")
  public ResponseEntity<BusinessAccountView> createAccount(@Valid @RequestBody CreateAccount req) {
    BusinessAccountView view = service.createAccount(req);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(view.id())
            .toUri();
    return ResponseEntity.created(location).body(view);
  }

  @GetMapping("/accounts")
  public List<BusinessAccountView> listAccounts() {
    return service.listAccounts();
  }

  @GetMapping("/accounts/{id}")
  public BusinessAccountView getAccount(@PathVariable UUID id) {
    return service.getAccount(id);
  }

  @PostMapping("/invoices")
  public ResponseEntity<InvoiceView> createInvoice(@Valid @RequestBody CreateInvoice req) {
    InvoiceView view = service.createInvoice(req);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(view.id())
            .toUri();
    return ResponseEntity.created(location).body(view);
  }

  @GetMapping("/invoices")
  public List<InvoiceView> listInvoices() {
    return service.listInvoices();
  }

  @GetMapping("/invoices/{id}")
  public InvoiceView getInvoice(@PathVariable UUID id) {
    return service.getInvoice(id);
  }

  @PostMapping("/invoices/{id}/pay")
  public InvoiceView markPaid(@PathVariable UUID id) {
    return service.markPaid(id);
  }

  @PostMapping("/invoices/{id}/overdue")
  public InvoiceView markOverdue(@PathVariable UUID id) {
    return service.markOverdue(id);
  }

  @PostMapping("/invoices/{id}/void")
  public InvoiceView voidInvoice(@PathVariable UUID id) {
    return service.voidInvoice(id);
  }
}