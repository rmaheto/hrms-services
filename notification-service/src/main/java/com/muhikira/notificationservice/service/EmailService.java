package com.muhikira.notificationservice.service;

import com.muhikira.notificationservice.model.MessageType;
import com.muhikira.notificationservice.model.NotificationRequest;
import com.muhikira.notificationservice.model.NotificationStatus;
import com.muhikira.notificationservice.model.NotificationTemplateDto;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

  private final MailjetService mailjetService;
  private final TemplateService templateService;
  private final NotificationLogService notificationLogService;

  public void sendEmail(NotificationRequest notificationRequest) {
    String body = notificationRequest.getMessageBody();
    String subject = notificationRequest.getSubject();

    // Check if a template is specified and process it
    if (notificationRequest.getTemplateId() != null) {
      Optional<NotificationTemplateDto> template = templateService.getTemplateById(notificationRequest.getTemplateId());
      if (template.isPresent()) {
        body = templateService.processTemplate(template.get().getBody(), notificationRequest.getPlaceholders());
        subject = template.get().getSubject(); // Use the subject from the template
      }
    }

    // Iterate over recipients and send emails using MailjetService
    for (String recipient : notificationRequest.getRecipients()) {
      try {
        // Send email using Mailjet
        mailjetService.sendEmail(
            recipient,
            "", // Optional: You can pass recipient name if available
            subject,
            body,
            body
        );

        // Log successful email
        notificationLogService.logNotification(
            notificationRequest.getServiceId(),
            MessageType.EMAIL,
            recipient,
            subject,
            body,
            NotificationStatus.SENT,
            null
        );

      } catch (Exception e) {
        // Log failed email
        notificationLogService.logNotification(
            notificationRequest.getServiceId(),
            MessageType.EMAIL,
            recipient,
            subject,
            body,
            NotificationStatus.FAILED,
            e.getMessage()
        );
      }
    }
  }
}
