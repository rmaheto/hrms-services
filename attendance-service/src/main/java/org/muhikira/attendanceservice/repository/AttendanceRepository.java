package org.muhikira.attendanceservice.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.muhikira.attendanceservice.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

  Optional<Attendance> findFirstByEmployeeIdAndEndIsNullOrderByDateDesc(Long employeeId);

  List<Attendance> findByEmployeeIdAndDateBetweenOrderByDateAsc(
      Long employeeId, LocalDate startDate, LocalDate endDate);

  List<Attendance> findByEndIsNull(); // Find all incomplete records
}
