package com.example.pawsicare.controller;


import com.example.pawsicare.business.requests.SendEmailRequest;
import com.example.pawsicare.domain.managerinterfaces.EmailManager;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.POST})
@RequestMapping("/contacts")
@AllArgsConstructor
public class EmailController {

    private final EmailManager emailManager;

    @RolesAllowed({"Client"})
    @PostMapping
    public ResponseEntity<String> sendContactEmail(@RequestBody @Valid SendEmailRequest emailRequest)
    {
        try{
            emailManager.sendEmail(emailRequest);
            return ResponseEntity.ok("Email sent successfully");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
