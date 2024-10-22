package org.muhikira.departmentservice.Mapper;

import org.muhikira.departmentservice.Dto.DepartmentDto;
import org.muhikira.departmentservice.entity.Department;

public class DepartmentMapper {

  public static DepartmentDto toDto(Department department) {
    return new DepartmentDto(
        department.getId(),
        department.getDepartmentName(),
        department.getDepartmentDescription(),
        department.getDepartmentCode(),
        department.getManagerId(),
        department.getLocation());
  }

  public static Department toEntity(DepartmentDto departmentDto) {
    return new Department(
        departmentDto.getId(),
        departmentDto.getDepartmentName(),
        departmentDto.getDepartmentDescription(),
        departmentDto.getDepartmentCode(),
        departmentDto.getManagerId(),
        departmentDto.getLocation());
  }
}
