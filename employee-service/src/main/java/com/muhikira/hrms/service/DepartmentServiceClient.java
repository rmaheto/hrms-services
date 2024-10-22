package com.muhikira.hrms.service;

import com.muhikira.hrms.dto.DepartmentDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DepartmentServiceClient {

  private final WebClient.Builder webClientBuilder;

  @Value("${department-service.base-url}")
  private String baseUrl;

  @Value("${department-service.endpoints.get-department-by-id}")
  private String getDepartmentByIdUri;

  @CircuitBreaker(name = "departmentServiceCircuitBreaker", fallbackMethod = "departmentServiceFallback")
  public Mono<DepartmentDto> getDepartmentById(Long departmentId) {
    String uri = baseUrl + getDepartmentByIdUri.replace("{id}", departmentId.toString());

    return webClientBuilder.build()
        .get()
        .uri(uri)
        .retrieve()
        .bodyToMono(DepartmentDto.class);
  }

  public Mono<DepartmentDto> departmentServiceFallback(Long departmentId, Throwable throwable) {
    DepartmentDto defaultDepartment = new DepartmentDto();  // Default fallback response
    defaultDepartment.setDepartmentName("Fallback Department");
    defaultDepartment.setDepartmentCode("N/A");
    return Mono.just(defaultDepartment);
  }
}
