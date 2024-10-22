package org.muhikira.departmentservice.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.muhikira.departmentservice.Dto.EmployeeDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceClient {

  private final WebClient.Builder webClientBuilder;

  @Value("${employee-service.base-url}")
  private String baseUrl;

  @Value("${employee-service.endpoints.get-employee-by-id}")
  private String getEmployeeByIdUri;

  @Value("${employee-service.endpoints.get-employees-by-department-id}")
  private String getEmployeesByDepartmentIdUri;

  private WebClient getWebClient() {
    return webClientBuilder.baseUrl(baseUrl).build();
  }

  @CircuitBreaker(
      name = "employeeServiceCircuitBreaker",
      fallbackMethod = "getEmployeeByIdFallback")
  public Mono<EmployeeDto> getEmployeeById(Long employeeId) {
    return getWebClient()
        .get()
        .uri(getEmployeeByIdUri, employeeId)
        .retrieve()
        .bodyToMono(EmployeeDto.class)
        .onErrorResume(
            WebClientResponseException.class,
            e -> {
              log.error("Error fetching employee with ID {} : {}", employeeId, e.getMessage());
              return Mono.error(
                  new RuntimeException("Employee Service is unavailable, please try again later."));
            });
  }

  @CircuitBreaker(
      name = "employeeServiceCircuitBreaker",
      fallbackMethod = "getEmployeesByDepartmentIdFallback")
  public Mono<List<EmployeeDto>> getEmployeesByDepartmentId(Long departmentId) {
    String uri = baseUrl + getEmployeesByDepartmentIdUri.replace("{departmentId}", departmentId.toString());

    return getWebClient()
        .get()
        .uri(uri)
        .retrieve()
        .bodyToFlux(EmployeeDto.class)
        .collectList();
  }

  public Mono<EmployeeDto> getEmployeeByIdFallback(Long employeeId, Throwable throwable) {
    log.error("Fallback for getEmployeeById {} : {} ",employeeId, throwable.getMessage());
    return Mono.empty();
  }

  public Mono<List<EmployeeDto>> getEmployeesByDepartmentIdFallback(
      Long departmentId, Throwable throwable) {
    log.error("Fallback for getEmployeesByDepartmentId {} : {}",departmentId, throwable.getMessage());
    return Mono.just(List.of());
  }
}
