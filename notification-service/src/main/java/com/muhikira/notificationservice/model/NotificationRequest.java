package com.muhikira.notificationservice.model;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class NotificationRequest {

  private List<String> recipients;
  private String subject;
  private String messageBody; // The message body with placeholders
  private Long templateId; // Optional, ID of the template to use
  private Map<String, String> placeholders; // Placeholder values for the template
  private MessageType messageType;
  private String serviceId;
}
