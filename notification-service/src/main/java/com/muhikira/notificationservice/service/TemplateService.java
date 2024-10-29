package com.muhikira.notificationservice.service;


import com.muhikira.notificationservice.entity.NotificationTemplate;
import com.muhikira.notificationservice.model.NotificationTemplateDto;
import com.muhikira.notificationservice.repository.NotificationTemplateRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemplateService {

  private final NotificationTemplateRepository notificationTemplateRepository;
  public void saveTemplate(NotificationTemplate template) {
    notificationTemplateRepository.save(template);
  }

  public Optional<NotificationTemplateDto> getTemplateById(Long id) {
    return notificationTemplateRepository.findById(id)
        .map(NotificationTemplateDto::new);
  }

  public List<NotificationTemplateDto> getAllTemplates(){

    return notificationTemplateRepository.findAll().stream()
        .map(notificationTemplate -> new NotificationTemplateDto(
            notificationTemplate.getId(),
            notificationTemplate.getName(),
            notificationTemplate.getMessageType(),
            notificationTemplate.getSubject(),
            notificationTemplate.getBody()
        )).toList();
  }

  public NotificationTemplateDto updateTemplate(Long id, NotificationTemplate updatedTemplate) {
    Optional<NotificationTemplate> existingTemplate = notificationTemplateRepository.findById(id);

    if (existingTemplate.isPresent()) {
      NotificationTemplate template = existingTemplate.get();
      template.setName(updatedTemplate.getName());
      template.setMessageType(updatedTemplate.getMessageType());
      template.setSubject(updatedTemplate.getSubject());
      template.setBody(updatedTemplate.getBody());
      NotificationTemplate savedTemplate = notificationTemplateRepository.save(template);
      return new NotificationTemplateDto(savedTemplate);
    } else {
      throw new IllegalArgumentException("Template with ID " + id + " not found.");
    }
  }

  public String processTemplate(String templateBody, Map<String, String> values) {
    String processedBody = templateBody;

    // Replace placeholders with actual values
    for (Map.Entry<String, String> entry : values.entrySet()) {
      String placeholder = "{" + entry.getKey() + "}";
      processedBody = processedBody.replace(placeholder, entry.getValue());
    }

    return processedBody;
  }
}
