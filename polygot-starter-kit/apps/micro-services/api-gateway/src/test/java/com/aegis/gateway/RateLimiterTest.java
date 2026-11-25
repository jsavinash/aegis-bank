package com.aegis.gateway;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import org.junit.jupiter.api.Test;

class RateLimiterTest {

  @Test
  void allowsUpToLimitThenRejects() {
    RateLimiter limiter = new RateLimiter(3, Duration.ofMinutes(1));
    assertTrue(limiter.tryAcquire("ip1"));
    assertTrue(limiter.tryAcquire("ip1"));
    assertTrue(limiter.tryAcquire("ip1"));
    assertFalse(limiter.tryAcquire("ip1"));
  }

  @Test
  void keysAreIndependent() {
    RateLimiter limiter = new RateLimiter(1, Duration.ofMinutes(1));
    assertTrue(limiter.tryAcquire("a"));
    assertFalse(limiter.tryAcquire("a"));
    assertTrue(limiter.tryAcquire("b"));
  }

  @Test
  void rejectsNonPositivePermits() {
    assertThrows(IllegalArgumentException.class, () -> new RateLimiter(0, Duration.ofMinutes(1)));
  }

  @Test
  void windowResetsAfterExpiry() throws Exception {
    RateLimiter limiter = new RateLimiter(1, Duration.ofMillis(50));
    assertTrue(limiter.tryAcquire("k"));
    assertFalse(limiter.tryAcquire("k"));
    Thread.sleep(80);
    assertTrue(limiter.tryAcquire("k"));
  }
}