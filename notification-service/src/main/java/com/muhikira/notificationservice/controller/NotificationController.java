package com.muhikira.notificationservice.controller;

import com.muhikira.notificationservice.model.NotificationRequest;
import com.muhikira.notificationservice.model.NotificationTemplate;
import com.muhikira.notificationservice.service.NotificationService;
import com.muhikira.notificationservice.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

  private final NotificationService notificationService;
  private final TemplateService templateService;

  @PostMapping("/send")
  public ResponseEntity<?> sendNotification(@RequestBody NotificationRequest request) {
    notificationService.sendNotification(request);
    return ResponseEntity.ok("Email sent successfully to multiple recipients!");
  }

  @PostMapping("/templates")
  public ResponseEntity<?> saveTemplate(@RequestBody NotificationTemplate template) {
    templateService.saveTemplate(template);
    return ResponseEntity.ok("Template saved successfully!");
  }
}
