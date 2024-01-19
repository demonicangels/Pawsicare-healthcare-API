package com.example.pawsicare.business.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendEmailRequest {
    String userEmail;
    String message;
    String subject;
}
