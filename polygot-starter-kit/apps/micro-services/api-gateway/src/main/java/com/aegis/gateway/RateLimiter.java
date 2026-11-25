package com.aegis.gateway;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * In-memory token-bucket rate limiter keyed by client identity (IP or API key). Each key gets
 * {@code permits} requests per {@code window}; over-limit requests are rejected with 429.
 */
public final class RateLimiter {

  private static final class Bucket {
    final AtomicInteger count = new AtomicInteger(0);
    long windowStart;

    Bucket(long windowStart) {
      this.windowStart = windowStart;
    }
  }

  private final int permits;
  private final Duration window;
  private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

  public RateLimiter(int permits, Duration window) {
    if (permits <= 0) {
      throw new IllegalArgumentException("permits must be positive");
    }
    this.permits = permits;
    this.window = window;
  }

  /** Returns true if the request is allowed, false if it exceeds the limit. */
  public boolean tryAcquire(String key) {
    long now = System.currentTimeMillis();
    Bucket bucket = buckets.computeIfAbsent(key, k -> new Bucket(now));
    synchronized (bucket) {
      if (now - bucket.windowStart >= window.toMillis()) {
        bucket.count.set(0);
        bucket.windowStart = now;
      }
      return bucket.count.incrementAndGet() <= permits;
    }
  }

  public int permits() {
    return permits;
  }

  public Duration window() {
    return window;
  }
}