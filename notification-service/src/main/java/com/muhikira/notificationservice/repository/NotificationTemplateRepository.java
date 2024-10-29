package com.muhikira.notificationservice.repository;

import com.muhikira.notificationservice.entity.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long> {
  // Custom query to find by name if needed
  NotificationTemplate findByName(String name);
}
