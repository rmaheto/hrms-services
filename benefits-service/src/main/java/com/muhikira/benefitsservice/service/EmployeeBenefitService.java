package com.muhikira.benefitsservice.service;

import com.muhikira.benefitsservice.dto.BenefitAssignmentResultDto;
import com.muhikira.benefitsservice.dto.EmployeeBenefitDto;
import com.muhikira.benefitsservice.dto.EmployeeBenefitPlanDto;
import com.muhikira.benefitsservice.dto.EmployeeDto;
import com.muhikira.benefitsservice.entity.BenefitPlan;
import com.muhikira.benefitsservice.entity.EmployeeBenefit;
import com.muhikira.benefitsservice.enums.BenefitStatus;
import com.muhikira.benefitsservice.enums.PlanLevel;
import com.muhikira.benefitsservice.mapper.EmployeeBenefitMapper;
import com.muhikira.benefitsservice.mapper.EmployeeBenefitPlanMapper;
import com.muhikira.benefitsservice.repository.BenefitPlanRepository;
import com.muhikira.benefitsservice.repository.EmployeeBenefitRepository;
import com.muhikira.benefitsservice.rest.EmployeeServiceClient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeBenefitService {

  private final EmployeeBenefitRepository employeeBenefitRepository;
  private final BenefitPlanRepository benefitPlanRepository;
  private final EmployeeServiceClient employeeServiceClient;

  @Transactional
  public List<BenefitAssignmentResultDto> assignBenefits(
      Long employeeId, List<Long> benefitPlanIds) {
    log.info(
        "Attempting to assign benefit plans with IDs {} to employee with ID {}",
        benefitPlanIds,
        employeeId);

    EmployeeDto employee =
        employeeServiceClient
            .getEmployeeById(employeeId)
            .blockOptional()
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Employee with ID " + employeeId + " does not exist"));
    log.info("Employee with ID {} exists. Proceeding with benefit assignments.", employee.getId());

    List<BenefitAssignmentResultDto> results = new ArrayList<>();

    for (Long benefitPlanId : benefitPlanIds) {
      try {
        BenefitPlan plan =
            benefitPlanRepository
                .findById(benefitPlanId)
                .filter(BenefitPlan::getIsActive)
                .orElseThrow(
                    () ->
                        new IllegalArgumentException(
                            "Benefit plan not found or inactive for ID: " + benefitPlanId));

//        if (!isEligibleForBenefit(employeeId, plan)) {
//          String message = "Employee not eligible for benefit ID: " + benefitPlanId;
//          log.warn(message);
//          results.add(new BenefitAssignmentResultDto("Failed", message, null));
//          continue;
//        }

        EmployeeBenefit employeeBenefit =
            EmployeeBenefit.builder()
                .employeeId(employeeId)
                .benefitPlanId(benefitPlanId)
                .startDate(LocalDate.now())
                .status(BenefitStatus.ACTIVE)
                .build();

        employeeBenefitRepository.save(employeeBenefit);
        results.add(
            new BenefitAssignmentResultDto(
                "Success",
                "Benefit assigned successfully",
                EmployeeBenefitMapper.toDto(employeeBenefit)));
        log.info(
            "Successfully assigned benefit plan with ID {} to employee with ID {}",
            benefitPlanId,
            employeeId);

      } catch (IllegalArgumentException e) {
        log.error("Error assigning benefit plan with ID {}: {}", benefitPlanId, e.getMessage());
        results.add(new BenefitAssignmentResultDto("Failed", e.getMessage(), null));
      }
    }

    log.info("Completed assigning eligible benefit plans to employee with ID {}", employeeId);
    return results;
  }

  @Transactional
  public EmployeeBenefitDto updateEmployeeBenefit(Long id, EmployeeBenefitDto updatedDto) {
    EmployeeBenefit existing =
        employeeBenefitRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Employee benefit record not found"));

    existing.setEndDate(updatedDto.getEndDate());
    existing.setStatus(updatedDto.getStatus());
    existing.setNotes(updatedDto.getNotes());
    employeeBenefitRepository.save(existing);

    return EmployeeBenefitMapper.toDto(existing);
  }

  public List<EmployeeBenefitDto> getEmployeeBenefits(Long employeeId) {
    return employeeBenefitRepository.findByEmployeeId(employeeId).stream()
        .map(benefit -> {
          BenefitPlan benefitPlan = benefitPlanRepository.findById(benefit.getBenefitPlanId())
              .orElseThrow(() -> new IllegalArgumentException("Benefit plan not found"));

          EmployeeBenefitPlanDto benefitPlanDto = EmployeeBenefitPlanMapper.toDto(benefitPlan);

          EmployeeBenefitDto employeeBenefitDto = EmployeeBenefitMapper.toDto(benefit);
          employeeBenefitDto.setBenefitPlan(benefitPlanDto);

          if (benefitPlanDto.getPlanLevel() != PlanLevel.FIXED) {
            employeeBenefitDto.setEmployeeContribution(benefit.getEmployeeContribution());
          } else {
            employeeBenefitDto.setEmployeeContribution(benefitPlanDto.getFixedEmployeeContribution());
          }

          return employeeBenefitDto;
        })
        .toList();
  }

  private boolean isEligibleForBenefit(Long employeeId, BenefitPlan plan) {
    EmployeeDto employee =
        employeeServiceClient
            .getEmployeeById(employeeId)
            .blockOptional()
            .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

    if (plan.getMinServiceYears() != null
        && employee.getServiceYears() < plan.getMinServiceYears()) {
      return false;
    }

    if (plan.getMinAge() != null && employee.getAge() < plan.getMinAge()) {
      return false;
    }

    if (!plan.getEligibleDepartmentIds().isEmpty()
        && !plan.getEligibleDepartmentIds().contains(employee.getDepartment().getId())) {
      return false;
    }

    return plan.getEligiblePositionIds().isEmpty()
        || plan.getEligiblePositionIds().contains(employee.getPositionId());
  }
}
