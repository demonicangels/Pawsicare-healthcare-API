package com.example.pawsicare.domain.managerinterfaces;

import com.example.pawsicare.business.requests.SendEmailRequest;

public interface EmailManager {
    String sendEmail(SendEmailRequest details);
}
