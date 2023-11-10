package com.example.pawsicare.domain.managerinterfaces;

import com.example.pawsicare.business.requests.sendEmailRequest;

public interface emailManager {
    String sendEmail(sendEmailRequest details);
}
