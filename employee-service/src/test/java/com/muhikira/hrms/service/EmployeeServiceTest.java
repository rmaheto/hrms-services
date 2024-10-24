package com.muhikira.hrms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.muhikira.hrms.dto.DepartmentDto;
import com.muhikira.hrms.dto.EmployeeDto;
import com.muhikira.hrms.dto.UserRequestDto;
import com.muhikira.hrms.exception.DepartmentNotFoundException;
import com.muhikira.hrms.model.Employee;
import com.muhikira.hrms.repository.EmployeeRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

  @Mock
  private EmployeeRepository employeeRepository;

  @Mock
  private DepartmentServiceClient departmentServiceClient;

  @Mock
  private AuthServiceClient authServiceClient;

  @InjectMocks
  private EmployeeService employeeService;

  @Test
  void createEmployee_whenDepartmentExists_thenEmployeeIsCreated() {
    Employee employee = buildEmployee();
    DepartmentDto departmentDto = buildDepartmentDto();

    when(departmentServiceClient.getDepartmentById(employee.getDepartmentId()))
        .thenReturn(Mono.just(departmentDto));
    when(employeeRepository.save(employee)).thenReturn(employee);
    when(authServiceClient.createUser(any(UserRequestDto.class)))
        .thenReturn(Mono.empty());

    EmployeeDto result = employeeService.createEmployee(employee);

    assertNotNull(result);
    verify(employeeRepository, times(1)).save(employee);
    verify(authServiceClient, times(1)).createUser(any(UserRequestDto.class));
  }

  @Test
  void createEmployee_whenDepartmentNotFound_thenThrowsDepartmentNotFoundException() {
    Employee employee = buildEmployee();

    when(departmentServiceClient.getDepartmentById(employee.getDepartmentId()))
        .thenReturn(Mono.error(new DepartmentNotFoundException("Department not found")));

    assertThrows(DepartmentNotFoundException.class, () -> employeeService.createEmployee(employee));
    verify(employeeRepository, never()).save(employee);
  }

  @Test
  void getEmployeeById_whenEmployeeExists_thenReturnsEmployeeWithDepartment() {
    Employee employee = buildEmployee();
    DepartmentDto departmentDto = buildDepartmentDto();

    when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
    when(departmentServiceClient.getDepartmentById(1L)).thenReturn(Mono.just(departmentDto));

    Optional<EmployeeDto> result = employeeService.getEmployeeById(1L);

    assertTrue(result.isPresent());
    assertEquals(employee.getId(), result.get().getId());
    assertEquals(employee.getDepartmentId(), departmentDto.getId());
  }

  @Test
  void getEmployeeById_whenEmployeeNotFound_thenReturnsEmpty() {
    when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

    Optional<EmployeeDto> result = employeeService.getEmployeeById(1L);

    assertFalse(result.isPresent());
    verify(departmentServiceClient, never()).getDepartmentById(anyLong());
  }

  private Employee buildEmployee() {
    Employee employee = new Employee();
    employee.setId(1L);
    employee.setDepartmentId(1L);
    employee.setFirstName("John");
    employee.setLastName("Doe");
    return employee;
  }

  private DepartmentDto buildDepartmentDto() {
    DepartmentDto departmentDto = new DepartmentDto();
    departmentDto.setId(1L);
    departmentDto.setDepartmentName("Engineering");
    return departmentDto;
  }
}
