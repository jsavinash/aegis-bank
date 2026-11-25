package com.aegis.security;

import com.aegis.security.command.RaiseAlert;
import com.aegis.security.command.RegisterDevice;
import com.aegis.security.web.DeviceView;
import com.aegis.security.web.FraudAlertView;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/** REST surface for the security &amp; fraud domain. Thin: validation + delegation, no business logic. */
@RestController
public class SecurityController {

  private final SecurityService service;

  public SecurityController(SecurityService service) {
    this.service = service;
  }

  @PostMapping("/devices")
  public ResponseEntity<DeviceView> register(@Valid @RequestBody RegisterDevice req) {
    DeviceView view = service.registerDevice(req);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(view.id())
            .toUri();
    return ResponseEntity.created(location).body(view);
  }

  @GetMapping("/devices")
  public List<DeviceView> devices() {
    return service.listDevices();
  }

  @PostMapping("/devices/{id}/trust")
  public DeviceView trust(@PathVariable UUID id) {
    return service.trustDevice(id);
  }

  @PostMapping("/devices/{id}/revoke")
  public DeviceView revoke(@PathVariable UUID id) {
    return service.revokeDevice(id);
  }

  @PostMapping("/alerts")
  public ResponseEntity<FraudAlertView> raise(@Valid @RequestBody RaiseAlert req) {
    FraudAlertView view = service.raiseAlert(req);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(view.id())
            .toUri();
    return ResponseEntity.created(location).body(view);
  }

  @GetMapping("/alerts")
  public List<FraudAlertView> alerts() {
    return service.listAlerts();
  }

  @PostMapping("/alerts/{id}/confirm")
  public FraudAlertView confirm(@PathVariable UUID id) {
    return service.confirmAlert(id);
  }

  @PostMapping("/alerts/{id}/dismiss")
  public FraudAlertView dismiss(@PathVariable UUID id) {
    return service.dismissAlert(id);
  }

  @PostMapping("/freeze/{customerId}")
  public void freeze(@PathVariable String customerId) {
    service.freeze(customerId);
  }

  @GetMapping("/freeze/{customerId}")
  public boolean frozen(@PathVariable String customerId) {
    return service.isFrozen(customerId);
  }
}