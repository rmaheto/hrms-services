package com.muhikira.notificationservice.service;

import com.muhikira.notificationservice.entity.NotificationTemplate;
import com.muhikira.notificationservice.model.MessageType;
import com.muhikira.notificationservice.model.NotificationRequest;
import com.muhikira.notificationservice.model.NotificationStatus;
import com.muhikira.notificationservice.model.NotificationTemplateDto;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsService {

  @Value("${twilio.from.phone}")
  private String fromPhoneNumber;

  private final TemplateService templateService;
  private final NotificationLogService notificationLogService;


  public void sendSms(NotificationRequest notificationRequest) {
    String messageBody = notificationRequest.getMessageBody();

    // If a template is provided, process the template
    if (notificationRequest.getTemplateId() != null) {
      Optional<NotificationTemplateDto> template = templateService.getTemplateById(notificationRequest.getTemplateId());
      if (template.isPresent()) {
        messageBody = templateService.processTemplate(template.get().getBody(),
            notificationRequest.getPlaceholders());
      }
    }

    // Send SMS to all recipients
    for (String recipient : notificationRequest.getRecipients()) {
      try {
        Message.creator(
            new PhoneNumber(recipient),
            new PhoneNumber(fromPhoneNumber),
            messageBody
        ).create();

        // Log successful SMS
        notificationLogService.logNotification(
            notificationRequest.getServiceId(),
            MessageType.SMS,
            recipient,
            null, // No subject for SMS
            messageBody,
            NotificationStatus.SENT,
            null
        );

      } catch (Exception e) {
        // Log failed SMS
        notificationLogService.logNotification(
            notificationRequest.getServiceId(),
            MessageType.SMS,
            recipient,
            null, // No subject for SMS
            messageBody,
            NotificationStatus.FAILED,
            e.getMessage()
        );
      }
    }
  }
}
