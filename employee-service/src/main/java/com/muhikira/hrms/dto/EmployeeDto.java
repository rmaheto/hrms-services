package com.muhikira.hrms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private LocalDate dateOfBirth;
  private String placeOfBirth;
  private String position;
  private DepartmentDto department;
  private LocalDate hireDate;
  private BigDecimal salary;
}
