package org.muhikira.attendanceservice.service.employee;


import lombok.RequiredArgsConstructor;
import org.muhikira.attendanceservice.dto.EmployeeDto;
import org.muhikira.attendanceservice.service.auth.AuthServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EmployeeServiceClient {
  private final WebClient.Builder webClientBuilder;
  private final AuthServiceClient authServiceClient;

  @Value("${employee-service.base-url}")
  private String employeeServiceBaseUrl;

  @Value("${employee-service.endpoints.get-employee-by-id}")
  private String getEmployeeByIdUri;

  public Mono<EmployeeDto> getEmployeeById(Long employeeId) {
    String token = authServiceClient.getJwtToken();

    return webClientBuilder
        .baseUrl(employeeServiceBaseUrl)
        .build()
        .get()
        .uri(getEmployeeByIdUri, employeeId)
        .headers(headers -> headers.setBearerAuth(token))  // Directly set the Bearer token here
        .retrieve()
        .bodyToMono(EmployeeDto.class)
        .onErrorResume(WebClientResponseException.Unauthorized.class, e -> {
          authServiceClient.evictJwtCache();  // Clear the cache if unauthorized
          return Mono.error(new RuntimeException("Employee Service authorization failed"));
        });
  }
}
