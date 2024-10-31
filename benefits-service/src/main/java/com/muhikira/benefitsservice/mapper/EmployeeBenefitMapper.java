package com.muhikira.benefitsservice.mapper;

import com.muhikira.benefitsservice.dto.BenefitPlanDto;
import com.muhikira.benefitsservice.dto.EmployeeBenefitDto;
import com.muhikira.benefitsservice.entity.EmployeeBenefit;

public class EmployeeBenefitMapper {

  private EmployeeBenefitMapper() {}

  public static EmployeeBenefitDto toDto(EmployeeBenefit benefit) {
    EmployeeBenefitDto dto = new EmployeeBenefitDto();
    dto.setId(benefit.getId());
    dto.setStartDate(benefit.getStartDate());
    dto.setEndDate(benefit.getEndDate());
    dto.setStatus(benefit.getStatus());
    dto.setNotes(benefit.getNotes());
    dto.setEmployeeContribution(benefit.getEmployeeContribution());
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
