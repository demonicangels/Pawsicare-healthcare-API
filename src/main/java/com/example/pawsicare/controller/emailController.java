package com.example.pawsicare.controller;


import com.example.pawsicare.business.requests.sendEmailRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/contacts")
@AllArgsConstructor
public class emailController {

    private final com.example.pawsicare.domain.managerinterfaces.emailManager emailManager;
    @PostMapping
    public ResponseEntity<String> sendContactEmail(@RequestBody @Valid sendEmailRequest emailRequest)
    {
        try{
            emailManager.sendEmail(emailRequest);
            return ResponseEntity.ok("Email sent successfully");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
