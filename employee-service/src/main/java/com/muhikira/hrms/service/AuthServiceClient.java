package com.muhikira.hrms.service;

import com.muhikira.hrms.dto.UserRequestDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthServiceClient {

  private final WebClient.Builder webClientBuilder;

  @Value("${auth-service.base-url}")
  private String baseUrl;

  @Value("${auth-service.endpoints.create}")
  private String createUserUri;

  @CircuitBreaker(name = "authServiceCircuitBreaker", fallbackMethod = "authServiceFallback")
  public Mono<Void> createUser(UserRequestDto userDto) {

    String jwtToken = extractJwtFromRequest();
    String uri = baseUrl + createUserUri;

    return webClientBuilder.build()
        .post()
        .uri(uri)
        .headers(headers -> headers.setBearerAuth(jwtToken))
        .bodyValue(userDto)
        .retrieve()
        .bodyToMono(Void.class)
        .doOnError(e -> {
          throw new RuntimeException("Failed to create user in Auth Service", e);
        });
  }

  public Mono<Void> authServiceFallback(UserRequestDto userDto, Throwable throwable) {
    return Mono.error(new RuntimeException("Auth Service is currently unavailable", throwable));
  }

  private String extractJwtFromRequest() {
    HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(
        RequestContextHolder.getRequestAttributes())).getRequest();

    String authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      return authorizationHeader.substring(7);  // Strip "Bearer " prefix
    }

    throw new RuntimeException("No Authorization header found or token is invalid");
  }
}

