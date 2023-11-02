package com.example.PawsiCare.domain.managerInterfaces;

import com.example.PawsiCare.business.requests.SendEmailRequest;

public interface EmailManager {
    String sendEmail(SendEmailRequest details);
}
