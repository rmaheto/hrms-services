package com.muhikira.benefitsservice.repository;

import com.muhikira.benefitsservice.entity.BenefitPlan;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenefitPlanRepository extends JpaRepository<BenefitPlan, Long> {
  List<BenefitPlan> findByIsActiveTrue();
}
