package com.muhikira.benefitsservice.repository;

import com.muhikira.benefitsservice.entity.EmployeeBenefit;
import com.muhikira.benefitsservice.enums.BenefitStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeBenefitRepository extends JpaRepository<EmployeeBenefit, Long> {
  List<EmployeeBenefit> findByEmployeeIdAndStatus(Long employeeId, BenefitStatus status);
  List<EmployeeBenefit> findByEmployeeId(Long employeeId);
}
