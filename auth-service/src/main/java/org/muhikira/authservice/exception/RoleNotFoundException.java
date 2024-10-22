package org.muhikira.authservice.exception;

public class RoleNotFoundException extends RuntimeException{

  public RoleNotFoundException(String msg){
    super(msg);
  }
}
