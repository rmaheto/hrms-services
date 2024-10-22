package com.muhikira.hrms.controller;

import com.muhikira.hrms.dto.EmployeeDto;
import com.muhikira.hrms.model.Employee;
import com.muhikira.hrms.service.EmployeeService;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

  private final  EmployeeService employeeService;

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping
  public EmployeeDto createEmployee(@RequestBody Employee employee) {
    return employeeService.createEmployee(employee);
  }

  @GetMapping
  public List<EmployeeDto> getAllEmployees() {
    return employeeService.getAllEmployees();
  }

  @GetMapping("/{id}")
  public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
    return employeeService.getEmployeeById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/firstName/{firstName}")
  public List<EmployeeDto> getEmployeesByFirstName(@PathVariable String firstName) {
    return employeeService.getEmployeesByFirstName(firstName);
  }

  @GetMapping("/lastName/{lastName}")
  public List<EmployeeDto> getEmployeesByLastName(@PathVariable String lastName) {
    return employeeService.getEmployeesByLastName(lastName);
  }

  @GetMapping("/department/{departmentId}")
  public ResponseEntity<List<EmployeeDto>> getEmployeesByDepartmentId(
      @PathVariable Long departmentId) {
    List<EmployeeDto> employees = employeeService.getEmployeesByDepartmentId(departmentId);
    return employees.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(employees);
  }

  @GetMapping("/salary/{salary}")
  public List<EmployeeDto> getEmployeesBySalary(@PathVariable BigDecimal salary) {
    return employeeService.getEmployeesBySalary(salary);
  }

  @GetMapping("/age/{age}")
  public List<EmployeeDto> getEmployeesByAge(@PathVariable int age) {
    return employeeService.getEmployeesByAge(age);
  }

  @PutMapping("/{id}")
  public ResponseEntity<EmployeeDto> updateEmployee(
      @PathVariable Long id, @RequestBody Employee employeeDetails) {
    return new ResponseEntity<>(
        employeeService.updateEmployee(id, employeeDetails), HttpStatus.ACCEPTED);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
    employeeService.deleteEmployee(id);
    return ResponseEntity.noContent().build();
  }
}
