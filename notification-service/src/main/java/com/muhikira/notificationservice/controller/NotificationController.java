package com.muhikira.notificationservice.controller;

import com.muhikira.notificationservice.entity.NotificationTemplate;
import com.muhikira.notificationservice.model.ApiResponse;
import com.muhikira.notificationservice.model.MessageType;
import com.muhikira.notificationservice.model.NotificationRequest;
import com.muhikira.notificationservice.model.NotificationTemplateDto;
import com.muhikira.notificationservice.service.NotificationService;
import com.muhikira.notificationservice.service.TemplateService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
  public ResponseEntity<ApiResponse<String>> sendNotification(
      @RequestBody NotificationRequest request) {
    notificationService.sendNotification(request);
    return ResponseEntity.ok(
        new ApiResponse<>("Email sent successfully to multiple recipients!", null));
  }

  @PostMapping("/templates")
  public ResponseEntity<ApiResponse<String>> saveTemplate(
      @RequestBody NotificationTemplate template) {
    templateService.saveTemplate(template);
    return ResponseEntity.ok(new ApiResponse<>("Template saved successfully!", null));
  }

  @GetMapping("/templates")
  public ResponseEntity<ApiResponse<List<NotificationTemplateDto>>> getAllTemplates() {
    List<NotificationTemplateDto> templates = templateService.getAllTemplates();

    if (templates.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(new ApiResponse<>("No notification templates found.", null));
    }

    return ResponseEntity.ok(new ApiResponse<>("Templates retrieved successfully.", templates));
  }

  @GetMapping("/templates/{id}")
  public ResponseEntity<ApiResponse<NotificationTemplateDto>> getTemplateById(
      @PathVariable Long id) {
    return templateService.getTemplateById(id)
        .map(template -> ResponseEntity.ok(
            new ApiResponse<>("Template found successfully.", template)))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ApiResponse<>("Template with ID " + id + " not found", null)));
  }

  @PutMapping("/templates/{id}")
  public ResponseEntity<ApiResponse<NotificationTemplateDto>> updateTemplate(
      @PathVariable Long id, @RequestBody NotificationTemplate updatedTemplate) {
    try {
      NotificationTemplateDto template = templateService.updateTemplate(id, updatedTemplate);
      return ResponseEntity.ok(new ApiResponse<>("Template updated successfully.", template));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(new ApiResponse<>(e.getMessage(), null));
    }
  }

  @GetMapping("/message-types")
  public ResponseEntity<ApiResponse<List<String>>> getMessageTypes() {
    List<String> messageTypes = Arrays.stream(MessageType.values())
        .map(Enum::name)
        .toList();

    return ResponseEntity.ok(
        new ApiResponse<>("Message types retrieved successfully.", messageTypes));
  }
}
