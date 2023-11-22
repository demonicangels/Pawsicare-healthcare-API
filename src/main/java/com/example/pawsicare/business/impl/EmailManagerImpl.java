package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.requests.SendEmailRequest;
import com.example.pawsicare.domain.managerinterfaces.EmailManager;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailManagerImpl implements EmailManager {
    private String recipient = "nikolgeova@gmail.com";
    private String sender = "pawsicarehealth@gmail.com";
    private final JavaMailSender javaMailSender;
    @Override
    public String sendEmail(SendEmailRequest details) {

        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(recipient);
            mailMessage.setSubject("PawsiCare contacts");
            mailMessage.setText("From: %s Message: %s".formatted(details.getUserEmail(), details.getMessage()));

            javaMailSender.send(mailMessage);
            return "Mail sent successfully";
        }
        catch (Exception e){
            return "Error sending the mail";
        }
    }
}

