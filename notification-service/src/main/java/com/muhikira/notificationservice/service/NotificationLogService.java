package com.muhikira.notificationservice.service;

import com.muhikira.notificationservice.entity.NotificationLog;
import com.muhikira.notificationservice.model.MessageType;
import com.muhikira.notificationservice.model.NotificationStatus;
import com.muhikira.notificationservice.repository.NotificationLogRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationLogService {

  private final NotificationLogRepository notificationLogRepository;

  public void logNotification(String serviceId, MessageType messageType, String recipient, String subject, String messageBody, NotificationStatus status, String errorMessage) {
    NotificationLog log = new NotificationLog();
    log.setServiceId(serviceId);
    log.setMessageType(messageType);
    log.setRecipient(recipient);
    log.setSubject(subject);  // Can be null for SMS
    log.setMessageBody(messageBody);
    log.setTimestamp(LocalDateTime.now());
    log.setStatus(status);
    log.setErrorMessage(errorMessage);

    notificationLogRepository.save(log);
  }
}
