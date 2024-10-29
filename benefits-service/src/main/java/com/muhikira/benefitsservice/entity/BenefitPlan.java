package com.muhikira.benefitsservice.entity;

import com.muhikira.benefitsservice.enums.EmploymentType;
import com.muhikira.benefitsservice.enums.PlanLevel;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BenefitPlan {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;
  private String coverageType;

  @Enumerated(EnumType.STRING)
  private PlanLevel planLevel;

  @Enumerated(EnumType.STRING)
  private EmploymentType employmentType;

  private BigDecimal employerContribution;
  private BigDecimal employeeContribution;
  private Boolean isActive;

  // Eligibility criteria as direct fields
  private Integer minServiceYears;
  private Integer minAge;

  @ElementCollection
  private Set<Long> eligibleDepartmentIds;

  @ElementCollection
  private Set<Long> eligiblePositionIds;
}
