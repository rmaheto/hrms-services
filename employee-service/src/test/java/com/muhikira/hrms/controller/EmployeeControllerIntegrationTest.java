package com.muhikira.hrms.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muhikira.hrms.dto.DepartmentDto;
import com.muhikira.hrms.dto.EmployeeDto;
import com.muhikira.hrms.mapper.EmployeeMapper;
import com.muhikira.hrms.model.Employee;
import com.muhikira.hrms.service.EmployeeService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "ADMIN")
class EmployeeControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private EmployeeService employeeService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void createEmployee_whenValidData_thenReturnsCreatedEmployee() throws Exception {
    EmployeeDto employee = EmployeeMapper.toDto(buildEmployee(), buildDepartment());

    when(employeeService.createEmployee(any(Employee.class))).thenReturn(employee);

    mockMvc.perform(post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(employee)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName").value("John"))
        .andExpect(jsonPath("$.lastName").value("Doe"))
        .andExpect(jsonPath("$.department.id").value(1));
  }

  @Test
  void getEmployeeById_whenEmployeeExists_thenReturnsEmployee() throws Exception {
    EmployeeDto employee = EmployeeMapper.toDto(buildEmployee(), buildDepartment());

    when(employeeService.getEmployeeById(1L)).thenReturn(Optional.of(employee));

    mockMvc.perform(get("/api/employees/1")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.firstName").value("John"))
        .andExpect(jsonPath("$.lastName").value("Doe"));
  }

  @Test
  void getEmployeeById_whenEmployeeNotFound_thenReturnsNotFound() throws Exception {
    when(employeeService.getEmployeeById(1L)).thenReturn(Optional.empty());

    mockMvc.perform(get("/api/employees/1")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void getAllEmployees_whenEmployeesExist_thenReturnsEmployeeList() throws Exception {
    EmployeeDto employee = EmployeeMapper.toDto(buildEmployee(), buildDepartment());
    when(employeeService.getAllEmployees()).thenReturn(List.of(employee));

    mockMvc.perform(get("/api/employees")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].firstName").value("John"))
        .andExpect(jsonPath("$[0].lastName").value("Doe"));
  }

  @Test
  void deleteEmployee_whenEmployeeExists_thenReturnsNoContent() throws Exception {
    mockMvc.perform(delete("/api/employees/1"))
        .andExpect(status().isNoContent());
  }

  private Employee buildEmployee() {
    Employee employee = new Employee();
    employee.setId(1L);
    employee.setFirstName("John");
    employee.setLastName("Doe");
    employee.setEmail("john.doe@example.com");
    employee.setPhone("1234567890");
    employee.setDateOfBirth(LocalDate.of(1985, 5, 20));
    employee.setPosition("Developer");
    employee.setDepartmentId(1L);
    employee.setHireDate(LocalDate.of(2020, 1, 15));
    employee.setSalary(new BigDecimal("75000.00"));
    return employee;
  }

  private DepartmentDto buildDepartment(){
    DepartmentDto departmentDto = new DepartmentDto();
    departmentDto.setId(1L);
    departmentDto.setDepartmentName("HR");
    departmentDto.setDepartmentDescription("Human Resource");
    departmentDto.setDepartmentCode("HR0001");
    return departmentDto;
  }
}
