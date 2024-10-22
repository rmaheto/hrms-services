package org.muhikira.departmentservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  @Value("${jwt.secret}")
  private String secret;

  public Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

  public String extractUsername(String token) {
    return extractAllClaims(token).getSubject();
  }

  public Boolean isTokenExpired(String token) {
    return extractAllClaims(token).getExpiration().before(new Date());
  }

  public Boolean validateToken(String token, String username) {
    final String tokenUsername = extractUsername(token);
    return (tokenUsername.equals(username) && !isTokenExpired(token));
  }
}
