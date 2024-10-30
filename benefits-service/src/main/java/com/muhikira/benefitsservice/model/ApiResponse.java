package com.muhikira.benefitsservice.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
  private String message;
  private T data;
  private int statusCode;
  private boolean success;
  private LocalDateTime timestamp;

  // Constructor for successful responses without needing explicit statusCode or timestamp
  public ApiResponse(String message, T data) {
    this.message = message;
    this.data = data;
    this.success = true;
    this.statusCode = HttpStatus.OK.value();
    this.timestamp = LocalDateTime.now();
  }

  // Constructor for error responses
  public ApiResponse(String message, int statusCode) {
    this.message = message;
    this.data = null;
    this.success = false;
    this.statusCode = statusCode;
    this.timestamp = LocalDateTime.now();
  }
}
