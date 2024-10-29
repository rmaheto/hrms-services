package com.muhikira.notificationservice.repository;

import com.muhikira.notificationservice.entity.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {
  // You can add custom query methods if needed
}
