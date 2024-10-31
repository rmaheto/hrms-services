package com.muhikira.benefitsservice.mapper;

import com.muhikira.benefitsservice.dto.EmployeeBenefitDto;
import com.muhikira.benefitsservice.dto.EmployeeBenefitPlanDto;
import com.muhikira.benefitsservice.entity.BenefitPlan;
import com.muhikira.benefitsservice.entity.EmployeeBenefit;

public class EmployeeBenefitPlanMapper {

  private EmployeeBenefitPlanMapper() {}

  public static EmployeeBenefitPlanDto toDto(BenefitPlan benefit) {
    EmployeeBenefitPlanDto dto = new EmployeeBenefitPlanDto();
    dto.setId(benefit.getId());
    dto.setDescription(benefit.getDescription());
    dto.setName(benefit.getName());
    dto.setPlanLevel(benefit.getPlanLevel());
    dto.setCoverageType(benefit.getCoverageType());
    dto.setEmployerContribution(benefit.getEmployerContribution());
    dto.setFixedEmployeeContribution(benefit.getFixedEmployeeContribution());
    dto.setIsActive(benefit.getIsActive());
    return dto;
  }

  public static EmployeeBenefit toEntity(EmployeeBenefitDto employeeBenefitDto) {
    return EmployeeBenefit.builder()
        .id(employeeBenefitDto.getId())
        .benefitPlanId(employeeBenefitDto.getBenefitPlan().getId())
        .startDate(employeeBenefitDto.getStartDate())
        .endDate(employeeBenefitDto.getEndDate())
        .status(employeeBenefitDto.getStatus())
        .notes(employeeBenefitDto.getNotes())
        .build();
  }
}
