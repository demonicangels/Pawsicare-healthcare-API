package com.example.pawsicare.domain.managerInterfaces;

import com.example.pawsicare.business.requests.SendEmailRequest;

public interface EmailManager {
    String sendEmail(SendEmailRequest details);
}
