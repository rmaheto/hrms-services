package org.muhikira.departmentservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

  @Bean
  public WebClient.Builder webClientBuilder() {
    return WebClient.builder()
        .filter(
            (request, next) -> {
              String token = getTokenFromSecurityContext();

              ClientRequest modifiedRequest =
                  ClientRequest.from(request).header("Authorization", "Bearer " + token).build();

              return next.exchange(modifiedRequest);
            });
  }

  private String getTokenFromSecurityContext() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getCredentials() instanceof String token) {
      return token;
    }
    return null;
  }
}
