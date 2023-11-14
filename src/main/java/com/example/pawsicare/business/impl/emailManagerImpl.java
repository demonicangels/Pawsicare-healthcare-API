package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.requests.sendEmailRequest;
import com.example.pawsicare.domain.MailConfig;
import com.example.pawsicare.domain.managerinterfaces.emailManager;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class emailManagerImpl implements emailManager {
    private String recipient = "nikolgeova@gmail.com";
    @Override
    public String sendEmail(sendEmailRequest details) {

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

