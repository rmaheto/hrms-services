package com.muhikira.notificationservice.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
  @Value("${spring.mail.host}")
  private String host;

  @Value("${spring.mail.port}")
  private int port;

  @Value("${spring.mail.username}")
  private String username;

  @Value("${spring.mail.password}")
  private String password;

  @Value("${spring.mail.properties.mail.smtp.auth}")
  private String smtpAuth;

  @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
  private String startTls;

  @Value("${spring.mail.properties.mail.smtp.ssl.trust}")
  private String sslTrust;

  @Bean
  public JavaMailSender javaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(host);
    mailSender.setPort(port);
    mailSender.setUsername(username);
    mailSender.setPassword(password);

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.smtp.auth", smtpAuth);
    props.put("mail.smtp.starttls.enable", startTls);
    props.put("mail.smtp.ssl.trust", sslTrust);
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.debug", "true");  // Set to false in production

    return mailSender;
  }
}
