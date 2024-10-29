package com.muhikira.benefitsservice.controller;

import com.muhikira.benefitsservice.dto.BenefitPlanDto;
import com.muhikira.benefitsservice.service.BenefitPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

  @PostMapping
  public ResponseEntity<BenefitPlanDto> createBenefitPlan(
      @RequestBody BenefitPlanDto benefitPlanDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(benefitPlanService.createBenefitPlan(benefitPlanDto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<BenefitPlanDto> updateBenefitPlan(@PathVariable Long id,
      @RequestBody BenefitPlanDto benefitPlanDto) {
    return ResponseEntity.ok(benefitPlanService.updateBenefitPlan(id, benefitPlanDto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> inactivateBenefitPlan(@PathVariable Long id) {
    benefitPlanService.inactivateBenefitPlan(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<BenefitPlanDto> getBenefitPlanById(@PathVariable Long id) {
    return ResponseEntity.ok(benefitPlanService.getBenefitPlanById(id));
  }
}
