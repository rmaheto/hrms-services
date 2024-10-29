package com.muhikira.benefitsservice.controller;

import com.muhikira.benefitsservice.dto.EmployeeBenefitDto;
import com.muhikira.benefitsservice.service.EmployeeBenefitService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/benefits")
@RequiredArgsConstructor
public class EmployeeBenefitController {

  private final EmployeeBenefitService employeeBenefitService;

  /**
   * Assign a benefit plan to an employee.
   */
  @PostMapping("/assign")
  public ResponseEntity<?> assignBenefit(@RequestParam Long employeeId,
      @RequestParam Long benefitPlanId,
      @RequestParam(required = false) String notes) {
    try {
      EmployeeBenefitDto assignedBenefit = employeeBenefitService.assignBenefit(employeeId,
          benefitPlanId, notes);
      return ResponseEntity.status(HttpStatus.CREATED).body(assignedBenefit);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  /**
   * Update an existing employee benefit record.
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> updateEmployeeBenefit(@PathVariable Long id,
      @RequestBody EmployeeBenefitDto updatedDto) {
    try {
      EmployeeBenefitDto updatedBenefit = employeeBenefitService.updateEmployeeBenefit(id,
          updatedDto);
      return ResponseEntity.ok(updatedBenefit);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  /**
   * Deactivate a benefit plan by its ID.
   */
  @PutMapping("/deactivate/{benefitPlanId}")
  public ResponseEntity<?> deactivateBenefitPlan(@PathVariable Long benefitPlanId) {
    employeeBenefitService.deactivateBenefitPlan(benefitPlanId);
    return ResponseEntity.ok("Benefit plan deactivated successfully.");
  }

  /**
   * Fetch all benefits assigned to a specific employee.
   */
  @GetMapping("/employee/{employeeId}")
  public ResponseEntity<?> getEmployeeBenefits(@PathVariable Long employeeId) {
    List<EmployeeBenefitDto> benefits = employeeBenefitService.getEmployeeBenefits(employeeId);
    if (benefits.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("No benefits found for employee with ID " + employeeId);
    }
    return ResponseEntity.ok(benefits);
  }
}
