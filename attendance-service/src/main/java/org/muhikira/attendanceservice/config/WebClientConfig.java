package org.muhikira.attendanceservice.config;

import static org.muhikira.attendanceservice.util.AppConstants.EMPLOYEE_SERVICE;
import static org.muhikira.attendanceservice.util.AppConstants.SERVICE_TYPE;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.muhikira.attendanceservice.model.AuthRequest;
import org.muhikira.attendanceservice.model.AuthResponse;
import org.muhikira.attendanceservice.service.auth.AuthServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
      return WebClient.builder();
    }

    @Bean
    public WebClient authServiceWebClient() {
      return WebClient.builder().build();
    }

}
