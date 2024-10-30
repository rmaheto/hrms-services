package com.muhikira.benefitsservice.controller;

import com.muhikira.benefitsservice.dto.BenefitAssignmentResultDto;
import com.muhikira.benefitsservice.dto.EmployeeBenefitDto;
import com.muhikira.benefitsservice.model.ApiResponse;
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

  @PostMapping("/{employeeId}/assign")
  public ResponseEntity<ApiResponse<List<BenefitAssignmentResultDto>>> assignBenefits(
      @PathVariable Long employeeId,
      @RequestParam List<Long> benefitPlanIds) {

    List<BenefitAssignmentResultDto> assignmentResults =
        employeeBenefitService.assignBenefits(employeeId, benefitPlanIds);

    ApiResponse<List<BenefitAssignmentResultDto>> response =
        new ApiResponse<>("Benefit assignment completed with results", assignmentResults);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<EmployeeBenefitDto>> updateEmployeeBenefit(
      @PathVariable Long id, @RequestBody EmployeeBenefitDto updatedDto) {

    EmployeeBenefitDto updatedBenefit =
        employeeBenefitService.updateEmployeeBenefit(id, updatedDto);
    ApiResponse<EmployeeBenefitDto> response =
        new ApiResponse<>("Benefit updated successfully", updatedBenefit);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/employee/{employeeId}")
  public ResponseEntity<ApiResponse<List<EmployeeBenefitDto>>> getEmployeeBenefits(
      @PathVariable Long employeeId) {
    List<EmployeeBenefitDto> benefits = employeeBenefitService.getEmployeeBenefits(employeeId);

    ApiResponse<List<EmployeeBenefitDto>> response =
        new ApiResponse<>("Benefits retrieved successfully", benefits);
    return ResponseEntity.ok(response);
  }
}
