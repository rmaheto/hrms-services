package com.muhikira.notificationservice.service;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.resource.Emailv31;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailjetService {

  @Value("${mailjet.api.public-key}")
  private String apiKeyPublic;

  @Value("${mailjet.api.private-key}")
  private String apiKeyPrivate;

  public boolean sendEmail(String recipientEmail, String recipientName, String subject,
      String textContent, String htmlContent) {
    try {
      ClientOptions options = ClientOptions.builder()
          .apiKey(apiKeyPublic)
          .apiSecretKey(apiKeyPrivate)
          .build();
      MailjetClient client = new MailjetClient(options);

      MailjetRequest request = new MailjetRequest(Emailv31.resource)
          .property(Emailv31.MESSAGES, new JSONArray()
              .put(new JSONObject()
                  .put(Emailv31.Message.FROM, new JSONObject()
                      .put("Email", "your-email@example.com")
                      .put("Name", "Your Name"))
                  .put(Emailv31.Message.TO, new JSONArray()
                      .put(new JSONObject()
                          .put("Email", recipientEmail)
                          .put("Name", recipientName)))
                  .put(Emailv31.Message.SUBJECT, subject)
                  .put(Emailv31.Message.TEXTPART, textContent)
                  .put(Emailv31.Message.HTMLPART, htmlContent)));

      MailjetResponse response = client.post(request);

      if (response.getStatus() == 200) {
        log.info("Email sent successfully to {}", recipientEmail);
        return true;
      } else {
        log.warn("Failed to send email to {}. Status: {} {}", recipientEmail, response.getStatus(),
            response.getData());
        return false;
      }
    } catch (Exception e) {
      log.error("Exception occurred while sending email to {}: {}", recipientEmail, e.getMessage());
      return false;
    }
  }
}
