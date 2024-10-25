package com.muhikira.notificationservice.model;

import com.muhikira.notificationservice.entity.NotificationTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationTemplateDto {
  private Long id;
  private String name;
  private MessageType type;
  private String subject;
  private String body;

  public NotificationTemplateDto(NotificationTemplate template){
    this.id = template.getId();
    this.name =  template.getName();
    this.type = template.getMessageType();
    this.subject = template.getSubject();
    this.body = template.getBody();
  }
}
