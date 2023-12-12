package com.example.pawsicare.controller;


import com.example.pawsicare.domain.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@AllArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

//    @PostMapping
//    public ResponseEntity<Void> sendNotificationToAll(@RequestBody ChatMessage msg){
//        messagingTemplate.convertAndSend("/chat/public", msg); // listens to /chat
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

    @PostMapping
    public ResponseEntity<Void> sendNotificationToUser(@DestinationVariable String to, @RequestBody ChatMessage msg) {
        messagingTemplate.convertAndSendToUser(to, "/user" + to +"/private", msg);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
