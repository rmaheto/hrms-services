package com.muhikira.benefitsservice.dto;

import com.muhikira.benefitsservice.enums.PlanLevel;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class EmployeeBenefitPlanDto {
  private Long id;
  private String name;
  private String description;
  private String coverageType;
  private PlanLevel planLevel;
  private BigDecimal employerContribution;
  private BigDecimal fixedEmployeeContribution;
  private Boolean isActive;
  private Integer minServiceYears;
  private Integer minAge;
}
