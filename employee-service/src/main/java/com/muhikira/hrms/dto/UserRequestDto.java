package com.muhikira.hrms.dto;

import com.muhikira.hrms.model.RoleName;
import java.util.Set;
import lombok.Data;


@Data
public class UserRequestDto {
  private String username;
  private String password;
  private Set<RoleName> roles;
  private Long employeeId;
}
