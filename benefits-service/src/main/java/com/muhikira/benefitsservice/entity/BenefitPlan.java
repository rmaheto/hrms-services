package com.muhikira.benefitsservice.entity;

import com.muhikira.benefitsservice.enums.EmploymentType;
import com.muhikira.benefitsservice.enums.PlanLevel;
import com.muhikira.benefitsservice.listener.AuditListener;
import com.muhikira.benefitsservice.model.audit.Audit;
import com.muhikira.benefitsservice.model.audit.Auditable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@EntityListeners(AuditListener.class)
public class BenefitPlan implements Auditable {

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
  private BigDecimal fixedEmployeeContribution;
  private Boolean isActive;
  private Integer minServiceYears;
  private Integer minAge;

  @ElementCollection
  private Set<Long> eligibleDepartmentIds;

  @ElementCollection
  private Set<Long> eligiblePositionIds;

  @Embedded private Audit audit;
}
