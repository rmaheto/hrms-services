package org.muhikira.authservice.dto;

import java.util.Set;
import lombok.Data;
import org.muhikira.authservice.model.RoleName;
@Data
public class UserUpdateRequestDto {
  private String username;
  private Set<RoleName> roles;
}
