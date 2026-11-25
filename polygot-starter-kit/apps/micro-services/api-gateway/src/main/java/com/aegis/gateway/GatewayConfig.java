package com.aegis.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Wires the gateway's rate limiter as a singleton bean. */
@Configuration
public class GatewayConfig {

  @Bean
  public RateLimiter rateLimiter() {
    return new RateLimiter(100, java.time.Duration.ofMinutes(1));
  }
}