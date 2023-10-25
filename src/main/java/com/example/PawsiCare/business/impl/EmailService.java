package com.example.PawsiCare.business.impl;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.InputStream;

@Service
@AllArgsConstructor
public class EmailService {

//    @Autowired
//    private final JavaMailSender mailSender;
//
//    public void sendEmail(String from, String body){
//        SimpleMailMessage emailMsg = new SimpleMailMessage();
//        emailMsg.setTo("areliaherondale@gmail.com");
//        emailMsg.setSubject("Contact - PawsiCare");
//        emailMsg.setText(body);
//
//        mailSender.send(emailMsg);
//    }


}
