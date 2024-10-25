package com.muhikira.notificationservice.service;

import com.muhikira.notificationservice.entity.NotificationTemplate;
import com.muhikira.notificationservice.model.MessageType;
import com.muhikira.notificationservice.model.NotificationRequest;
import com.muhikira.notificationservice.model.NotificationTemplateDto;
import com.muhikira.notificationservice.repository.NotificationTemplateRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationService {

  private final SmsService smsService;
  private final EmailService emailService;

  public void sendNotification(NotificationRequest request) {
    MessageType type = request.getMessageType();

    switch (type) {
      case EMAIL:
        emailService.sendEmail(request);
        break;
      case SMS:
        smsService.sendSms(request);
        break;
      case BOTH:
        emailService.sendEmail(request);
        smsService.sendSms(request);
        break;
    }
  }
}
