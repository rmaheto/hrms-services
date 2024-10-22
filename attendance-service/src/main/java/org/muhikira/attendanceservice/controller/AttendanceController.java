package org.muhikira.attendanceservice.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.muhikira.attendanceservice.model.Attendance;
import org.muhikira.attendanceservice.service.attendance.AttendanceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {
  
  private final AttendanceService attendanceService;
  @PostMapping("/clock-in/{employeeId}")
  public ResponseEntity<Attendance> clockIn(@PathVariable Long employeeId) {
    Attendance attendance = attendanceService.clockIn(employeeId);
    return new ResponseEntity<>(attendance, HttpStatus.OK);
  }

  @PostMapping("/clock-out/{employeeId}")
  public ResponseEntity<Attendance> clockOut(@PathVariable Long employeeId) {
    Attendance attendance = attendanceService.clockOut(employeeId);
    return new ResponseEntity<>(attendance, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/report/{employeeId}")
  public ResponseEntity<List<Attendance>> getAttendanceReport(
      @PathVariable Long employeeId,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

    List<Attendance> report = attendanceService.getAttendanceReportForEmployee(employeeId, startDate, endDate);
    return new ResponseEntity<>(report, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PatchMapping("/close-incomplete/{attendanceId}")
  public ResponseEntity<Attendance> closeIncomplete(
      @PathVariable Long attendanceId,
      @RequestParam LocalTime endTime) {

    Attendance attendance = attendanceService.closeIncompleteRecord(attendanceId, endTime);
    return new ResponseEntity<>(attendance, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/incomplete")
  public ResponseEntity<List<Attendance>> getIncompleteRecords() {
    List<Attendance> incompleteRecords = attendanceService.getIncompleteRecords();
    return new ResponseEntity<>(incompleteRecords, HttpStatus.OK);
  }
}