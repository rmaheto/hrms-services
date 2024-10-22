package org.muhikira.authservice.dto;

import java.util.Set;
import lombok.Data;
import org.muhikira.authservice.model.RoleName;

@Data
public class UserRequestDto {
  private String username;
  private String password;
  private Set<RoleName> roles;
}
