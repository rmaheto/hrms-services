package com.muhikira.benefitsservice.entity;

import com.muhikira.benefitsservice.enums.BenefitStatus;
import com.muhikira.benefitsservice.listener.AuditListener;
import com.muhikira.benefitsservice.model.audit.Audit;
import com.muhikira.benefitsservice.model.audit.Auditable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
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
public class EmployeeBenefit implements Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long employeeId;
  private Long benefitPlanId;
  private LocalDate startDate;
  private LocalDate endDate;

  @Enumerated(EnumType.STRING)
  private BenefitStatus status;

  private String notes;
  @Embedded
  private Audit audit;
}
