package com.muhikira.benefitsservice.advice;

import com.muhikira.benefitsservice.exception.BenefitPlanNotFoundException;
import com.muhikira.benefitsservice.model.ApiResponse;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(BenefitPlanNotFoundException.class)
  public ResponseEntity<ApiResponse<Void>> handleBenefitPlanNotFound(BenefitPlanNotFoundException ex) {
    log.error("BenefitPlanNotFoundException: {}", ex.getMessage());
    ApiResponse<Void> response = new ApiResponse<>("Benefit plan not found", null);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
    log.error("IllegalArgumentException: {}", ex.getMessage());
    ApiResponse<Void> response = new ApiResponse<>("Invalid argument: " + ex.getMessage(), null);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
    log.error("Exception: {}", ex.getMessage(), ex);
    ApiResponse<Void> response = new ApiResponse<>("An unexpected error occurred", null);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

  // Optional: handle validation exceptions (for example, from @Valid annotations in DTOs)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(MethodArgumentNotValidException ex) {
    log.error("Validation error: {}", ex.getMessage());

    Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
        .collect(Collectors.toMap(
            fieldError -> {
              fieldError.getField();
              return fieldError.getField();
            },
            fieldError -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "Validation error",
            (existing, replacement) -> existing 
        ));

    ApiResponse<Map<String, String>> response = new ApiResponse<>("Validation failed", errors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }
}
