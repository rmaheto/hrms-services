package com.muhikira.hrms.exception;

public class DepartmentNotFoundException extends RuntimeException {
  public DepartmentNotFoundException(String message) {
    super(message);
  }
}
