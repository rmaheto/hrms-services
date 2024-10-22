package org.muhikira.attendanceservice.service.auth;

import static org.muhikira.attendanceservice.util.AppConstants.JWT_TOKEN_CACHE;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.muhikira.attendanceservice.model.AuthRequest;
import org.muhikira.attendanceservice.model.AuthResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceClient {

  private final WebClient authServiceWebClient;

  @Value("${auth-service.base-url}")
  private String authServiceBaseUrl;

  @Value("${auth-service.endpoints.get-jwt}")
  private String getJwtUri;

  @Cacheable(value = JWT_TOKEN_CACHE, unless = "#result == null || #result.isEmpty()")
  public String getJwtToken() {
    AuthRequest authRequest = new AuthRequest("admin", "admin");
    try {
      AuthResponse authResponse =
          authServiceWebClient
              .post()
              .uri(authServiceBaseUrl + getJwtUri)
              .bodyValue(authRequest)
              .retrieve()
              .bodyToMono(AuthResponse.class)
              .block();

      return authResponse != null ? authResponse.jwt() : null;
    } catch (WebClientResponseException e) {
      log.error("Error retrieving JWT token from Auth Service: {}", e.getMessage());
      throw new RuntimeException("Failed to retrieve JWT token from Auth Service", e);
    }
  }

  @CacheEvict(value = "jwtTokenCache", allEntries = true)
  public void evictJwtCache() {
    log.info("Evicted JWT token cache for Auth Service");
  }
}
