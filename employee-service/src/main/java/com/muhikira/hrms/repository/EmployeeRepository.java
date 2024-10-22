package com.muhikira.hrms.repository;

import com.muhikira.hrms.model.Employee;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  Optional<Object> findById(SingularAttribute<AbstractPersistable, Serializable> id);

  List<Employee> findByDepartmentId(Long departmentId);

  // Find employees by first name
  List<Employee> findByFirstName(String firstName);

  // Find employees by last name
  List<Employee> findByLastName(String lastName);

  // Find employees by salary greater than or equal to a value
  List<Employee> findBySalaryGreaterThanEqual(BigDecimal salary);

  // Custom query to find employees by age (calculated using date of birth)
  @Query("SELECT e FROM Employee e WHERE YEAR(CURRENT_DATE) - YEAR(e.dateOfBirth) = ?1")
  List<Employee> findByAge(int age);
}
