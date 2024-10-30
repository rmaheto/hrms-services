package com.muhikira.benefitsservice.service;

import com.muhikira.benefitsservice.dto.BenefitPlanDto;
import com.muhikira.benefitsservice.entity.BenefitPlan;
import com.muhikira.benefitsservice.exception.BenefitPlanNotFoundException;
import com.muhikira.benefitsservice.mapper.BenefitPlanMapper;
import com.muhikira.benefitsservice.repository.BenefitPlanRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BenefitPlanService {

  private final BenefitPlanRepository benefitPlanRepository;

  private static final String BENEFIT_NOT_FOUND = "Benefit plan not found";

  public BenefitPlanDto createBenefitPlan(BenefitPlanDto benefitPlanDto) {
    BenefitPlan benefitPlan = BenefitPlanMapper.toEntity(benefitPlanDto);
    return BenefitPlanMapper.toDto(benefitPlanRepository.save(benefitPlan));
  }

  public BenefitPlanDto updateBenefitPlan(Long id, BenefitPlanDto benefitPlanDto) {
    BenefitPlan existingPlan = benefitPlanRepository.findById(id)
        .orElseThrow(() -> new BenefitPlanNotFoundException(BENEFIT_NOT_FOUND));

    existingPlan.setName(benefitPlanDto.getName());
    existingPlan.setDescription(benefitPlanDto.getDescription());
    existingPlan.setCoverageType(benefitPlanDto.getCoverageType());
    existingPlan.setPlanLevel(benefitPlanDto.getPlanLevel());
    existingPlan.setEmploymentType(benefitPlanDto.getEmploymentType());
    existingPlan.setEmployerContribution(benefitPlanDto.getEmployerContribution());
    existingPlan.setEmployeeContribution(benefitPlanDto.getEmployeeContribution());
    existingPlan.setMinServiceYears(benefitPlanDto.getMinServiceYears());
    existingPlan.setMinAge(benefitPlanDto.getMinAge());
    existingPlan.setEligibleDepartmentIds(benefitPlanDto.getEligibleDepartmentIds());
    existingPlan.setEligiblePositionIds(benefitPlanDto.getEligiblePositionIds());

    return BenefitPlanMapper.toDto(benefitPlanRepository.save(existingPlan));
  }

  public void inactivateBenefitPlan(Long id) {
    BenefitPlan benefitPlan = benefitPlanRepository.findById(id)
        .orElseThrow(() -> new BenefitPlanNotFoundException(BENEFIT_NOT_FOUND));
    benefitPlan.setIsActive(false);
    benefitPlanRepository.save(benefitPlan);
  }

  public void activateBenefitPlan(Long id) {
    BenefitPlan benefitPlan = benefitPlanRepository.findById(id)
        .orElseThrow(() -> new BenefitPlanNotFoundException(BENEFIT_NOT_FOUND));
    benefitPlan.setIsActive(true);
    benefitPlanRepository.save(benefitPlan);
  }

  public BenefitPlanDto getBenefitPlanById(Long id) {
    BenefitPlan benefitPlan = benefitPlanRepository.findById(id)
        .orElseThrow(() -> new BenefitPlanNotFoundException(BENEFIT_NOT_FOUND));
    return BenefitPlanMapper.toDto(benefitPlan);
  }

  public List<BenefitPlanDto> getAllBenefitPlans() {
    return benefitPlanRepository.findAll().stream()
        .map(BenefitPlanMapper::toDto)
        .toList();
  }
}
