package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.requests.SendEmailRequest;
import com.example.pawsicare.domain.config.MailConfig;
import com.example.pawsicare.domain.managerinterfaces.EmailManager;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailManagerImpl implements EmailManager {
    private String recipient = "nikolgeova@gmail.com";
    private final JavaMailSender javaMailSender;
    @Override
    public String sendEmail(SendEmailRequest details) {

        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(details.getUserEmail());
            mailMessage.setTo(recipient);
            mailMessage.setSubject("PawsiCare contacts");
            mailMessage.setText(details.getMessage());

            javaMailSender.send(mailMessage);
            return "Mail sent successfully";
        }
        catch (Exception e){
            return "Error sending the mail";
        }
    }
}

