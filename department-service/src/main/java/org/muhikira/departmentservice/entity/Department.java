package org.muhikira.departmentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "departments")
public class Department {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String departmentName;

  private String departmentDescription;

  @Column(nullable = false, unique = true)
  private String departmentCode;

  private Long managerId;

  @Column(nullable = false)
  private String location;
}