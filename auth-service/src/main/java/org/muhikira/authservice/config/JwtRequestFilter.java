package org.muhikira.authservice.config;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.muhikira.authservice.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

  private final UserDetailsService userDetailsService;

  private final JwtUtil jwtUtil;

  public static final String AUTHORIZATION = "Authorization";
  public static final String BEARER = "Bearer ";

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws ServletException, IOException {
    final String authorizationHeader = request.getHeader(AUTHORIZATION);

    String username = null;
    String jwt = null;

    if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
      jwt = authorizationHeader.substring(7);
      try {
        username = jwtUtil.extractUsername(jwt);
      } catch (ExpiredJwtException e) {
        log.error("JWT token has expired: {}", e.getMessage());
      } catch (Exception e) {
        log.error("Error occurred while parsing JWT: {}", e.getMessage());
      }
    }


    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

      if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    }

    chain.doFilter(request, response);
  }
}
