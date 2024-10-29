package com.muhikira.benefitsservice.service;

import com.muhikira.benefitsservice.dto.EmployeeBenefitDto;
import com.muhikira.benefitsservice.dto.EmployeeDto;
import com.muhikira.benefitsservice.entity.BenefitPlan;
import com.muhikira.benefitsservice.entity.EmployeeBenefit;
import com.muhikira.benefitsservice.enums.BenefitStatus;
import com.muhikira.benefitsservice.mapper.EmployeeBenefitMapper;
import com.muhikira.benefitsservice.repository.BenefitPlanRepository;
import com.muhikira.benefitsservice.repository.EmployeeBenefitRepository;
import com.muhikira.benefitsservice.rest.EmployeeServiceClient;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeBenefitService {

  private final EmployeeBenefitRepository employeeBenefitRepository;
  private final BenefitPlanRepository benefitPlanRepository;
  private final EmployeeServiceClient employeeServiceClient;

  /**
   * Assign a benefit plan to an employee after checking eligibility.
   */
  @Transactional
  public EmployeeBenefitDto assignBenefit(Long employeeId, Long benefitPlanId, String notes) {
    BenefitPlan plan = benefitPlanRepository.findById(benefitPlanId)
        .filter(BenefitPlan::getIsActive)
        .orElseThrow(() -> new IllegalArgumentException("Benefit plan not found or inactive"));

    if (!isEligibleForBenefit(employeeId, plan)) {
      throw new IllegalArgumentException("Employee is not eligible for this benefit.");
    }

    EmployeeBenefit employeeBenefit = EmployeeBenefit.builder()
        .employeeId(employeeId)
        .benefitPlanId(benefitPlanId)
        .startDate(LocalDate.now())
        .status(BenefitStatus.ACTIVE)
        .notes(notes)
        .build();

    employeeBenefitRepository.save(employeeBenefit);
    return EmployeeBenefitMapper.toDto(employeeBenefit);
  }

  /**
   * Update an existing employee benefit record.
   */
  @Transactional
  public EmployeeBenefitDto updateEmployeeBenefit(Long id, EmployeeBenefitDto updatedDto) {
    EmployeeBenefit existing = employeeBenefitRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Employee benefit record not found"));

    existing.setEndDate(updatedDto.getEndDate());
    existing.setStatus(updatedDto.getStatus());
    existing.setNotes(updatedDto.getNotes());
    employeeBenefitRepository.save(existing);

    return EmployeeBenefitMapper.toDto(existing);
  }

  /**
   * Deactivate a benefit plan, preventing it from new assignments.
   */
  @Transactional
  public void deactivateBenefitPlan(Long benefitPlanId) {
    benefitPlanRepository.findById(benefitPlanId).ifPresent(plan -> {
      plan.setIsActive(false);
      benefitPlanRepository.save(plan);
    });
  }

  /**
   * Fetch all benefits assigned to a specific employee.
   */
  public List<EmployeeBenefitDto> getEmployeeBenefits(Long employeeId) {
    return employeeBenefitRepository.findByEmployeeId(employeeId).stream()
        .map(EmployeeBenefitMapper::toDto)
        .collect(Collectors.toList());
  }

  /**
   * Check if an employee is eligible for a benefit plan.
   */
  private boolean isEligibleForBenefit(Long employeeId, BenefitPlan plan) {
    EmployeeDto employee = employeeServiceClient.getEmployeeById(employeeId)
        .blockOptional()
        .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

    // Check years of service
    if (plan.getMinServiceYears() != null && employee.getServiceYears() < plan.getMinServiceYears()) {
      return false;
    }

    // Check age
    if (plan.getMinAge() != null && employee.getAge() < plan.getMinAge()) {
      return false;
    }

    // Check department eligibility
    if (!plan.getEligibleDepartmentIds().isEmpty() &&
        !plan.getEligibleDepartmentIds().contains(employee.getDepartment().getId())) {
      return false;
    }

    // Check position eligibility
    return plan.getEligiblePositionIds().isEmpty() ||
        plan.getEligiblePositionIds().contains(employee.getPositionId());
  }
}
