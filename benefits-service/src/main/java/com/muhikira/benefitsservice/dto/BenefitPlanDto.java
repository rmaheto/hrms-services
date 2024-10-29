package com.muhikira.benefitsservice.dto;

import com.muhikira.benefitsservice.enums.EmploymentType;
import com.muhikira.benefitsservice.enums.PlanLevel;
import java.math.BigDecimal;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BenefitPlanDto {

  private Long id;
  private String name;
  private String description;
  private String coverageType;
  private PlanLevel planLevel;
  private EmploymentType employmentType;
  private BigDecimal employerContribution;
  private BigDecimal employeeContribution;
  private Boolean isActive;

  // Eligibility fields
  private Integer minServiceYears;
  private Integer minAge;

  private Set<Long> eligibleDepartmentIds; // Eligible department IDs
  private Set<Long> eligiblePositionIds;   // Eligible position IDs
}
