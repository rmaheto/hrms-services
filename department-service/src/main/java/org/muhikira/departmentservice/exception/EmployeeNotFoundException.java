package org.muhikira.departmentservice.exception;

public class EmployeeNotFoundException extends RuntimeException {
  public EmployeeNotFoundException(String message) {
    super(message);
  }
}
