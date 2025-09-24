package com.back_alasso.security;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import io.github.bucket4j.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.Duration;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class LoginRateLimitFilter extends OncePerRequestFilter {

  private final Integer SIXTY = 60;
  private final Integer TEN_THOUSAND = 10_000;
  private final Integer FIVE = 5;
  private final Integer FIFTEEN = 15;
  private final Long ONE_BILLION = 1_000_000_000L;
  private final Integer FOUR_HUNDRED_AND_TWENTY_NINE = 429;

  // cache buckets per-key (IP or username)
  private final LoadingCache<String, Bucket> cache = Caffeine.newBuilder()
    .expireAfterAccess(Duration.ofMinutes(SIXTY))
    .maximumSize(TEN_THOUSAND)
    .build(this::newBucket);

  private Bucket newBucket(String key) {
    // 5 attempts every 15 minutes (burst=5)
    return Bucket.builder().addLimit(limit -> limit.capacity(FIVE).refillGreedy(1, Duration.ofMinutes(FIFTEEN))).build();
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
    if (!"/auth/login".equalsIgnoreCase(request.getRequestURI()) || !"POST".equalsIgnoreCase(request.getMethod())) {
      filterChain.doFilter(request, response);
      return;
    }

    String key = extractKey(request); // username param OR remote IP
    Bucket bucket = cache.get(key);
    ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

    if (probe.isConsumed()) {
      response.setHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
      filterChain.doFilter(request, response);
    } else {
      // too many attempts
      long waitForSecs = probe.getNanosToWaitForRefill() / ONE_BILLION;
      response.setStatus(FOUR_HUNDRED_AND_TWENTY_NINE);
      response.setHeader("Retry-After", String.valueOf(waitForSecs));
      response.getWriter().write("Too many login attempts. Try later.");
    }
  }

  private String extractKey(HttpServletRequest request) {
    String username = request.getParameter("username");
    if (username != null && !username.isBlank()) return "usr:" + username.toLowerCase();
    String ip = request.getHeader("X-Forwarded-For");
    if (ip == null) ip = request.getRemoteAddr();
    return "ip:" + ip;
  }
}
