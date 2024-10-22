package com.muhikira.hrms;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.muhikira.hrms.repository")
@EntityScan(basePackages = "com.muhikira.hrms.model") @OpenAPIDefinition(info = @Info(title = "List of API", version = "1.0", description = "All the APIs Information"))
public class HrmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrmsApplication.class, args);
    }
}

