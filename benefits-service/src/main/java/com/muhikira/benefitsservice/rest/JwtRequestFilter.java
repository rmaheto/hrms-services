package com.muhikira.benefitsservice.rest;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    final String authorizationHeader = request.getHeader("Authorization");
    String username = null;
    String jwt = null;

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jwt = authorizationHeader.substring(7);
      username = jwtUtil.extractUsername(jwt);
    }

    if (username != null
        && SecurityContextHolder.getContext().getAuthentication() == null
        && Boolean.TRUE.equals(jwtUtil.validateToken(jwt, username))) {

      // Extract roles from JWT
      Set<SimpleGrantedAuthority> authorities = jwtUtil.extractRoles(jwt).stream()
          .map(SimpleGrantedAuthority::new)
          .collect(Collectors.toSet());

      // Set authentication object with roles
      UsernamePasswordAuthenticationToken authToken =
          new UsernamePasswordAuthenticationToken(username, null, authorities);

      authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authToken);
      }


    chain.doFilter(request, response);
  }
}
