package org.muhikira.departmentservice.service.impl;

import java.util.List;
import org.muhikira.departmentservice.Dto.DepartmentDto;
import org.muhikira.departmentservice.Dto.EmployeeDto;

public interface DepartmentService {

  DepartmentDto saveDepartment(DepartmentDto departmentDto);

  DepartmentDto getDepartmentByCode(String code);

  DepartmentDto getDepartmentById(Long departmentId);

  List<DepartmentDto> getAllDepartments();

  DepartmentDto updateDepartment(Long departmentId, DepartmentDto departmentDto);

  void deleteDepartment(Long departmentId);

  DepartmentDto setDepartmentManager(Long departmentId, Long managerId);

  EmployeeDto getDepartmentManager(Long departmentId);

  List<EmployeeDto> getEmployeesByDepartmentId(Long departmentId);
}
