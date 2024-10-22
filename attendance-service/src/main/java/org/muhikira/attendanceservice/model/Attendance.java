package org.muhikira.attendanceservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "attendance")
public class Attendance {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long employeeId; // New field to store Employee ID
  private LocalDate date;
  private LocalTime begin;
  private LocalTime end;
  private double hoursWorked;
  private double overtime;

  // Calculate worked hours and overtime
  public void calculateWorkedHoursAndOvertime() {
    if (this.begin != null && this.end != null) {
      Duration duration = Duration.between(this.begin, this.end);
      this.hoursWorked = duration.toMinutes() / 60.0; // Convert minutes to hours

      // Assume standard workday is 8 hours
      if (this.hoursWorked > 8) {
        this.overtime = this.hoursWorked - 8;
      } else {
        this.overtime = 0;
      }
    } else {
      this.hoursWorked = 0;
      this.overtime = 0;
    }
  }
}
