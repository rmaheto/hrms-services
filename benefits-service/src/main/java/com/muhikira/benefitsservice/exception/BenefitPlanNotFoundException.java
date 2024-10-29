package com.muhikira.benefitsservice.exception;

public class BenefitPlanNotFoundException extends RuntimeException {

  public BenefitPlanNotFoundException(String benefitPlanNotFound) {
    super(benefitPlanNotFound);
  }
}
