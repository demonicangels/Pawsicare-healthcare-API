package com.example.pawsicare.controller;


import com.example.pawsicare.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/msg/public")
    public ResponseEntity<Void> sendNotificationToAll(@Payload ChatMessage msg){
        messagingTemplate.convertAndSend("/chat/public", msg); // listens to /chat
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @MessageMapping("/message/private")
    public ResponseEntity<Void> sendNotificationToUser(@RequestBody ChatMessage msg){
        messagingTemplate.convertAndSendToUser(msg.getTo(),"/private", msg); //listens to /user/username/private
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
