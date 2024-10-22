package org.muhikira.departmentservice.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponse {
  private int status;
  private String message;
  private long timestamp;

}
