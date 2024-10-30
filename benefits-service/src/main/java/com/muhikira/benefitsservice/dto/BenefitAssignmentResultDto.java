package com.muhikira.benefitsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BenefitAssignmentResultDto {
  private String status;
  private String message;
  private EmployeeBenefitDto assignedBenefit;
}
