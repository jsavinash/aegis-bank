package com.aegis.gateway;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Applies the rate limit to every request, keyed by client IP. Over-limit requests get a 429 with a
 * {@code Retry-After} header; allowed requests carry {@code X-RateLimit-*}.
 */
@Component
public class RateLimitFilter extends OncePerRequestFilter {

  private final RateLimiter limiter;

  public RateLimitFilter(RateLimiter limiter) {
    this.limiter = limiter;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    String key = clientKey(request);
    if (!limiter.tryAcquire(key)) {
      response.setStatus(429);
      response.setHeader("Retry-After", String.valueOf(limiter.window().toSeconds()));
      response.getWriter().write("{\"error\":\"rate_limited\"}");
      return;
    }
    response.setHeader("X-RateLimit-Limit", String.valueOf(limiter.permits()));
    chain.doFilter(request, response);
  }

  private String clientKey(HttpServletRequest request) {
    String forwarded = request.getHeader("X-Forwarded-For");
    if (forwarded != null && !forwarded.isBlank()) {
      return forwarded.split(",")[0].trim();
    }
    return request.getRemoteAddr();
  }
}