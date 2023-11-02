package com.example.PawsiCare.business.impl;

import com.example.PawsiCare.business.requests.SendEmailRequest;
import com.example.PawsiCare.domain.MailConfig;
import com.example.PawsiCare.domain.managerInterfaces.EmailManager;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailManagerImpl implements EmailManager {
    private String recipient = "nikolgeova@gmail.com";
    @Override
    public String sendEmail(SendEmailRequest details) {

        JavaMailSender javaMailSender;

        MailConfig getJavaMailSender = new MailConfig();
        javaMailSender = getJavaMailSender.JavaMailSender();

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

