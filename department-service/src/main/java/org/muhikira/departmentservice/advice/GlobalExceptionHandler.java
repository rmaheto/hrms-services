package org.muhikira.departmentservice.advice;

import org.muhikira.departmentservice.exception.DepartmentNotFoundException;
import org.muhikira.departmentservice.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(DepartmentNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleDepartmentNotFoundException(DepartmentNotFoundException ex) {
    ErrorResponse error = new ErrorResponse(
        HttpStatus.NOT_FOUND.value(),
        ex.getMessage(),
        System.currentTimeMillis()
    );
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

}
