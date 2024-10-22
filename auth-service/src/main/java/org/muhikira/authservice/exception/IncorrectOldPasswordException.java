package org.muhikira.authservice.exception;

public class IncorrectOldPasswordException extends RuntimeException {
  public IncorrectOldPasswordException(String message) {
    super(message);
  }
}
