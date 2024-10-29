package com.muhikira.notificationservice.entity;

import com.muhikira.notificationservice.model.MessageType;
import com.muhikira.notificationservice.model.NotificationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String serviceId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private MessageType messageType;

  @Column(nullable = false)
  private String recipient;

  @Column(nullable = false)
  private String subject;

  @Column(nullable = false)
  private String messageBody;

  @Column(nullable = false)
  private LocalDateTime timestamp;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private NotificationStatus status;

  private String errorMessage;
}
