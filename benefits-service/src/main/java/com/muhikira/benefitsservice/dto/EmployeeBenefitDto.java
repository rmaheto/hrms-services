package com.muhikira.benefitsservice.dto;

import com.muhikira.benefitsservice.enums.BenefitStatus;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeBenefitDto {

  private Long id;
  private Long employeeId;
  private Long benefitPlanId;
  private LocalDate startDate;
  private LocalDate endDate;
  private BenefitStatus status;
  private String notes;
}