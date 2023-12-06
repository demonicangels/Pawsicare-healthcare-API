package com.example.pawsicare.domain;

import lombok.Data;

@Data
public class ChatMessage {
    private String id;
    private String from;
    private String to;
    private String text;
}
