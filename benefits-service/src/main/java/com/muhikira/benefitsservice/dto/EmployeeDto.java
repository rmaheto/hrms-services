package com.muhikira.benefitsservice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
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
  private Long positionId;
  private DepartmentDto department;
  private LocalDate hireDate;
  private BigDecimal salary;

  /**
   * Calculates the number of years the employee has been with the company.
   * @return number of years since hireDate, or 0 if hireDate is null
   */
  public int getServiceYears() {
    return (hireDate != null) ? Period.between(hireDate, LocalDate.now()).getYears() : 0;
  }

  /**
   * Calculates the employee's age based on dateOfBirth.
   * @return age in years, or 0 if dateOfBirth is null
   */
  public int getAge() {
    return (dateOfBirth != null) ? Period.between(dateOfBirth, LocalDate.now()).getYears() : 0;
  }
}
