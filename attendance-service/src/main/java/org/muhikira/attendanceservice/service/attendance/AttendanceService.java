package org.muhikira.attendanceservice.service.attendance;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.muhikira.attendanceservice.model.Attendance;
import org.muhikira.attendanceservice.repository.AttendanceRepository;
import org.muhikira.attendanceservice.service.employee.EmployeeServiceClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendanceService {
  private final AttendanceRepository attendanceRepository;
  private final EmployeeServiceClient employeeServiceClient;

  public Attendance clockIn(Long employeeId) {
    // Check if employee exists
    employeeServiceClient
        .getEmployeeById(employeeId)
        .blockOptional()
        .orElseThrow(() -> new RuntimeException("Employee not found"));

    LocalDate today = LocalDate.now();

    // Check for incomplete clock-out
    Optional<Attendance> incompleteRecord =
        attendanceRepository.findFirstByEmployeeIdAndEndIsNullOrderByDateDesc(employeeId);

    if (incompleteRecord.isPresent()) {
      throw new RuntimeException("Employee has an incomplete clock-out for the previous day.");
    }

    // Create a new attendance record for today's clock-in
    Attendance attendance = new Attendance();
    attendance.setEmployeeId(employeeId);
    attendance.setDate(today);
    attendance.setBegin(LocalTime.now());

    return attendanceRepository.save(attendance);
  }

  public Attendance clockOut(Long employeeId) {
    // Check if employee exists
    employeeServiceClient
        .getEmployeeById(employeeId)
        .blockOptional()
        .orElseThrow(() -> new RuntimeException("Employee not found"));

    LocalDate today = LocalDate.now();

    // Find the latest incomplete attendance record (where clock-in exists but clock-out is missing)
    Attendance attendance =
        attendanceRepository
            .findFirstByEmployeeIdAndEndIsNullOrderByDateDesc(employeeId)
            .orElseThrow(
                () -> new RuntimeException("No open clock-in record found for clock-out."));

    // If the clock-out is happening on a different day, update the date if necessary
    if (!attendance.getDate().isEqual(today)) {
      attendance.setDate(today); // Update date if clock-out is on the next day
    }

    attendance.setEnd(LocalTime.now());
    attendance.calculateWorkedHoursAndOvertime();

    return attendanceRepository.save(attendance);
  }

  public List<Attendance> getAttendanceReportForEmployee(
      Long employeeId, LocalDate startDate, LocalDate endDate) {

    employeeServiceClient
        .getEmployeeById(employeeId)
        .blockOptional()
        .orElseThrow(() -> new RuntimeException("Employee not found"));

    return attendanceRepository.findByEmployeeIdAndDateBetweenOrderByDateAsc(
        employeeId, startDate, endDate);
  }

  // Method for admin to close an incomplete record with a custom clock-out time
  public Attendance closeIncompleteRecord(Long attendanceId, LocalTime endTime) {
    Attendance attendance =
        attendanceRepository
            .findById(attendanceId)
            .orElseThrow(() -> new RuntimeException("Attendance record not found."));

    if (attendance.getEnd() != null) {
      throw new RuntimeException("This attendance record is already completed.");
    }

    attendance.setEnd(endTime);
    attendance.calculateWorkedHoursAndOvertime();
    return attendanceRepository.save(attendance);
  }

  public List<Attendance> getIncompleteRecords() {
    return attendanceRepository.findByEndIsNull();
  }
}
