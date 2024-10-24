package com.muhikira.notificationservice.service;

import com.muhikira.notificationservice.model.MessageType;
import com.muhikira.notificationservice.model.NotificationRequest;
import com.muhikira.notificationservice.model.NotificationStatus;
import com.muhikira.notificationservice.model.NotificationTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender mailSender;
  private final TemplateService templateService;
  private final NotificationLogService notificationLogService;

  public void sendEmail(NotificationRequest notificationRequest) {
    String body = notificationRequest.getMessageBody();
    String subject = notificationRequest.getSubject();

    if (notificationRequest.getTemplateId() != null) {
      NotificationTemplate template = templateService.getTemplateById(notificationRequest.getTemplateId());
      if (template != null) {
        body = templateService.processTemplate(template.getBody(), notificationRequest.getPlaceholders());
        subject = template.getSubject(); // Use subject from the template
      }
    }


    for (String recipient : notificationRequest.getRecipients()) {
      try {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("your-email@gmail.com");

        mailSender.send(message);

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
