package com.muhikira.notificationservice.model;

import lombok.Data;

@Data
public class NotificationTemplate {
  private String id;
  private String name;
  private String type;
  private String subject;
  private String body;
}
