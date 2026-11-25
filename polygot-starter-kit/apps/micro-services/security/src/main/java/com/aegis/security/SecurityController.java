package com.aegis.security;
import java.util.*; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/security")
public class SecurityController {
  private final SecurityService s; public SecurityController(SecurityService s){this.s=s;}
  @PostMapping("/devices") public ResponseEntity<Device> register(@RequestParam String customerId, @RequestParam String fingerprint){ Device d=s.register(customerId,fingerprint); return ResponseEntity.ok(d); }
  @PostMapping("/devices/{id}/trust") public Device trust(@PathVariable UUID id){ return s.trust(id); }
  @GetMapping("/devices") public List<Device> devices(){ return s.devices(); }
  @GetMapping("/risk") public double risk(@RequestParam String customerId, @RequestParam double amount){ return s.risk(customerId,amount); }
  @PostMapping("/freeze") public void freeze(@RequestParam String customerId){ s.freeze(customerId); }
  @GetMapping("/frozen") public boolean frozen(@RequestParam String customerId){ return s.isFrozen(customerId); }
}
