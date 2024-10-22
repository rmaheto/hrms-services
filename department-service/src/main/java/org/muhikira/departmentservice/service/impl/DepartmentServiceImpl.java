package org.muhikira.departmentservice.service.impl;

import java.util.List;
import lombok.AllArgsConstructor;
import org.muhikira.departmentservice.Dto.DepartmentDto;
import org.muhikira.departmentservice.Dto.EmployeeDto;
import org.muhikira.departmentservice.Mapper.DepartmentMapper;
import org.muhikira.departmentservice.entity.Department;
import org.muhikira.departmentservice.exception.DepartmentNotFoundException;
import org.muhikira.departmentservice.exception.EmployeeNotFoundException;
import org.muhikira.departmentservice.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

  private final DepartmentRepository departmentRepository;

  private final EmployeeServiceClient employeeServiceClient;

  private static final String DEPARTMENT_NOT_FOUND_MSG = "Department not found with ID: ";

  private static final String EMPLOYEE_NOT_FOUND_MSG = "Employee not found with ID: ";

  @Override
  public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
    Department department = DepartmentMapper.toEntity(departmentDto);
    Department savedDepartment = departmentRepository.save(department);
    return DepartmentMapper.toDto(savedDepartment);
  }

  @Override
  public DepartmentDto getDepartmentByCode(String code) {
    Department department =
        departmentRepository
            .findByDepartmentCode(code)
            .orElseThrow(() -> new DepartmentNotFoundException(DEPARTMENT_NOT_FOUND_MSG + code));
    return DepartmentMapper.toDto(department);
  }

  @Override
  public DepartmentDto getDepartmentById(Long departmentId) {
    Department department =
        departmentRepository
            .findById(departmentId)
            .orElseThrow(
                () -> new DepartmentNotFoundException(DEPARTMENT_NOT_FOUND_MSG + departmentId));
    return DepartmentMapper.toDto(department);
  }

  @Override
  public List<DepartmentDto> getAllDepartments() {
    return departmentRepository.findAll().stream()
        .map(DepartmentMapper::toDto)
        .toList();
  }

  @Override
  public DepartmentDto updateDepartment(Long departmentId, DepartmentDto departmentDto) {
    Department existingDepartment =
        departmentRepository
            .findById(departmentId)
            .orElseThrow(
                () -> new DepartmentNotFoundException(DEPARTMENT_NOT_FOUND_MSG + departmentId));

    existingDepartment.setDepartmentName(departmentDto.getDepartmentName());
    existingDepartment.setDepartmentDescription(departmentDto.getDepartmentDescription());
    existingDepartment.setDepartmentCode(departmentDto.getDepartmentCode());
    existingDepartment.setManagerId(departmentDto.getManagerId());
    existingDepartment.setLocation(departmentDto.getLocation());

    Department updatedDepartment = departmentRepository.save(existingDepartment);
    return DepartmentMapper.toDto(updatedDepartment);
  }

  @Override
  public void deleteDepartment(Long departmentId) {
    Department department =
        departmentRepository
            .findById(departmentId)
            .orElseThrow(
                () -> new DepartmentNotFoundException(DEPARTMENT_NOT_FOUND_MSG + departmentId));
    departmentRepository.delete(department);
  }


  @Override
  @Transactional
  public DepartmentDto setDepartmentManager(Long departmentId, Long managerId) {
    Department department = departmentRepository.findById(departmentId)
        .orElseThrow(() -> new DepartmentNotFoundException(DEPARTMENT_NOT_FOUND_MSG + departmentId));

    EmployeeDto manager = employeeServiceClient.getEmployeeById(managerId).block();
    if (manager == null) {
      throw new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND_MSG + managerId);
    }

    department.setManagerId(managerId);
    Department updatedDepartment = departmentRepository.save(department);

    return DepartmentMapper.toDto(updatedDepartment);
  }

  @Override
  public EmployeeDto getDepartmentManager(Long departmentId) {
    Department department =
        departmentRepository
            .findById(departmentId)
            .orElseThrow(
                () -> new DepartmentNotFoundException(DEPARTMENT_NOT_FOUND_MSG + departmentId));

    Long managerId = department.getManagerId();
    return employeeServiceClient.getEmployeeById(managerId).block();
  }

  @Override
  public List<EmployeeDto> getEmployeesByDepartmentId(Long departmentId) {
    return employeeServiceClient.getEmployeesByDepartmentId(departmentId).block();
  }
}
