package org.muhikira.departmentservice.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.muhikira.departmentservice.Dto.DepartmentDto;
import org.muhikira.departmentservice.Dto.EmployeeDto;
import org.muhikira.departmentservice.service.impl.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/departments")
@AllArgsConstructor
public class DepartmentController {

  private final DepartmentService departmentService;

  @PostMapping
  public ResponseEntity<DepartmentDto> saveDepartment(@RequestBody DepartmentDto departmentDto) {
    DepartmentDto savedDepartment = departmentService.saveDepartment(departmentDto);
    return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Long id) {
    DepartmentDto department = departmentService.getDepartmentById(id);
    return ResponseEntity.ok(department);
  }

  @GetMapping("/code/{code}")
  public ResponseEntity<DepartmentDto> getDepartmentByCode(@PathVariable String code) {
    DepartmentDto department = departmentService.getDepartmentByCode(code);
    return ResponseEntity.ok(department);
  }

  @GetMapping
  public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
    return new ResponseEntity<>(departmentService.getAllDepartments(), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<DepartmentDto> updateDepartment(
      @PathVariable Long id, @RequestBody DepartmentDto departmentDto) {
    DepartmentDto updatedDepartment = departmentService.updateDepartment(id, departmentDto);
    return ResponseEntity.ok(updatedDepartment);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
    departmentService.deleteDepartment(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{departmentId}/manager/{managerId}")
  public ResponseEntity<DepartmentDto> setDepartmentManager(
      @PathVariable Long departmentId, @PathVariable Long managerId) {

    DepartmentDto updatedDepartment = departmentService.setDepartmentManager(departmentId, managerId);
    return ResponseEntity.ok(updatedDepartment);
  }

  @GetMapping("/{id}/manager")
  public ResponseEntity<EmployeeDto> getDepartmentManager(@PathVariable Long id) {
    EmployeeDto manager = departmentService.getDepartmentManager(id);
    return ResponseEntity.ok(manager);
  }

  @GetMapping("/{id}/employees")
  public ResponseEntity<List<EmployeeDto>> getEmployeesByDepartmentId(@PathVariable Long id) {
    List<EmployeeDto> employees = departmentService.getEmployeesByDepartmentId(id);
    return ResponseEntity.ok(employees);
  }
}
