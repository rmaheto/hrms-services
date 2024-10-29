package com.muhikira.benefitsservice.mapper;

import com.muhikira.benefitsservice.dto.EmployeeBenefitDto;
import com.muhikira.benefitsservice.entity.EmployeeBenefit;

public class EmployeeBenefitMapper {

  public static EmployeeBenefitDto toDto(EmployeeBenefit employeeBenefit) {
    return new EmployeeBenefitDto(
        employeeBenefit.getId(),
        employeeBenefit.getEmployeeId(),
        employeeBenefit.getBenefitPlanId(),
        employeeBenefit.getStartDate(),
        employeeBenefit.getEndDate(),
        employeeBenefit.getStatus(),
        employeeBenefit.getNotes()
    );
  }

  public static EmployeeBenefit toEntity(EmployeeBenefitDto employeeBenefitDto) {
    return EmployeeBenefit.builder()
        .id(employeeBenefitDto.getId())
        .employeeId(employeeBenefitDto.getEmployeeId())
        .benefitPlanId(employeeBenefitDto.getBenefitPlanId())
        .startDate(employeeBenefitDto.getStartDate())
        .endDate(employeeBenefitDto.getEndDate())
        .status(employeeBenefitDto.getStatus())
        .notes(employeeBenefitDto.getNotes())
        .build();
  }
}
