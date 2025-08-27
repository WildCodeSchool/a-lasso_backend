package com.back_alasso.core;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailService {

  private final MailSender mailSender;

  public MailService(MailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void buildMail(String to, String subject, String body) {
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo(to);
    mailMessage.setSubject(subject);
    mailMessage.setText(body);

    mailSender.send(mailMessage);
  }
}
