package com.muhikira.hrms.service;

import com.muhikira.hrms.dto.DepartmentDto;
import com.muhikira.hrms.dto.EmployeeDto;
import com.muhikira.hrms.dto.UserRequestDto;
import com.muhikira.hrms.exception.DepartmentNotFoundException;
import com.muhikira.hrms.mapper.EmployeeMapper;
import com.muhikira.hrms.model.Employee;
import com.muhikira.hrms.model.RoleName;
import com.muhikira.hrms.repository.EmployeeRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {

  private final EmployeeRepository employeeRepository;

  private final DepartmentServiceClient departmentServiceClient;

  private final AuthServiceClient authServiceClient;

  public EmployeeDto createEmployee(Employee employee) {
    DepartmentDto department =
        departmentServiceClient
            .getDepartmentById(employee.getDepartmentId())
            .onErrorResume(
                e -> {
                  throw new DepartmentNotFoundException(
                      "Department not found for ID: " + employee.getDepartmentId());
                })
            .block();

    Employee savedEmployee = employeeRepository.save(employee);

    createUserForEmployee(savedEmployee);

    return EmployeeMapper.toDto(savedEmployee, department);
  }

  public List<EmployeeDto> getAllEmployees() {
    return employeeRepository.findAll().stream()
        .map(
            employee -> {
              DepartmentDto department =
                  departmentServiceClient.getDepartmentById(employee.getDepartmentId()).block();
              return EmployeeMapper.toDto(employee, department);
            })
        .toList();
  }

  public Optional<EmployeeDto> getEmployeeById(Long id) {
    Optional<Employee> employee = employeeRepository.findById(id);

    return employee.map(
        emp -> {
          DepartmentDto departmentDto =
              departmentServiceClient.getDepartmentById(emp.getDepartmentId()).block();

          if (departmentDto != null) {
            emp.setDepartmentId(departmentDto.getId());
          }
          return EmployeeMapper.toDto(emp, departmentDto);
        });
  }

  public List<EmployeeDto> getEmployeesByFirstName(String firstName) {
    return employeeRepository.findByFirstName(firstName).stream()
        .map(
            employee -> {
              DepartmentDto department =
                  departmentServiceClient.getDepartmentById(employee.getDepartmentId()).block();
              return EmployeeMapper.toDto(employee, department);
            })
        .toList();
  }

  public List<EmployeeDto> getEmployeesByLastName(String lastName) {
    return employeeRepository.findByLastName(lastName).stream()
        .map(
            employee -> {
              DepartmentDto department =
                  departmentServiceClient.getDepartmentById(employee.getDepartmentId()).block();
              return EmployeeMapper.toDto(employee, department);
            })
        .toList();
  }

  public List<EmployeeDto> getEmployeesByDepartmentId(Long departmentId) {
    return employeeRepository.findByDepartmentId(departmentId).stream()
        .map(
            employee -> {
              DepartmentDto department =
                  departmentServiceClient.getDepartmentById(departmentId).block();
              return EmployeeMapper.toDto(employee, department);
            })
        .toList();
  }

  public List<EmployeeDto> getEmployeesBySalary(BigDecimal salary) {
    return employeeRepository.findBySalaryGreaterThanEqual(salary).stream()
        .map(
            employee -> {
              DepartmentDto department =
                  departmentServiceClient.getDepartmentById(employee.getDepartmentId()).block();
              return EmployeeMapper.toDto(employee, department);
            })
        .toList();
  }

  public List<EmployeeDto> getEmployeesByAge(int age) {
    return employeeRepository.findByAge(age).stream()
        .map(
            employee -> {
              DepartmentDto department =
                  departmentServiceClient.getDepartmentById(employee.getDepartmentId()).block();
              return EmployeeMapper.toDto(employee, department);
            })
        .toList();
  }

  public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDetails) {
    Employee employee =
        employeeRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Employee not found with id " + id));

    employee.setFirstName(employeeDetails.getFirstName());
    employee.setLastName(employeeDetails.getLastName());
    employee.setEmail(employeeDetails.getEmail());
    employee.setPhone(employeeDetails.getPhone());
    employee.setDateOfBirth(employeeDetails.getDateOfBirth());
    employee.setPlaceOfBirth(employeeDetails.getPlaceOfBirth());
    employee.setPosition(employeeDetails.getPosition());
    employee.setDepartmentId(employeeDetails.getDepartment().getId());
    employee.setHireDate(employeeDetails.getHireDate());
    employee.setSalary(employeeDetails.getSalary());

    Employee savedEmployee = employeeRepository.save(employee);

    DepartmentDto department =
        departmentServiceClient.getDepartmentById(employee.getDepartmentId()).block();

    return EmployeeMapper.toDto(savedEmployee, department);
  }

  public void deleteEmployee(Long id) {
    Employee employee =
        employeeRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Employee not found with id " + id));
    employeeRepository.delete(employee);
  }

  private void createUserForEmployee(Employee employee) {
    String randomPassword = generateRandomPassword();
    UserRequestDto userDto = new UserRequestDto();
    userDto.setUsername(employee.getEmail());
    userDto.setPassword(randomPassword);
    userDto.setRoles(Set.of(RoleName.ROLE_USER));
    userDto.setEmployeeId(employee.getId());

    // Call Auth Service to create user and block to ensure user creation is successful
    authServiceClient.createUser(userDto).block();

    // Send email to the employee with the account details using Kafka
//    notificationServiceClient.sendAccountDetails(
//        employee.getEmail(), employee.getFirstName(), userDto.getUsername(), randomPassword
//    );
  }

  private String generateRandomPassword() {
    return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
  }
}
