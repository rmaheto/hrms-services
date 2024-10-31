package com.muhikira.benefitsservice.mapper;

import com.muhikira.benefitsservice.dto.BenefitPlanDto;
import com.muhikira.benefitsservice.entity.BenefitPlan;

public class BenefitPlanMapper {

  public static BenefitPlanDto toDto(BenefitPlan benefitPlan) {
    return new BenefitPlanDto(
        benefitPlan.getId(),
        benefitPlan.getName(),
        benefitPlan.getDescription(),
        benefitPlan.getCoverageType(),
        benefitPlan.getPlanLevel(),
        benefitPlan.getEmploymentType(),
        benefitPlan.getEmployerContribution(),
        benefitPlan.getFixedEmployeeContribution(),
        benefitPlan.getIsActive(),
        benefitPlan.getMinServiceYears(),
        benefitPlan.getMinAge(),
        benefitPlan.getEligibleDepartmentIds(),
        benefitPlan.getEligiblePositionIds()
    );
  }

  public static BenefitPlan toEntity(BenefitPlanDto benefitPlanDto) {
    return BenefitPlan.builder()
        .id(benefitPlanDto.getId())
        .name(benefitPlanDto.getName())
        .description(benefitPlanDto.getDescription())
        .coverageType(benefitPlanDto.getCoverageType())
        .planLevel(benefitPlanDto.getPlanLevel())
        .employmentType(benefitPlanDto.getEmploymentType())
        .employerContribution(benefitPlanDto.getEmployerContribution())
        .fixedEmployeeContribution(benefitPlanDto.getFixedEmployeeContribution())
        .minServiceYears(benefitPlanDto.getMinServiceYears())
        .minAge(benefitPlanDto.getMinAge())
        .eligibleDepartmentIds(benefitPlanDto.getEligibleDepartmentIds())
        .eligiblePositionIds(benefitPlanDto.getEligiblePositionIds())
        .isActive(benefitPlanDto.getIsActive())
        .build();
  }
}
