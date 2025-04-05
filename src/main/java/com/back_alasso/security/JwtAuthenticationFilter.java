package com.back_alasso.security;

import static com.back_alasso.security.SecurityConstants.PUBLIC_URLS;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException, ServletException, IOException {
    // Skip JWT filter for public URLs
    String requestURI = request.getRequestURI();
    if (PUBLIC_URLS.stream().anyMatch(requestURI::startsWith)) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      String jwt = parseJwt(request);
      // If no token is provided, continue (unauthenticated request)
      if (jwt == null) {
        filterChain.doFilter(request, response);
        return;
      }
      boolean isTokenValid = jwtService.validateJwtToken(jwt, response);
      System.out.println("isTokenValid" + isTokenValid);

      // If no token is invalid, send error to front to redirect user to auth page
      if (isTokenValid == false) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"message\": \"Token expiré\", \"error\": \"INVALID_TOKEN\"}");
        response.getWriter().flush(); // send immediately the response
        return;
      }

      String username = jwtService.extractClaims(jwt).getSubject();
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      System.out.println("userDetails" + userDetails);
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (Exception e) {
      System.out.println("Cannot set user authentication: " + e);
    }
    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");
    String bearer = "Bearer ";
    if (headerAuth != null && headerAuth.startsWith(bearer)) {
      return headerAuth.substring(bearer.length());
    }
    return null;
  }
}
