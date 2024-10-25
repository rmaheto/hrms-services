package com.muhikira.hrms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Data
public class EmployeeUpdateRequestDto {
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private LocalDate dateOfBirth;
  private String placeOfBirth;
  private String position;
  private Long departmentId;
  private LocalDate hireDate;
  private BigDecimal salary;
}
