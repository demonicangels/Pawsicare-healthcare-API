package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.requests.SendEmailRequest;
import com.example.pawsicare.domain.managerinterfaces.EmailManager;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailManagerImpl implements EmailManager {
    private String recipient = "nikolgeova@gmail.com";
    private final String sender;
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailManagerImpl(@Value("${spring.mail.username}") String sender, JavaMailSender javaMailSender){
        this.sender = sender;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public String sendEmail(SendEmailRequest details) {

        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(sender);
            helper.setTo(recipient);
            helper.setSubject("PawsiCare contacts");
            helper.setText("From: %s Message: %s".formatted(details.getUserEmail(), details.getMessage()));

            javaMailSender.send(mimeMessage);
            return "Mail sent successfully";
        }
        catch (Exception e){
            return "Error sending the mail";
        }
    }
}

