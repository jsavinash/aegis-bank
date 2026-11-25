package com.aegis.gateway;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** Gateway control-plane endpoints: health and the route table. */
@RestController
public class GatewayController {

  @GetMapping("/health")
  public Map<String, Object> health() {
    return Map.of("ok", true, "service", "api-gateway");
  }

  @GetMapping("/routes")
  public Map<String, String> routes() {
    return RouteRegistry.all();
  }
}