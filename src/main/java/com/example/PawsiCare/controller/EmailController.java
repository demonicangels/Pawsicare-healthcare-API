package com.example.PawsiCare.controller;


import com.example.PawsiCare.business.impl.EmailService;
import com.example.PawsiCare.business.requests.SendEmailRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

@RestController
@RequestMapping("/contacts")
@AllArgsConstructor
public class EmailController {

    private final EmailService emailService;
    @PostMapping
    public String sendContactEmail(@RequestBody @Valid SendEmailRequest emailRequest)
    {
        //emailService.sendEmail(emailRequest.getUserEmail(),emailRequest.getMessage());
        return "Email sent succesfully";
    }

//    private void sendEmail(String userEmail, String userMsg) throws AddressException, MessagingException, IOException{
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "az@mail.com");
//        props.put("mail.smtp.port", "587");
//    }

}
