package com.example.pawsicare.controller;


import com.example.pawsicare.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("chat")
@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/app/msg/public")
    public ResponseEntity<Void> sendNotificationToAll(@Payload ChatMessage msg){
        messagingTemplate.convertAndSend("/chat/public", msg); // listens to /chat
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/app/message/private/{to}")
    public ResponseEntity<Void> sendNotificationToUser(@DestinationVariable String to, @RequestBody ChatMessage msg) {
        messagingTemplate.convertAndSendToUser(to, "/private", msg);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
