package com.muhikira.hrms.mapper;

import com.muhikira.hrms.dto.DepartmentDto;
import com.muhikira.hrms.dto.EmployeeDto;
import com.muhikira.hrms.model.Employee;

public class EmployeeMapper {

  private EmployeeMapper() {}

    public static EmployeeDto toDto(Employee employee, DepartmentDto departmentDto) {
      return new EmployeeDto(
          employee.getId(),
          employee.getFirstName(),
          employee.getLastName(),
          employee.getEmail(),
          employee.getPhone(),
          employee.getDateOfBirth(),
          employee.getPlaceOfBirth(),
          employee.getPosition(),
          departmentDto,
          employee.getHireDate(),
          employee.getSalary()
      );
    }
}
