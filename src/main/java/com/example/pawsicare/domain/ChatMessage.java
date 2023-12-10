package com.example.pawsicare.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private String id;
    private String from;
    private String to;
    private String text;
}
