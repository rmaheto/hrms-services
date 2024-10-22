package org.muhikira.authservice.service;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.muhikira.authservice.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  
  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;

  public String authenticateAndGenerateToken(String username, String password) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password)
    );

    final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    Set<String> roles = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toSet());

    return jwtUtil.generateToken(userDetails.getUsername(), roles);
  }
  
}
