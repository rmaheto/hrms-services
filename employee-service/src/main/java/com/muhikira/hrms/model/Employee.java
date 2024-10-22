package com.muhikira.hrms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Entity
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "email")
  private String email;

  @Column(name = "phone")
  private String phone;

  @Column(name = "birthday")
  private LocalDate dateOfBirth;

  @Column(name = "placeOfBirth")
  private String placeOfBirth;

  @Column(name = "position")
  private String position;

  @Column(name = "department_id")
  private Long departmentId;

  @Column(name = "hireDate ")
  private LocalDate hireDate;

  @Column(name = "salary ")
  private BigDecimal salary;
}
