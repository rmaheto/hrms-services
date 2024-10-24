package com.muhikira.notificationservice.service;

import com.muhikira.notificationservice.model.NotificationTemplate;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {
  // In-memory store for templates (use a database in production)
  private Map<String, NotificationTemplate> templateStore = new HashMap<>();

  // Method to add or update a template
  public void saveTemplate(NotificationTemplate template) {
    templateStore.put(template.getId(), template);
  }

  // Method to get a template by ID
  public NotificationTemplate getTemplateById(String id) {
    return templateStore.get(id);
  }

  // Method to process the template and replace placeholders with actual values
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
