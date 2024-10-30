package com.muhikira.benefitsservice.controller;

import com.muhikira.benefitsservice.dto.BenefitPlanDto;
import com.muhikira.benefitsservice.model.ApiResponse;
import com.muhikira.benefitsservice.service.BenefitPlanService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/benefit-plans")
@RequiredArgsConstructor
public class BenefitPlanController {

  private final BenefitPlanService benefitPlanService;

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping
  public ResponseEntity<ApiResponse<BenefitPlanDto>> createBenefitPlan(
      @RequestBody BenefitPlanDto benefitPlanDto) {
    BenefitPlanDto createdPlan = benefitPlanService.createBenefitPlan(benefitPlanDto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new ApiResponse<>("Benefit plan created successfully", createdPlan));
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<BenefitPlanDto>> updateBenefitPlan(
      @PathVariable Long id, @RequestBody BenefitPlanDto benefitPlanDto) {
    BenefitPlanDto updatedPlan = benefitPlanService.updateBenefitPlan(id, benefitPlanDto);
    return ResponseEntity.ok(new ApiResponse<>("Benefit plan updated successfully", updatedPlan));
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping("/{id}/inactivate")
  public ResponseEntity<ApiResponse<Void>> inactivateBenefitPlan(@PathVariable Long id) {
    benefitPlanService.inactivateBenefitPlan(id);
    ApiResponse<Void> response = new ApiResponse<>("Benefit plan inactivated successfully", null);
    return ResponseEntity.ok(response);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping("/{id}/activate")
  public ResponseEntity<ApiResponse<Void>> activateBenefitPlan(@PathVariable Long id) {
    benefitPlanService.activateBenefitPlan(id);
    ApiResponse<Void> response = new ApiResponse<>("Benefit plan activated successfully", null);
    return ResponseEntity.ok(response);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<BenefitPlanDto>> getBenefitPlanById(@PathVariable Long id) {
    BenefitPlanDto plan = benefitPlanService.getBenefitPlanById(id);
    return ResponseEntity.ok(new ApiResponse<>("Benefit plan retrieved successfully", plan));
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping
  public ResponseEntity<ApiResponse<List<BenefitPlanDto>>> getAllBenefitPlans() {
    List<BenefitPlanDto> plans = benefitPlanService.getAllBenefitPlans();
    String message =
        plans.isEmpty() ? "No benefit plans found" : "Benefit plans retrieved successfully";
    return ResponseEntity.ok(new ApiResponse<>(message, plans));
  }
}
